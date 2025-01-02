package main.day15

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.StdIn

object Exercise4 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("DAGAnalysis").setMaster("local[*]").set("spark.driver.host", "localhost"))

    val numbersRdd = sc.parallelize(1 to 10000)

    // Narrow transformations
    val evenNumbersRdd = numbersRdd.filter(_ % 2 == 0)
    val multipliedRdd = evenNumbersRdd.map(_ * 10)
    val tupleRdd = multipliedRdd.flatMap(x => Seq((x, 1), (x + 1, 1)))

    // Broad transformations
    val reducedRdd = tupleRdd.reduceByKey(_ + _)
    val inputDirectory = "/Users/navadeep/Desktop/dagAnalysisOp"
    reducedRdd.saveAsTextFile(inputDirectory)

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

      val outputDirectory = "/Users/navadeep/Desktop/consolidatedDAGOp"
      consolidatedRdd.coalesce(1).saveAsTextFile(outputDirectory)

      println(s"Consolidated file saved at: $outputDirectory")
    }
    // To view Spark UI and DAG
    val haltValue = StdIn.readLine()
    sc.stop()
  }

}
