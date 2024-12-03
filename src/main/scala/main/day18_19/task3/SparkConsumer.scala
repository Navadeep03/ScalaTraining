package main.day18_19.task3

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, current_timestamp, from_json, sum, window}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructField, StructType}
import org.apache.spark.sql.streaming.Trigger

object SparkConsumer {
  def main(args: Array[String]): Unit = {
    // Initialize Spark session for processing streaming data
    val spark = SparkSession.builder()
      .appName("Exercise3 - KafkaTransactionProcessor")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    val topic = "transactions" // Define Kafka topic

    // Read streaming data from Kafka
    val kafkaStreamDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", topic)
      .option("startingOffsets", "earliest")
      .load()

    // Define schema for incoming JSON data
    val transactionSchema = StructType(Seq(
      StructField("transactionId", IntegerType, nullable = false),
      StructField("userId", IntegerType, nullable = false),
      StructField("amount", DoubleType, nullable = false)
    ))

    // Parse and transform Kafka messages
    val parsedStreamDF = kafkaStreamDF
      .selectExpr("CAST(value AS STRING) as jsonString")
      .select(from_json(col("jsonString"), transactionSchema).as("data"))
      .select("data.transactionId", "data.amount")
      .withColumn("timestamp", current_timestamp())

    // Aggregate transactions in a 10-second window
    val aggregatedStreamDF = parsedStreamDF
      .groupBy(window(col("timestamp"), "10 seconds"))
      .agg(sum("amount").alias("total_amount"))

    // Write the results to console
    val query = aggregatedStreamDF.writeStream
      .outputMode("update") // Output only updated results
      .format("console")
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()

    query.awaitTermination()
  }
}
