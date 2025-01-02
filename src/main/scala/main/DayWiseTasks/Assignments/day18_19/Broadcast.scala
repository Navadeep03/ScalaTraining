package main.day18_19

import org.apache.spark.sql.{SparkSession, Row}
import org.apache.spark.sql.functions.broadcast
import org.apache.spark.sql.types._

object Broadcast {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Broadcast Join Example")
      .master("local[*]") // This will run Spark locally with all available cores
      .getOrCreate()

    val userDetailsSchema = StructType(Array(
      StructField("user_id", IntegerType, true),
      StructField("name", StringType, true)
    ))

    // Create sample data for user details
    val userDetailsData = Seq(
      Row(1, "Alice"),
      Row(2, "Bob"),
      Row(3, "Charlie"),
      Row(4, "David")
    )

    val userDetails = spark.createDataFrame(
      spark.sparkContext.parallelize(userDetailsData),
      userDetailsSchema
    )

    val transactionLogsSchema = StructType(Array(
      StructField("transaction_id", IntegerType, true),
      StructField("user_id", IntegerType, true),
      StructField("amount", DoubleType, true),
      StructField("date", StringType, true)
    ))

    val transactionLogsData = Seq(
      Row(1001, 1, 250.0, "2024-11-25"),
      Row(1002, 2, 125.5, "2024-11-26"),
      Row(1003, 3, 300.0, "2024-11-27"),
      Row(1004, 1, 450.0, "2024-11-28"),
      Row(1005, 4, 200.0, "2024-11-29"),
      Row(1006, 2, 300.0, "2024-11-29"),
      Row(1007, 3, 150.0, "2024-11-30")
    )

    val transactionLogs = spark.createDataFrame(
      spark.sparkContext.parallelize(transactionLogsData),
      transactionLogsSchema
    )

    val broadcastedUserDetails = broadcast(userDetails)

    val joinedData = transactionLogs.join(broadcastedUserDetails, "user_id")

    joinedData.show()
  }
}
