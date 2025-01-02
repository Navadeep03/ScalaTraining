package main.FinalProject.CaseStudy4

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
import scala.util.Random
import scala.concurrent.duration._

object KafkaProducerApp {

  // Kafka Producer Configurations
  val topic = "walmart_sales_topic"
  val kafkaBootstrapServers = "localhost:9092"  // Adjust according to your Kafka cluster

  def createProducer(): KafkaProducer[String, String] = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
    new KafkaProducer[String, String](props)
  }

  // Generate random sales data for the producer
  def generateSalesData(): String = {
    val storeId = Random.nextInt(100).toString
    val departmentId = Random.nextInt(20).toString
    val weeklySales = Random.nextInt(100000).toString
    val isHoliday = Random.nextInt(2).toString
    val record = s"""{"Store": $storeId, "Department": $departmentId, "Weekly_Sales": $weeklySales, "IsHoliday": $isHoliday}"""
    record
  }

  // Produce records to Kafka topic
  def produceRecords(producer: KafkaProducer[String, String]): Unit = {
    while (true) {
      val record = generateSalesData()
      val producerRecord = new ProducerRecord[String, String](topic, record)
      producer.send(producerRecord)
      println(s"Produced: $record")
      Thread.sleep(5000)  // Produce data every 5 seconds
    }
  }

  def main(args: Array[String]): Unit = {
    val producer = createProducer()
    produceRecords(producer)
  }
}
