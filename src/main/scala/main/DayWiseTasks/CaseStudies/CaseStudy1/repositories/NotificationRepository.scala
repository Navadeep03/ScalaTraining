package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Notification
import scala.collection.mutable

class NotificationRepository {

  private val notifications = mutable.Map[String, Notification]()

  def findAll(): Seq[Notification] = notifications.values.toSeq

  def findById(id: String): Option[Notification] = notifications.get(id)

  def save(notification: Notification): Unit = notifications.put(notification.id, notification)

  def delete(id: String): Boolean = notifications.remove(id).isDefined
}
