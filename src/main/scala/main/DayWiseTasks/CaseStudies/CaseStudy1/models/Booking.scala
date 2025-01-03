package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

object Booking {
  case class Booking(
                      bookingId: String,
                      guestId: String,
                      roomId: String,
                      checkInDate: String,
                      checkOutDate: String
                    )

  implicit val bookingFormat: OFormat[Booking] = Json.format[Booking]
}
