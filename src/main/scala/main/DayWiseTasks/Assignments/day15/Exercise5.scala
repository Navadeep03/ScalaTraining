package main.day15

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.StdIn

object Exercise5 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("DAGAnalysis").setMaster("local[*]").set("spark.driver.host", "localhost"))

    val filePath = "/Users/navadeep/Downloads/Work/ScalaTraining/scala-spark/Images/Exercise5/judgments.csv"
    val rdd = sc.textFile(filePath)
    val partitions2 = rdd.repartition(2)
    val partitions4 = rdd.repartition(4)
    val partitions8 = rdd.repartition(8)

    val startTime = System.nanoTime()
    partitions2.count()
    val endTime = System.nanoTime()
    println(s"Execution time for 2 partitions: ${(endTime - startTime) / 1e9} seconds")

    val startTime4 = System.nanoTime()
    partitions4.count()
    val endTime4 = System.nanoTime()
    println(s"Execution time for 4 partitions: ${(endTime4 - startTime4) / 1e9} seconds")

    val startTime8 = System.nanoTime()
    partitions8.count()
    val endTime8 = System.nanoTime()
    println(s"Execution time for 8 partitions: ${(endTime8 - startTime8) / 1e9} seconds")

    val sortedRdd = rdd.sortBy(line => line.length)

    sortedRdd.saveAsTextFile("/Users/navadeep/Desktop/sortedJudgmentsOutput")

    // To view Spark UI and DAG
    val haltValue = StdIn.readLine()
    sc.stop()
  }

}
