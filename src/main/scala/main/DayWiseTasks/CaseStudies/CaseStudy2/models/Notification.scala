package main.DayWiseTasks.CaseStudies.CaseStudy2.models

import play.api.libs.json._

object Notification {
  case class Notification(
                           notificationId: String,    // Unique ID for the notification
                           recipient: String,         // Recipient of the notification (e.g., email or ID)
                           message: String,           // Content of the notification
                           timestamp: String          // Time when the notification was created
                         )

  implicit val notificationFormat: OFormat[Notification] = Json.format[Notification]
}
