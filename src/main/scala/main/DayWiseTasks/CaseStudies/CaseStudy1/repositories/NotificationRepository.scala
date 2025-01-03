package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Notification.Notification

import scala.collection.mutable

object NotificationRepository {
  private val notifications = mutable.ListBuffer[Notification]()

  def getAllNotifications: List[Notification] = notifications.toList

  def addNotification(notification: Notification): Unit = notifications += notification
}
