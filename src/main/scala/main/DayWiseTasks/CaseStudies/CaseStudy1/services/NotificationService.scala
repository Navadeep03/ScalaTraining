package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Notification
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.NotificationRepository
import javax.inject.Inject

import java.time.ZonedDateTime
import java.util.UUID

class NotificationService @Inject()(notificationRepository: NotificationRepository) {

  def getAllNotifications(): Seq[Notification] = notificationRepository.findAll()

  def getNotificationById(id: String): Option[Notification] = notificationRepository.findById(id)

  def createNotification(recipient: String, message: String): Notification = {
    val notification = Notification(
      id = UUID.randomUUID().toString,
      recipient = recipient,
      message = message,
      timestamp = ZonedDateTime.now().toString
    )
    notificationRepository.save(notification)
    notification
  }

  def deleteNotification(id: String): Boolean = notificationRepository.delete(id)
}
