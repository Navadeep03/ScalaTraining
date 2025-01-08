package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Booking(
                    id: String,
                    guestId: String,
                    roomId: String,
                    checkInDate: String,
                    checkOutDate: String
                  )

object Booking {
  implicit val format: Format[Booking] = Json.format[Booking]
}
