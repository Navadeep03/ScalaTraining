package main.day18_19.task5

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructField, StructType}

object SparkConsumer {
  def main(args: Array[String]): Unit = {
    val gsServiceAccountPath = "/Users/navadeep/spark-gcs-key.json"

    val userDetailsPath = "gs://task-dataset-bucket/Day_18_19/task5/user_details.csv"
    val outputPath = "gs://task-dataset-bucket/Day_18_19/task5/enriched_data"
    val checkpointPath = "gs://task-dataset-bucket/Day_18_19/task5/enriched_data_checkpoint"

    val kafkaTopic = "orders"

    val spark = initializeSparkSession(gsServiceAccountPath)
    spark.sparkContext.setLogLevel("WARN")

    val userDetailsDF = loadUserDetails(spark, userDetailsPath)

    val kafkaStreamDF = readKafkaStream(spark, kafkaTopic)

    val ordersSchema = defineOrdersSchema()

    val parsedDF = parseKafkaMessages(kafkaStreamDF, ordersSchema)

    val enrichedDF = enrichOrderData(parsedDF, userDetailsDF)

    writeStreamToGCS(enrichedDF, outputPath, checkpointPath)

    spark.streams.awaitAnyTermination()
  }

  private def initializeSparkSession(serviceAccountPath: String): SparkSession = {
    SparkSession.builder()
      .appName("User Details CSV To GCS")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", serviceAccountPath)
      .config("spark.hadoop.fs.gs.auth.service.account.debug", "true")
      .master("local[*]")
      .getOrCreate()
  }

  private def loadUserDetails(spark: SparkSession, path: String) = {
    val userDetailsDF = spark.read.option("header", "true").csv(path)
    userDetailsDF.show(10)
    userDetailsDF
  }

  private def readKafkaStream(spark: SparkSession, topic: String) = {
    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", topic)
      .option("startingOffsets", "earliest")
      .load()
  }

  private def defineOrdersSchema(): StructType = {
    StructType(Seq(
      StructField("order_id", IntegerType),
      StructField("user_id", IntegerType),
      StructField("amount", DoubleType)
    ))
  }

  private def parseKafkaMessages(kafkaStreamDF: org.apache.spark.sql.DataFrame, schema: StructType) = {
    kafkaStreamDF
      .selectExpr("CAST(value AS STRING) as json_string")
      .select(from_json(col("json_string"), schema).as("data"))
      .select("data.order_id", "data.user_id", "data.amount")
  }

  private def enrichOrderData(parsedDF: org.apache.spark.sql.DataFrame, userDetailsDF: org.apache.spark.sql.DataFrame) = {
    parsedDF
      .join(broadcast(userDetailsDF), Seq("user_id"), "left_outer")
      .select(
        col("order_id"),
        col("user_id"),
        col("amount"),
        coalesce(col("name"), lit("UNKNOWN")).alias("name"),
        coalesce(col("age"), lit(0)).alias("age"),
        coalesce(col("email"), lit("NOT_AVAILABLE")).alias("email")
      )
  }

  private def writeStreamToGCS(enrichedDF: org.apache.spark.sql.DataFrame, outputPath: String, checkpointPath: String) = {
    enrichedDF.writeStream
      .outputMode("append")
      .format("json")
      .option("path", outputPath)
      .option("checkpointLocation", checkpointPath)
      .start()
  }
}

