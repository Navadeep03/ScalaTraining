import org.apache.spark.sql.SparkSession

object DoubleExample {
  def main(args: Array[String]): Unit = {
    SparkSession.builder().master("local[*]").getOrCreate()
      .sparkContext.parallelize(Seq(1, 2, 3, 4, 5))
      .map(_ * 2)
      .collect()
      .foreach(println)
  }
}