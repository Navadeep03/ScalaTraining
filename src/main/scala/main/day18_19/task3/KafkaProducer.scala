package main.day18_19.task3

import com.google.gson.{Gson, JsonParser}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import java.util.Properties
import scala.collection.JavaConverters._
import scala.io.Source

object KafkaProducer {
  def main(args: Array[String]): Unit = {
    val topic = "transactions"
    val fp = "src/main/scala/main/day18_19/task3/transactions.json"

    val producer: KafkaProducer[String, String] = createProducer()

    try {
      val messages = parseJsonFile(fp)
      messages.foreach { message =>
        producer.send(new ProducerRecord[String, String](topic, message))
        Thread.sleep(1000) // Introduced delay between messages
      }
    } finally {
      producer.close()
    }
  }

  private def createProducer(): KafkaProducer[String, String] = {
    val properties = new Properties()
    properties.put("bootstrap.servers", "localhost:9092")
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](properties)
  }

  private def parseJsonFile(filePath: String): List[String] = {
    var source: Source = null
    try {
      source = Source.fromFile(filePath)
      val content = source.mkString

      val gson = new Gson()
      val jsonElement = JsonParser.parseString(content)

      if (jsonElement.isJsonArray) {
        jsonElement.getAsJsonArray.asScala
          .map(element => gson.toJson(element))
          .toList
      } else {
        List(gson.toJson(jsonElement))
      }
    } catch {
      case ex: Exception =>
        println(s"Failed to read or parse the JSON file: ${ex.getMessage}")
        List.empty
    } finally {
      if (source != null) source.close()
    }
  }
}
