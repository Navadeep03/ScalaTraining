package ScalaTraining.src.scala

import org.apache.spark.{SparkConf, SparkContext}

object FilterCSVByAge {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("FilterCSVByAge").setMaster("local[*]"))

    val csvData = Seq(
      "1,John,25",
      "2,Sarah,17",
      "3,Bob,19",
      "4,Alice,16",
      "5,Charlie,22"
    )

    val csvRDD = sc.parallelize(csvData)
    val filteredRDD = csvRDD
      .map(row => row.split(","))                  // Split each row into an array
      .filter(fields => fields(2).toInt >= 18)    // Filter rows where age (3rd column) >= 18
      .map(fields => (fields(0).toInt, fields(1), fields(2).toInt)) // Convert to (id, name, age)

    filteredRDD.collect().foreach {
      case (id, name, age) => println(s"id: $id, name: $name, age: $age")
    }

    sc.stop()
  }
}
