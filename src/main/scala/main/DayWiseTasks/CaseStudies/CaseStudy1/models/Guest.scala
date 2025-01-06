package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Guest(
                  id: String,
                  name: String,
                  email: String,
                  phone: String,
                  idProof: String
                )

object Guest {
  implicit val format: OFormat[Guest] = Json.format[Guest]
}
