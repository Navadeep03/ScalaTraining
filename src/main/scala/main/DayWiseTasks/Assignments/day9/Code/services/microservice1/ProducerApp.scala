package main.DayWiseTasks.Assignments.day9.Code.services.microservice1


import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import spray.json._
import JsonFormats._

import java.util.Properties

case class Message(messageType: String, message: String, messageKey: String)
case class ProcessMessage(message: String, messageKey: String)

object JsonFormats {
  implicit val messageFormat: RootJsonFormat[Message] = jsonFormat3(Message)
  implicit val processMessageFormat: RootJsonFormat[ProcessMessage] = jsonFormat2(ProcessMessage)
}

object MessageTypes {
  val NETWORK_MESSAGE = "NETWORK"
  val APP_MESSAGE = "APP"
  val CLOUD_MESSAGE = "CLOUD"
}

class MessageHandler(networkMessageActor: ActorRef, appMessageActor: ActorRef, cloudMessageActor: ActorRef) extends Actor {
  def receive: Receive = {
    case Message(messageType, message, messageKey) =>
      messageType match {
        case MessageTypes.NETWORK_MESSAGE => networkMessageActor ! ProcessMessage(message, messageKey)
        case MessageTypes.APP_MESSAGE => appMessageActor ! ProcessMessage(message, messageKey)
        case MessageTypes.CLOUD_MESSAGE => cloudMessageActor ! ProcessMessage(message, messageKey)
      }
  }
}

class NetworkMessageProcessor(producer: KafkaProducer[String, String]) extends Actor {
  def receive: Receive = {
    case pm: ProcessMessage =>
      val record = new ProducerRecord[String, String]("network-message", pm.toJson.toString())
      producer.send(record)
  }
}

class AppMessageProcessor(producer: KafkaProducer[String, String]) extends Actor {
  def receive: Receive = {
    case pm: ProcessMessage =>
      val record = new ProducerRecord[String, String]("app-message", pm.toJson.toString())
      producer.send(record)
  }
}

class CloudMessageProcessor(producer: KafkaProducer[String, String]) extends Actor {
  def receive: Receive = {
    case pm: ProcessMessage =>
      val record = new ProducerRecord[String, String]("cloud-message", pm.toJson.toString())
      producer.send(record)
  }
}

object WebServer {
  implicit val system = ActorSystem("MessagingSystem")
  val producer: KafkaProducer[String, String] = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  val networkMessageProcessor: ActorRef = system.actorOf(Props(new NetworkMessageProcessor(producer)), "NetworkMessageProcessor")
  val appMessageProcessor: ActorRef = system.actorOf(Props(new AppMessageProcessor(producer)), "AppMessageProcessor")
  val cloudMessageProcessor: ActorRef = system.actorOf(Props(new CloudMessageProcessor(producer)), "CloudMessageProcessor")

  val messageHandler: ActorRef = system.actorOf(Props(new MessageHandler(networkMessageProcessor, appMessageProcessor, cloudMessageProcessor)))

  def main(args: Array[String]): Unit = {
    val route = post {
      path("process-message") {
        entity(as[Message]) { message =>
          messageHandler ! message
          complete(StatusCodes.OK, "Message processed")
        }
      }
    }
    Http().newServerAt("0.0.0.0", 8080).bind(route)
    println("Server started at http://0.0.0.0:8080/")
  }
}
