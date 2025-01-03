package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

object Notification {
  case class Notification(
                           notificationId: String,
                           recipientEmail: String,
                           message: String,
                           timestamp: String
                         )

  implicit val notificationFormat: OFormat[Notification] = Json.format[Notification]
}
