package main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.events

case class RoomEvent(
                      roomId: String,
                      status: String // e.g., "available", "booked"
                    )
