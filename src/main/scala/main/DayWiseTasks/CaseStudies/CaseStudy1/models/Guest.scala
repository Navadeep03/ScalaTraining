package main.DayWiseTasks.CaseStudies.CaseStudy1.models

import play.api.libs.json._

case class Guest(
                  id: String,
                  name: String,
                  contact: String,
                  email: String
                )

object Guest {
  implicit val format: Format[Guest] = Json.format[Guest]
}
