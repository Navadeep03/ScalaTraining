package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Notification(
                         id: String,
                         recipient: String,  // Email or other identifier
                         message: String,
                         timestamp: String   // ISO 8601 format, e.g., "2025-01-01T12:00:00Z"
                       )

object Notification {
  implicit val format: OFormat[Notification] = Json.format[Notification]
}
