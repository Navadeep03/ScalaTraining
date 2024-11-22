import org.apache.spark.{SparkConf, SparkContext}

object AverageScore {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("AverageScore").setMaster("local[*]"))

    val data = Seq((1, 85), (2, 90), (3, 78), (4, 88), (5, 92))
    val rdd = sc.parallelize(data)

    val scoreCount = rdd
      .map { case (_, score) => (score, 1) }
      .reduceByKey(_ + _)

    val (totalScore, count) = scoreCount
      .collect()
      .reduce((acc, next) => (acc._1 + next._1, acc._2 + next._2))

    println(s"Average Score: ${totalScore.toDouble / count}")

    sc.stop()
  }
}
