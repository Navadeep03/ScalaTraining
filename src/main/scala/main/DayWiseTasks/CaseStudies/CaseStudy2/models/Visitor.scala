package main.DayWiseTasks.CaseStudies.CaseStudy2.models

import play.api.libs.json._

object Visitor {
  case class Visitor(
                      visitorId: String,         // Unique ID for the visitor
                      name: String,              // Visitor's full name
                      contact: String,           // Visitor's contact number or email
                      purpose: String,           // Reason for visiting
                      hostEmployee: String,      // Host employee's name or ID
                      checkInTime: Option[String] = None,   // Optional check-in time
                      checkOutTime: Option[String] = None,  // Optional check-out time
                      idProof: String            // Path or URL to ID proof document
                    )

  implicit val visitorFormat: OFormat[Visitor] = Json.format[Visitor]
}
