package main.DayWiseTasks.CaseStudies.CaseStudy2.services

import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Notification.Notification
import main.DayWiseTasks.CaseStudies.CaseStudy2.repositories.NotificationRepository

object NotificationService {
  def getAllNotifications: List[Notification] = NotificationRepository.getAllNotifications

  def getNotificationById(notificationId: String): Option[Notification] = NotificationRepository.getNotificationById(notificationId)

  def sendNotification(notification: Notification): Unit = NotificationRepository.addNotification(notification)

  def deleteNotification(notificationId: String): Boolean = NotificationRepository.deleteNotification(notificationId)
}
