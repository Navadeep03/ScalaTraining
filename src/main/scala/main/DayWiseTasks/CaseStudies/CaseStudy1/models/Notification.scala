package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Notification(
                         id: String,
                         recipient: String,
                         message: String,
                         timestamp: Long,
                         status: String // e.g., "sent", "pending"
                       )

object Notification {
  implicit val format: Format[Notification] = Json.format[Notification]
}
