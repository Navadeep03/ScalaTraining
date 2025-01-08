package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Room(
                 id: String,
                 roomType: String,
                 status: String, // e.g., "available", "booked"
                 price: Double
               )

object Room {
  implicit val format: Format[Room] = Json.format[Room]
}
