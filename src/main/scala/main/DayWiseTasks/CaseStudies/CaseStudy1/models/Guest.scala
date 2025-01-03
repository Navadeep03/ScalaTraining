package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

object Guest {
  case class Guest(
                    guestId: String,
                    name: String,
                    email: String,
                    phoneNumber: String,
                    idProof: String
                  )

  implicit val guestFormat: OFormat[Guest] = Json.format[Guest]
}
