package repositories

import models.Notification
import scala.collection.mutable

class NotificationRepository {

  private val notifications = mutable.Map[String, Notification]()

  def saveNotification(notification: Notification): Unit = {
    notifications(notification.id) = notification
  }

  def getAllNotifications(): List[Notification] = {
    notifications.values.toList
  }

  def getNotificationById(id: String): Option[Notification] = {
    notifications.get(id)
  }

  def deleteNotification(id: String): Unit = {
    notifications.remove(id)
  }
}
