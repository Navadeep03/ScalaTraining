package main.day15

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.StdIn

object Exercise3 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("MillionString_WordCountMapper").setMaster("local[*]").set("spark.driver.host", "localhost"))
    val numLines = 1000000
    val textRdd = sc.parallelize(Seq.fill(numLines)("lorem ipsum dolor sit amet"))

    val wordsRdd = textRdd.flatMap(_.split(" "))
    val wordPairsRdd = wordsRdd.map(word => (word, 1))
    val wordCountsRdd = wordPairsRdd.reduceByKey(_ + _)

    val inputDirectory = "/Users/navadeep/Desktop/wordCounts"

    wordCountsRdd.collect().foreach(println)
    wordCountsRdd.saveAsTextFile(inputDirectory)

    val fs = FileSystem.get(sc.hadoopConfiguration)
    val files = fs.listStatus(new Path(inputDirectory))
      .filter(status => status.getPath.getName.startsWith("part") && fs.getFileStatus(status.getPath).getLen > 0)
      .map(_.getPath.toString)
    if (files.isEmpty) {
      println("No non-empty part files found!")
    } else {
      val rdd = sc.textFile(files.mkString(","))

      val consolidatedRdd = rdd.map(line => {
        val parts = line.stripPrefix("(").stripSuffix(")").split(",")
        (parts(0).trim, parts(1).trim.toInt)
      })

      val outputDirectory = "/Users/navadeep/Desktop/consolidatedWordCounts"
      consolidatedRdd.coalesce(1).saveAsTextFile(outputDirectory)

      println(s"Consolidated file saved at: $outputDirectory")
    }

    // To view Spark UI and DAG
    val haltValue = StdIn.readLine()
    sc.stop()
  }
}
