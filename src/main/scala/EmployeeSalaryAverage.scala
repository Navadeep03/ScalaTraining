import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object EmployeeSalaryAverage {
  def main(args: Array[String]): Unit = {
    SparkSession.builder().master("local[*]").getOrCreate()
      .createDataFrame(Seq(
        ("Alice", "Engineering", 50000),
        ("Bob", "Engineering", 55000),
        ("Charlie", "HR", 40000),
        ("David", "HR", 45000),
        ("Eve", "IT", 60000)
      )).toDF("name", "department", "salary")
      .groupBy("department")
      .agg(avg("salary").alias("average_salary"))
      .sort(desc("average_salary"))
      .show()
  }
}
