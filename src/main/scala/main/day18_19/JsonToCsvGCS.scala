package main.day18_19

import org.apache.spark.sql.SparkSession

object JsonToCsvGCS {
  def main(args: Array[String]): Unit = {
    // Initialize SparkSession with GCS configurations
    val spark = SparkSession.builder()
      .appName("JSON to CSV Conversion")
      .config("spark.hadoop.fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
      .config("spark.hadoop.fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
      .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      .config("spark.hadoop.google.cloud.auth.service.account.json.keyfile", "/Users/navadeep/spark-gcs-key.json")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    val csvData = Seq(
      Map("id" -> 1, "name" -> "Alice", "age" -> 30, "city" -> "New York"),
      Map("id" -> 2, "name" -> "Bob", "age" -> 25, "city" -> "Los Angeles"),
      Map("id" -> 3, "name" -> "Charlie", "age" -> 35, "city" -> "Chicago")
    ).map(_.values.toSeq.mkString(","))
    val csvDF = spark.createDataset(csvData).toDF("csv_row")
    val outputPath = "gs://task-dataset-bucket/Day_18_19/output"
    csvDF.write
      .mode("overwrite")
      .option("header", "false")
      .text(outputPath)

    println(s"CSV successfully written to $outputPath")

    spark.stop()
  }
}