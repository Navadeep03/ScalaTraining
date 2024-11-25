package ScalaTraining.src.scala

import org.apache.spark.{SparkConf, SparkContext}

object RDDJoin {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("RDDJoinExample").setMaster("local[*]"))

    val names = Seq((1, "Alice"), (2, "Bob"), (3, "Charlie"))
    val namesRDD = sc.parallelize(names)
    val scores = Seq((1, 85), (2, 90), (3, 78), (4, 92))
    val scoresRDD = sc.parallelize(scores)

    val joinedRDD = namesRDD
      .join(scoresRDD)

    val result = joinedRDD.map { case (id, (name, score)) => (id, name, score) }

    result.collect().foreach {
      case (id, name, score) => println(s"id: $id, name: $name, score: $score")
    }

    println("Hello world")

    sc.stop()
  }
}
