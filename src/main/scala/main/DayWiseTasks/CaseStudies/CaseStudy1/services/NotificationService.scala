package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Notification.Notification
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.NotificationRepository

object NotificationService {
  def getAllNotifications(): List[Notification] = NotificationRepository.getAllNotifications

  def sendNotification(notification: Notification): Unit = NotificationRepository.addNotification(notification)
}
