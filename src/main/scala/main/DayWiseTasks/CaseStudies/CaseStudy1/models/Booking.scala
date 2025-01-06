package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Booking(
                    id: String,
                    roomId: String,
                    guestId: String,
                    startDate: String, // YYYY-MM-DD
                    endDate: String,   // YYYY-MM-DD
                    status: String     // e.g., "Booked", "CheckedIn", "CheckedOut", "Cancelled"
                  )

object Booking {
  implicit val format: OFormat[Booking] = Json.format[Booking]
}
