package main.day14

import org.apache.spark.{SparkConf, SparkContext}

object EvenNumbers {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("EvenNumbers").setMaster("local[*]").set("spark.driver.host", "localhost"))

    sc.parallelize(Seq(1, 53, 12, 342, 534, 234, 745, 564, 2342, 4673, 1341))
      .filter(_ % 2 == 0)
      .collect()
      .foreach(println)

    sc.stop()
  }
}
