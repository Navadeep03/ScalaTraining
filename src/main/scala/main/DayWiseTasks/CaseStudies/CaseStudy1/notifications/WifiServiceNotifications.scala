package main.DayWiseTasks.CaseStudies.CaseStudy1.notifications

import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.producer.KafkaProducerService

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class WifiServiceNotifications @Inject()(kafkaProducerService: KafkaProducerService)(implicit ec: ExecutionContext) {

  private val topic = "WifiServiceNotifications"

  def sendWifiCredentials(guestId: String, wifiUsername: String, wifiPassword: String): Future[Unit] = {
    val message = s"Your WiFi credentials are Username: $wifiUsername, Password: $wifiPassword"
    kafkaProducerService.publishMessage(topic, guestId, message)
  }
}
