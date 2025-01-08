package main.DayWiseTasks.CaseStudies.CaseStudy1.notifications

import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.producer.KafkaProducerService

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RoomServiceNotifications @Inject()(kafkaProducerService: KafkaProducerService)(implicit ec: ExecutionContext) {

  private val topic = "RoomServiceNotifications"

  def sendWelcomeEmail(guestId: String, roomId: String, emergencyContact: String): Future[Unit] = {
    val message = s"Welcome to your room! Emergency contact: $emergencyContact, Room: $roomId"
    kafkaProducerService.publishMessage(topic, guestId, message)
  }

  def notifyCleaningService(roomId: String): Future[Unit] = {
    val message = s"Room $roomId is ready for cleaning."
    kafkaProducerService.publishMessage(topic, roomId, message)
  }
}
