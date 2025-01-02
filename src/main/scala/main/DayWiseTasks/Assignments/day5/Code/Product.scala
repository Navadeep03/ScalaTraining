package main.DayWiseTasks.Assignments.day5.Code

import scala.io.Source
import java.nio.file.{Files, Paths}

object Product {

  case class Product(id: Int, name: String, category: String, price: Double, stock: Int)

  class ProductOps(productList: List[Product]) {
    def filterByPrice(minPrice: Double): List[Product] = {
      productList.filter(_.price >= minPrice)
    }

    def filterByCategory(category: String): List[Product] = {
      productList.filter(_.category == category)
    }

    def averagePrice: Double = {
      productList.map(_.price).sum / productList.length
    }

    def totalStock: Int = {
      productList.map(_.stock).sum
    }

    def productsByCategory: Map[String, Int] = {
      productList.groupBy(_.category).map { case (cat, products) => cat -> products.length }
    }

    def generateReport: String = {
      val reportHeader = f"Product Report - Total Products: ${productList.length}\n"
      val reportBody = productList.map { product =>
        f"ID: ${product.id}%-5d | Name: ${product.name}%-20s | Category: ${product.category}%-15s | Price: ${product.price}%-10.2f | Stock: ${product.stock}"
      }.mkString("\n")

      reportHeader + reportBody
    }
  }

  def main(args: Array[String]): Unit = {
    println("Product Operations:")

    def verifyFilePath(filePath: String): Boolean = {
      if (Files.exists(Paths.get(filePath))) {
        println(s"File found at $filePath")
        true
      } else {
        println(s"File not found at $filePath")
        false
      }
    }

    def readCSV(filename: String): List[Product] = {
      val lines = Source.fromFile(filename).getLines().toList
      val rows = lines.tail
      rows.map { line =>
        val columns = line.split(",").map(_.trim)
        Product(
          id = columns(0).toInt,
          name = columns(1),
          category = columns(2),
          price = columns(3).toDouble,
          stock = columns(4).toInt
        )
      }
    }

    val filePath = "/Users/navadeep/Downloads/Projects/DayWiseProjects/ScalaTraining/resources/dataSets/products.csv"
    if (verifyFilePath(filePath)) {
      val data = readCSV(filePath)
      val productOps = new ProductOps(data)

      println(f"Average Price: ${productOps.averagePrice}%.2f")
      println(f"Total Stock: ${productOps.totalStock}")
      println("Filtered Products by Price >= 100.0:")
      productOps.filterByPrice(100.0).foreach(println)
      println("Filtered Products by Category 'Electronics':")
      productOps.filterByCategory("Electronics").foreach(println)
      println("Products by Category:")
      productOps.productsByCategory.foreach { case (category, count) => println(s"Category: $category, Count: $count") }
      println("Generated Report:")
      println(productOps.generateReport)
    } else {
      println("Please ensure the file path is correct and accessible.")
    }
  }
}