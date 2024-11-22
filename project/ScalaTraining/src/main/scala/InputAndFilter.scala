import org.apache.spark.{SparkConf, SparkContext}
import scala.io.StdIn

object InputAndFilter {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("InputAndFilterRDD").setMaster("local[*]"))

    println("Enter the key to filter:")
    val inputKey = StdIn.readLine()

    val rdd = sc.parallelize(Seq(
      ("a", 10),
      ("b", 20),
      ("a", 30),
      ("b", 40),
      ("c", 50)
    ))

    val result = rdd.filter { case (key, _) => key == inputKey }.collect()
    if (result.isEmpty) {
      println(s"No records found for key: $inputKey")
    } else {
      result.foreach { case (key, value) => println(s"Key: $key, Value: $value") }
    }

    // To view Spark UI and DAG
    val haltValue = StdIn.readLine()

    sc.stop()
  }
}
