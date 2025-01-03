package main.DayWiseTasks.CaseStudies.CaseStudy2.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Notification.Notification

import scala.collection.mutable

object NotificationRepository {
  private val notifications = mutable.ListBuffer[Notification]()

  def getAllNotifications: List[Notification] = notifications.toList

  def getNotificationById(notificationId: String): Option[Notification] = notifications.find(_.notificationId == notificationId)

  def addNotification(notification: Notification): Unit = notifications += notification

  def deleteNotification(notificationId: String): Boolean = {
    getNotificationById(notificationId).exists { notification =>
      notifications -= notification
      true
    }
  }
}
