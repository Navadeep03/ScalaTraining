package main.day15

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.StdIn
import scala.util.Random

object Exercise1 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("RDD Partitioning Exercise").setMaster("local[*]").set("spark.driver.host","localhost"))
    val numRdd = sc.parallelize(Seq.fill(10000000)(Random.nextInt(1000)))
    println(s"Initial number of partitions: ${numRdd.getNumPartitions}")

    val repartitionedRdd = numRdd.repartition(4)
    println(s"Number of partitions after repartition: ${repartitionedRdd.getNumPartitions}")

    repartitionedRdd.mapPartitionsWithIndex((index, partition) => {
      Iterator(s"Partition $index: ${partition.take(5).mkString(", ")}")
    }).collect().foreach(println)

    val coalescedRdd = repartitionedRdd.coalesce(2)
    println(s"Number of partitions after coalesce: ${coalescedRdd.getNumPartitions}")

    coalescedRdd.mapPartitionsWithIndex((index, partition) => {
      Iterator(s"Partition $index: ${partition.take(5).mkString(", ")}")
    }).collect().foreach(println)

    // To view Spark UI and DAG
    val haltValue = StdIn.readLine()

    sc.stop()
  }
}
