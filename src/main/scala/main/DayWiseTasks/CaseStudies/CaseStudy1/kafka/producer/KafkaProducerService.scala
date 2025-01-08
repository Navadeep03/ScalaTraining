package main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.producer

import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.config.KafkaConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import javax.inject._
import scala.jdk.CollectionConverters._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class KafkaProducerService @Inject()(implicit ec: ExecutionContext) {
  private val producer = new KafkaProducer[String, String](KafkaConfig.producerConfig.asJava) // Converts Scala Map to Java Map

  def publishMessage(topic: String, key: String, value: String): Future[Unit] = Future {
    val record = new ProducerRecord[String, String](topic, key, value)
    producer.send(record)
    println(s"Published message to topic: $topic, key: $key, value: $value")
  }

  def closeProducer(): Unit = producer.close()
}
