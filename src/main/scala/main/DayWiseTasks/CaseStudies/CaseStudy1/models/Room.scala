package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Room(
                 id: String,
                 roomNumber: String,
                 roomType: String, // e.g., "Deluxe", "Luxury", "Suite"
                 floor: Int,
                 isAvailable: Boolean
               )

object Room {
  implicit val format: OFormat[Room] = Json.format[Room]
}
