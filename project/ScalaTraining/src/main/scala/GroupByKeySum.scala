import org.apache.spark.{SparkConf, SparkContext}

object GroupByKeySum {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("GroupByKeySum").setMaster("local[*]"))

    val rdd = sc.parallelize(Seq(
      ("a", 10),
      ("b", 20),
      ("a", 30),
      ("b", 40),
      ("c", 50)
    ))

    val sumByKey = rdd
      .groupByKey()
      .mapValues(values => values.sum)

    sumByKey.collect().foreach {
      case (key, sum) => println(s"Key: $key, Sum: $sum")
    }

    sc.stop()
  }
}
