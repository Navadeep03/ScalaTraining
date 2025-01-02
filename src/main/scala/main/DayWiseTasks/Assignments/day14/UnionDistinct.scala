package main.day14

import org.apache.spark.{SparkConf, SparkContext}

object UnionDistinct {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("RDDUnionDistinct").setMaster("local[*]").set("spark.driver.host", "localhost"))

    val rdd1 = sc.parallelize(Seq(1, 2, 3, 4, 5))
    val rdd2 = sc.parallelize(Seq(4, 5, 6, 7, 8))

    rdd1.union(rdd2).distinct().collect().foreach(println)

    sc.stop()
  }
}
