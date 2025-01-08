package main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.consumer

import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.config.KafkaConfig
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

import javax.inject._
import scala.jdk.CollectionConverters._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class KafkaConsumerService @Inject()(implicit ec: ExecutionContext) {
  private val consumer = new KafkaConsumer[String, String](KafkaConfig.consumerConfig.asJava)

  def subscribeAndConsume(topic: String)(processMessage: (String, String) => Unit): Future[Unit] = Future {
    consumer.subscribe(java.util.Collections.singletonList(topic))
    while (true) {
      val records: ConsumerRecords[String, String] = consumer.poll(java.time.Duration.ofMillis(1000))
      records.asScala.foreach { record =>
        processMessage(record.key(), record.value())
      }
    }
  }

  def closeConsumer(): Unit = consumer.close()
}
