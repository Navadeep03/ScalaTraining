package main.DayWiseTasks.Assignments.day8.Code

import java.sql.{Connection, DriverManager, PreparedStatement}

object IntegratedQuestions {

  def main(args: Array[String]): Unit = {

    // Question 1: Multiple Inheritance with Traits
    println("--- Question 1: Multiple Inheritance with Traits ---")

    trait GetStarted {
      def prepare(): Unit = {
        println("From GetStarted prepare")
      }
    }

    trait Cook extends GetStarted {
      override def prepare(): Unit = {
        super.prepare()
        println("From Cook prepare")
      }
    }

    trait Seasoning {
      def applySeasoning(): Unit = {
        println("From Seasoning applySeasoning")
      }
    }

    class Food extends Cook with Seasoning {
      def prepareFood(): Unit = {
        prepare()
        applySeasoning()
      }
    }

    val food = new Food()
    food.prepareFood()

    println("\n")

    // Question 2: Observing Behavior with Trait Order
    println("--- Question 2: Observing Behavior with Trait Order ---")

    trait Task {
      def doTask(): Unit = {
        println("From Task doTask")
      }
    }

    trait CookTask extends Task {
      override def doTask(): Unit = {
        println("From CookTask doTask")
      }
    }

    trait Garnish extends CookTask {
      override def doTask(): Unit = {
        println("From Garnish doTask")
      }
    }

    trait Pack extends Garnish {
      override def doTask(): Unit = {
        println("From Pack doTask")
      }
    }

    class Activity extends Task {
      def doActivity(): Unit = {
        println("From Activity doActivity")
        doTask()
      }
    }

    val activity1 = new Activity()
    activity1.doActivity()

    println("Break")

    val activity2: Task = new Activity with CookTask with Garnish with Pack
    activity2.doTask()

    println("\n")

    // Question 3: Case Class and Database Operations
    println("--- Question 3: Case Class and Database Operations ---")

    case class Candidate(sno: Int, name: String, city: String)

    object Candidate {
      def apply(sno: Int, name: String, city: String): Candidate = {
        new Candidate(sno, name, city)
      }
    }

    implicit def tupleToCandidate(tuple: (Int, String, String)): Candidate = {
      Candidate(tuple._1, tuple._2, tuple._3)
    }

    class CandidateOps {
      def insertMethod(candidate: Candidate, connection: Connection): Unit = {
        try {
          val sql = "INSERT INTO candidates (sno, name, city) VALUES (?, ?, ?)"
          val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
          preparedStatement.setInt(1, candidate.sno)
          preparedStatement.setString(2, candidate.name)
          preparedStatement.setString(3, candidate.city)
          preparedStatement.executeUpdate()
          println(s"Inserted: ${candidate.sno}, ${candidate.name}, ${candidate.city}")
        } catch {
          case e: Exception => e.printStackTrace()
        }
      }

      def insertCandidates(candidates: Array[Candidate], connection: Connection): Unit = {
        candidates.foreach(candidate => insertMethod(candidate, connection))
      }

      def verifyInsertion(connection: Connection): Unit = {
        try {
          val query = "SELECT * FROM candidates"
          val statement = connection.createStatement()
          val resultSet = statement.executeQuery(query)
          println("Candidates in the database:")
          while (resultSet.next()) {
            val sno = resultSet.getInt("sno")
            val name = resultSet.getString("name")
            val city = resultSet.getString("city")
            println(s"ID: $sno, Name: $name, City: $city")
          }
        } catch {
          case e: Exception => e.printStackTrace()
        }
      }
    }

    val url = "jdbc:mysql://localhost:3306/scala_project"
    val username = "root"
    val password = "vedanthamrrn"
    var connection: Connection = null

    try {
      Class.forName("com.mysql.cj.jdbc.Driver")
      connection = DriverManager.getConnection(url, username, password)
      val candidateData: Array[(Int, String, String)] = Array(
        (1, "Alice", "New York"),
        (2, "Bob", "Los Angeles"),
        (3, "Charlie", "Chicago")
        // Add more candidates as needed
      )
      val candidates = candidateData.map(tuple => tuple: Candidate)
      val ops = new CandidateOps()
      ops.insertCandidates(candidates, connection)
      ops.verifyInsertion(connection)
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (connection != null) connection.close()
    }
  }
}
