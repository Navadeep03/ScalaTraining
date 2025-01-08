package main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.events

case class GuestEvent(
                       id: String,
                       name: String,
                       action: String // e.g., "checked_in", "checked_out"
                     )
