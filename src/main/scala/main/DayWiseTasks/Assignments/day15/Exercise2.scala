package main.day15

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.StdIn

object Exercise2 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("NarrowVsWideTransformations").setMaster("local[*]").set("spark.driver.host", "localhost"))
    val numbersRdd = sc.parallelize(1 to 1000)
    val directory = "/Users/navadeep/Desktop/groupedFile"

    // Narrow transformations
    val mappedRdd = numbersRdd.map(_ * 2)
    val filteredRdd = mappedRdd.filter(_ > 500)

    // Wide transformations
    val keyValueRdd = filteredRdd.map(num => (num % 10, num)) // Key - value pair
    val groupedRdd = keyValueRdd.groupByKey()

    groupedRdd.saveAsTextFile(directory)

    val fs = FileSystem.get(sc.hadoopConfiguration)
    val files = fs.listStatus(new Path(directory))
      .filter(_.getPath.getName.startsWith("part"))
      .map(_.getPath.toString)
    val rdd = sc.textFile(files.mkString(","))
    val consolidatedRdd = rdd.coalesce(1)

    val outputDirectory = "/Users/navadeep/Desktop/consolidatedFile"
    consolidatedRdd.saveAsTextFile(outputDirectory)

    // To view Spark UI and DAG
    val haltValue = StdIn.readLine()

    sc.stop()
  }
}
