package models

case class Visitor(
                    id: String,
                    name: String,
                    contactNumber: String,
                    purpose: String,
                    checkInTime: Option[String] = None,
                    checkOutTime: Option[String] = None
                  )
