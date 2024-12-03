package main.day14

import org.apache.spark.{SparkConf, SparkContext}

object CharacterFrequency {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("CharacterFrequency").setMaster("local[*]").set("spark.driver.host", "localhost"))

    sc.parallelize(Seq("Apache Spark", "is awesome", "and powerful"))
      .flatMap(_.replaceAll("\\s", "").toLowerCase)
      .map((_, 1))
      .reduceByKey(_ + _)
      .collect()
      .foreach { case (char, count) => println(s"$char: $count") }

    sc.stop()
  }
}
