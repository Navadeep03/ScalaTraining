package main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.events

case class BookingEvent(
                         id: String,
                         guestId: String,
                         roomId: String,
                         status: String // e.g., "created", "cancelled"
                       )
