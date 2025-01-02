package main.DayWiseTasks.Assignments.day7.Code

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Random, Success, Failure}
import java.util.concurrent.atomic.{AtomicBoolean, AtomicReference}
import org.mongodb.scala._

object UnifiedMain {

  // JobRunner Class
  def jobRunner(description: String, timeInSeconds: Int)(logic: => Unit): Unit = {
    println(s"Waiting for $timeInSeconds seconds")
    Thread.sleep(timeInSeconds * 1000)
    logic
    println(s"Job '$description' completed.")
  }

  // Async Thread Execution
  def randomNumberThreadExecutor(): Future[String] = {
    val promise = Promise[String]()
    val isCompleted = new AtomicBoolean(false)
    val stopSignals = new AtomicReference[Seq[Thread]](Seq.empty)

    def startThread(threadName: String): Thread = new Thread(() => {
      val random = new Random()
      while (!isCompleted.get() && !promise.isCompleted) {
        val randomInt = random.nextInt(2000)
        if (randomInt == 1567 && !isCompleted.getAndSet(true)) {
          promise.success(s"$threadName has generated 1567")
          stopSignals.get().foreach(_.interrupt())
        }
      }
    })

    val threads = Seq(
      startThread("FirstThread"),
      startThread("SecondThread"),
      startThread("ThirdThread")
    )

    stopSignals.set(threads)
    threads.foreach(_.start())
    promise.future
  }

  // MongoDB Interaction Example
  def interactWithMongoDB(): Unit = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("mydb")
    val collection: MongoCollection[Document] = database.getCollection("employees")

    // Insert Documents
    val documents = Seq(
      Document("name" -> "John Doe", "age" -> 30),
      Document("name" -> "Jane Smith", "age" -> 25)
    )
    collection.insertMany(documents).toFuture().onComplete {
      case Success(_) => println("Documents inserted successfully.")
      case Failure(e) => e.printStackTrace()
    }

    // Query Documents
    collection.find().collect().toFuture().onComplete {
      case Success(docs) => docs.foreach(doc => println(doc.toJson()))
      case Failure(e)    => e.printStackTrace()
    }

    Thread.sleep(3000) // Wait for async operations
    mongoClient.close()
  }

  def main(args: Array[String]): Unit = {
    // JobRunner Example
    jobRunner("Background job", 4) {
      println("Hello, I am from inside the block of code after 4 seconds")
    }

    // Async Thread Example
    val futureResult = randomNumberThreadExecutor()

    futureResult.onComplete {
      case Success(message) => println(message)
      case Failure(exception) => println(s"Error occurred: ${exception.getMessage}")
    }

    scala.concurrent.Await.result(futureResult, scala.concurrent.duration.Duration.Inf)

    // MongoDB Interaction Example
    interactWithMongoDB()
  }
}
