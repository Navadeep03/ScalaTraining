package services

import repositories.NotificationRepository
import models.Notification

class NotificationService(notificationRepository: NotificationRepository) {

  def sendNotification(notification: Notification): Unit = {
    if (notification.message.isEmpty || notification.recipient.isEmpty) {
      throw new IllegalArgumentException("Message and recipient are required.")
    }
    notificationRepository.saveNotification(notification)
  }

  def getAllNotifications(): List[Notification] = {
    notificationRepository.getAllNotifications()
  }

  def getNotificationById(id: String): Option[Notification] = {
    notificationRepository.getNotificationById(id)
  }

  def deleteNotification(id: String): Unit = {
    notificationRepository.deleteNotification(id)
  }
}
