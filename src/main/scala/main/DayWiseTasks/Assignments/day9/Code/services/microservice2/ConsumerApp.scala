package main.DayWiseTasks.Assignments.day9.Code.services.microservice2


import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import spray.json._
import JsonFormats._

import java.util.Properties

object Constants {
  val NETWORK_MESSAGE_TOPIC = "network-message"
  val APP_MESSAGE_TOPIC = "app-message"
  val CLOUD_MESSAGE_TOPIC = "cloud-message"
  val CONSOLIDATED_MESSAGES_TOPIC = "consolidated-messages"
}

case class ProcessMessage(message: String, messageKey: String)

object JsonFormats {
  implicit val processMessageFormat: RootJsonFormat[ProcessMessage] = jsonFormat2(ProcessMessage)
}

class NetworkListener(messageGatherer: ActorRef) extends Actor {
  override def receive: Receive = {
    case pm: ProcessMessage => messageGatherer ! pm
  }
}

class AppListener(messageGatherer: ActorRef) extends Actor {
  override def receive: Receive = {
    case pm: ProcessMessage => messageGatherer ! pm
  }
}

class CloudListener(messageGatherer: ActorRef) extends Actor {
  override def receive: Receive = {
    case pm: ProcessMessage => messageGatherer ! pm
  }
}

class MessageGatherer(producer: KafkaProducer[String, String]) extends Actor {
  override def receive: Receive = {
    case pm: ProcessMessage =>
      val record = new ProducerRecord[String, String](Constants.CONSOLIDATED_MESSAGES_TOPIC, pm.toJson.toString())
      producer.send(record)
  }
}

object KafkaConsumer {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("MessagingConsumerSystem")

    val producer: KafkaProducer[String, String] = {
      val props = new Properties()
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
      new KafkaProducer[String, String](props)
    }

    val messageGatherer: ActorRef = system.actorOf(Props(new MessageGatherer(producer)), "MessageGatherer")

    val networkListener: ActorRef = system.actorOf(Props(new NetworkListener(messageGatherer)), "NetworkListener")
    val appListener: ActorRef = system.actorOf(Props(new AppListener(messageGatherer)), "AppListener")
    val cloudListener: ActorRef = system.actorOf(Props(new CloudListener(messageGatherer)), "CloudListener")

    val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    def startListener(topic: String, listener: ActorRef): Unit = {
      Consumer.plainSource(consumerSettings, Subscriptions.topics(topic))
        .map(record => record.value().parseJson.convertTo[ProcessMessage])
        .runWith(Sink.actorRef(listener, onCompleteMessage = "complete"))
    }

    startListener(Constants.NETWORK_MESSAGE_TOPIC, networkListener)
    startListener(Constants.APP_MESSAGE_TOPIC, appListener)
    startListener(Constants.CLOUD_MESSAGE_TOPIC, cloudListener)
  }
}
