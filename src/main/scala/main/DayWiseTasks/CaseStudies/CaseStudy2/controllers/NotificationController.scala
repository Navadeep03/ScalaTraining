package controllers

import services.NotificationService
import models.Notification

class NotificationController(notificationService: NotificationService) {

  def sendNotification(notification: Notification): String = {
    notificationService.sendNotification(notification)
    s"Notification sent to ${notification.recipient}."
  }

  def getAllNotifications(): List[Notification] = {
    notificationService.getAllNotifications()
  }

  def getNotificationById(id: String): Option[Notification] = {
    notificationService.getNotificationById(id)
  }

  def deleteNotification(id: String): String = {
    notificationService.deleteNotification(id)
    s"Notification with ID $id deleted successfully."
  }
}
