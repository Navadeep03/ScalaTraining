package main.day14

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object EmployeeSalaryAverage {
  def computeAverageSalaryByDepartment(employeeData: DataFrame): DataFrame = {
    employeeData
      .groupBy("department")
      .agg(avg("salary").alias("average_salary"))
      .sort(desc("average_salary"))
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate()
    import spark.implicits._
    val employeeData = Seq(
      ("Alice", "Engineering", 50000),
      ("Bob", "Engineering", 55000),
      ("Charlie", "HR", 40000),
      ("David", "HR", 45000),
      ("Eve", "IT", 60000)
    ).toDF("name", "department", "salary")

    computeAverageSalaryByDepartment(employeeData).show()
  }
}
