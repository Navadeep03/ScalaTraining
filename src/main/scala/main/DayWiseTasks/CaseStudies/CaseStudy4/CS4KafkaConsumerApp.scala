package main.FinalProject.CaseStudy4

import org.apache.spark.sql.{SparkSession, functions => F}
import org.apache.spark.sql.types._
import org.apache.spark.sql.streaming.Trigger

object KafkaConsumerApp {

  // Kafka Consumer Configurations
  val topic = "walmart_sales_topic"  // Kafka topic for sales data
  val kafkaBootstrapServers = "localhost:9092"  // Kafka server address
  val featuresPath = "src/resources/features.csv"  // Path to the features CSV file
  val storesPath = "src/resources/stores.csv"  // Path to the stores CSV file
  val baseGCSPath = "gs://task-dataset-bucket/FinalProject/CaseStudy4/"  // GCS path for storing results

  def main(args: Array[String]): Unit = {
    // Initialize Spark session
    val spark = SparkSession.builder
      .appName("Optimized Real-Time Data Processing with Spark")
      .master("local[*]")  // Run Spark locally or on a cluster
      .getOrCreate()

    // Set up GCS credentials (ensure you have the service account credentials file)
    spark.conf.set("spark.hadoop.fs.gs.auth.service.account.json.keyfile", "/path/to/your/service-account-file.json")

    println("Spark session initialized.")

    // Kafka Consumer Options
    val kafkaOptions = Map(
      "kafka.bootstrap.servers" -> kafkaBootstrapServers,
      "kafka.security.protocol" -> "PLAINTEXT",  // Adjust for your security needs
      "kafka.sasl.mechanism" -> "PLAIN",  // Adjust based on your Kafka setup
      "kafka.consumer.group.id" -> "test-group",
      "startingOffsets" -> "earliest"
    )

    println("Kafka consumer options set.")

    // Load the features and stores data from CSV
    val featuresDF = spark.read.option("header", "true").option("inferSchema", "true").csv(featuresPath)
    val storesDF = spark.read.option("header", "true").option("inferSchema", "true").csv(storesPath)

    println("Features and stores data loaded.")

    // Clean and rename IsHoliday column in featuresDF to avoid ambiguity
    val cleanedFeaturesDF = featuresDF.filter(F.col("Store").isNotNull)
      .withColumnRenamed("IsHoliday", "Features_IsHoliday")

    // Clean and rename IsHoliday column in storesDF to avoid ambiguity
    val cleanedStoresDF = storesDF.filter(F.col("Store").isNotNull)

    // Cache the features and stores data as they will be used repeatedly
    val cachedFeaturesDF = cleanedFeaturesDF.cache()
    val cachedStoresDF = cleanedStoresDF.cache()

    println("Features and stores data cleaned and cached.")

    // Kafka Stream - Reading from Kafka topic
    val kafkaStreamDF = spark.readStream
      .format("kafka")
      .options(kafkaOptions)
      .option("subscribe", topic)  // Ensure the topic name is correct
      .load()

    println(s"Reading from Kafka topic: $topic")

    // Define schema for the sales data in the Kafka message
    val salesSchema = StructType(Seq(
      StructField("Store", IntegerType),
      StructField("Department", IntegerType),
      StructField("Weekly_Sales", IntegerType),
      StructField("IsHoliday", IntegerType),
      StructField("Date", StringType)  // Assuming you have a 'Date' column in the Kafka stream
    ))

    // Parse the Kafka stream's message (JSON)
    val parsedDF = kafkaStreamDF
      .selectExpr("CAST(value AS STRING) as json_string")  // Converting the Kafka value to a string
      .select(F.from_json(F.col("json_string"), salesSchema).as("data"))
      .select("data.Store", "data.Department", "data.Weekly_Sales", "data.IsHoliday", "data.Date")

    println("Parsed Kafka stream data.")

    // Rename the IsHoliday column in the Kafka stream to avoid ambiguity
    val renamedKafkaDF = parsedDF.withColumnRenamed("IsHoliday", "Kafka_IsHoliday")
      .withColumnRenamed("Date", "Stream_Date")  // Rename Date to avoid ambiguity

    // Data Validation: Ensure no negative Weekly_Sales and handle missing values
    val validDF = renamedKafkaDF.filter(F.col("Weekly_Sales").geq(0))

    println("Validated data (removed negative Weekly_Sales).")

    // Enrich with features and stores metadata (avoiding ambiguity by renaming columns)
    val enrichedDF = validDF
      .join(F.broadcast(cachedFeaturesDF), Seq("Store"), "left_outer")  // Join featuresDF (broadcasted)
      .join(F.broadcast(cachedStoresDF), Seq("Store"), "left_outer")  // Left join to handle missing store metadata
      .select(
        F.col("Store"),
        F.col("Department"),
        F.col("Weekly_Sales"),
        F.col("Kafka_IsHoliday"),  // From Kafka stream
        F.coalesce(F.col("Type"), F.lit("UNKNOWN")).alias("Type"),  // Coalesce Type if null
        F.col("Features_IsHoliday"),  // From features.csv
        F.col("Stream_Date")  // Using the renamed Date column to avoid ambiguity
      )

    println("Data enriched with store and feature data.")

    // Convert Stream_Date to TimestampType for watermarking
    val enrichedWithTimestampDF = enrichedDF
      .withColumn("Stream_Date_Timestamp", F.to_timestamp(F.col("Stream_Date"), "yyyy-MM-dd HH:mm:ss"))

    // Add watermarking based on 'Stream_Date_Timestamp' to handle late-arriving data
    val enrichedWithWatermarkDF = enrichedWithTimestampDF
      .withWatermark("Stream_Date_Timestamp", "1 minute")  // Allow late data within 1 minute of the watermark

    println("Watermark applied to the Stream_Date_Timestamp.")

    // **Write data with watermark to console for visualization**
    enrichedWithWatermarkDF.writeStream
      .outputMode("append")
      .format("console")
      .option("truncate", "false")
      .start()

    println("Data with watermark applied displayed in console.")

    // Windowed Aggregation: Process data in time windows (10-minute window, sliding every 5 minutes)
    val windowedMetricsDF = enrichedWithWatermarkDF
      .groupBy(
        F.window(F.col("Stream_Date_Timestamp"), "10 minutes", "5 minutes"),  // 10-minute windows with 5-minute sliding
        F.col("Store")
      )
      .agg(
        F.sum("Weekly_Sales").alias("Total_Weekly_Sales"),
        F.avg("Weekly_Sales").alias("Avg_Weekly_Sales"),
        F.max("Weekly_Sales").alias("Top_Store_Sales")
      )

    println("Windowed metrics aggregated.")

    // **Write windowed metrics to console for visualization**
    windowedMetricsDF.writeStream
      .outputMode("append")
      .format("console")
      .option("truncate", "false")
      .start()

    println("Windowed aggregated metrics displayed in console.")

    // Write windowed metrics to Parquet using Append Mode
    windowedMetricsDF.writeStream
      .outputMode("append")  // Use Append mode for streaming aggregation
      .format("parquet")
      .option("checkpointLocation", baseGCSPath + "checkpoint")
      .option("path", baseGCSPath + "windowed_store_level_metrics")
      .trigger(Trigger.ProcessingTime("10 seconds"))  // Process every 10 seconds (micro-batches)
      .start()

    println("Windowed metrics are being written to GCS in Parquet format.")

    // Await termination to keep the streaming job running
    spark.streams.awaitAnyTermination()
  }
}
