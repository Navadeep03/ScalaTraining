package main.day18_19

import org.apache.spark.sql.{SparkSession, functions => F}

object Caching {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Caching Example")
      .master("local[*]")
      .getOrCreate()

    val salesData = spark.createDataFrame(Seq(
      (1, "Electronics", 100, 2),
      (2, "Clothing", 50, 3),
      (3, "Electronics", 200, 1),
      (4, "Clothing", 150, 2),
      (5, "Home", 120, 1)
    )).toDF("id", "category", "price", "quantity")

    val transformedData = salesData.withColumn("amount", F.col("price") * F.col("quantity"))
      .filter(F.col("amount") > 100)

    val startTimeWithoutCache = System.nanoTime()
    transformedData.show()
    transformedData.groupBy("category").sum("amount").show()
    transformedData.filter(F.col("amount") > 200).show()
    val endTimeWithoutCache = System.nanoTime()
    println(s"Time taken without caching: ${(endTimeWithoutCache - startTimeWithoutCache) / 1e9} seconds")

    val cachedData = transformedData.cache()


    val startTimeWithCache = System.nanoTime()
    cachedData.show()
    cachedData.groupBy("category").sum("amount").show()
    cachedData.filter(F.col("amount") > 200).show()
    val endTimeWithCache = System.nanoTime()
    println(s"Time taken with caching: ${(endTimeWithCache - startTimeWithCache) / 1e9} seconds")

    spark.stop()
  }
}

