package main.day14

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object DepartmentWithEmployees {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Department Employees JSON Output")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val departments = Seq((1, "HR"), (2, "Engineering"), (3, "Marketing"))
      .toDF("department_id", "department_name")
    val employees = Seq((101, "Alice", 1), (102, "Bob", 1), (103, "Charlie", 2), (104, "David", 3), (105, "Eve", 2))
      .toDF("employee_id", "employee_name", "department_id")

    departments
      .join(employees, "department_id")
      .groupBy("department_id", "department_name")
      .agg(collect_list(struct("employee_id", "employee_name")).alias("employees"))
      .toJSON
      .show(false)

    spark.stop()
  }
}
