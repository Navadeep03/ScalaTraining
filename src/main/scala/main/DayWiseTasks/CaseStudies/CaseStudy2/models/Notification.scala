package models

case class Notification(
                         id: String,
                         recipient: String, // Email, phone number, or user ID
                         message: String,
                         timestamp: String, // ISO 8601 formatted date-time
                         status: String // e.g., "Sent", "Failed", "Pending"
                       )
