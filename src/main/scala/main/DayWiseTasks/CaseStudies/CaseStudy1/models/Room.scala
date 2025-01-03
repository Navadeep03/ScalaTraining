package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

object Room {
  case class Room(
                   roomId: String,
                   roomType: String,
                   availability: Boolean,
                   floorNumber: Int
                 )

  implicit val roomFormat: OFormat[Room] = Json.format[Room]
}
