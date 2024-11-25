package ScalaTraining.src.scala

import org.apache.spark.{SparkConf, SparkContext}

object CartesianProduct {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("CartesianProduct").setMaster("local[*]").set("spark.driver.host", "localhost")))

    sc.parallelize(Seq(5, 3, 6, 1, 2, 5, 8))
      .cartesian(sc.parallelize(Seq(10, 13, 12, 4, 1, 2, 45)))
      .map { case (a, b) => a * b }
      .collect()
      .foreach(println)

    sc.stop()
  }
}
