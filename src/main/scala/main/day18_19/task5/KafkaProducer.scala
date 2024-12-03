package main.day18_19.task5

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.util.Random
import java.util.Properties
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object KafkaProducer {
  def main(args: Array[String]): Unit = {
    val topic = "orders"
    val random = new Random()

    val producer: KafkaProducer[String, String] = initializeProducer()

    val sendRecord: () => Unit = () => {
      val (orderId, userId, amount) = generateOrder(random)

      val message = createJsonMessage(orderId, userId, amount)
      val record = new ProducerRecord[String, String](topic, message)

      producer.send(record)
      println(s"Message sent: $message at ${currentTimeString()}")
    }

    val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    scheduler.scheduleAtFixedRate(() => sendRecord(), 0, 1, TimeUnit.SECONDS)

    addShutdownHook(producer, scheduler)
  }

  private def initializeProducer(): KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  private def generateOrder(random: Random): (Int, Int, BigDecimal) = {
    val orderId = random.nextInt(1000) + 1
    val userId = 100 + random.nextInt(100) + 1
    val amount = BigDecimal(random.nextDouble() * 500).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    (orderId, userId, amount)
  }

  private def createJsonMessage(orderId: Int, userId: Int, amount: BigDecimal): String = {
    s"""{"order_id": $orderId, "user_id": $userId, "amount": $amount}"""
  }

  private def currentTimeString(): String = {
    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
  }

  private def addShutdownHook(producer: KafkaProducer[String, String], scheduler: ScheduledExecutorService): Unit = {
    sys.addShutdownHook {
      println("Initiating shutdown of Kafka producer and scheduler...")
      scheduler.shutdown()
      try {
        if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
          scheduler.shutdownNow()
        }
      } catch {
        case _: InterruptedException =>
          scheduler.shutdownNow()
      }
      producer.close()
      println("Shutdown complete. Kafka producer stopped.")
    }
  }
}

