import org.apache.spark.{SparkConf, SparkContext}

object SumOf100Integers {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("SumOfIntegers").setMaster("local[*]"))
    val sum = sc.parallelize(1 to 100).reduce(_ + _)
    println(s"Sum of integers from 1 to 100: $sum")

    sc.stop()
  }
}
