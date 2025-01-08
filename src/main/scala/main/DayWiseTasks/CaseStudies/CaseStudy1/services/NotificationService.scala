package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.producer.KafkaProducerService
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.NotificationRepository
import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Notification.Notification

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class NotificationService @Inject()(
                                     notificationRepository: NotificationRepository,
                                     kafkaProducerService: KafkaProducerService
                                   )(implicit ec: ExecutionContext) {

  def getAllNotifications: Future[Seq[Notification]] = {
    notificationRepository.getAllNotifications
  }

  def sendNotification(notification: Notification): Future[Unit] = {
    kafkaProducerService.publishMessage("NotificationTopic", notification.id, notification.message)
    notificationRepository.saveNotification(notification)
  }
}
