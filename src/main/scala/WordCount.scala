package ScalaTraining.src.scala

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("WordCount").setMaster("local[*]").set("spark.driver.host", "localhost")))

    sc.parallelize(Seq("Apache Spark is awesome", "Learning Spark is fun", "My name is Spark"))
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
      .collect()
      .foreach { case (word, count) => println(s"$word: $count") }

    sc.stop()
  }
}
