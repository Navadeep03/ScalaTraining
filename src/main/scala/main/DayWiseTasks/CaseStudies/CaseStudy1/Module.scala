package main.DayWiseTasks.CaseStudies.CaseStudy1

import com.google.inject.AbstractModule
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories._
import main.DayWiseTasks.CaseStudies.CaseStudy1.services._

class Module extends AbstractModule {
  override def configure(): Unit = {
    // Repositories
    bind(classOf[BookingRepository]).asEagerSingleton()
    bind(classOf[GuestRepository]).asEagerSingleton()
    bind(classOf[NotificationRepository]).asEagerSingleton()
    bind(classOf[RoomRepository]).asEagerSingleton()

    // Services
    bind(classOf[BookingService]).asEagerSingleton()
    bind(classOf[GuestService]).asEagerSingleton()
    bind(classOf[NotificationService]).asEagerSingleton()
    bind(classOf[RoomService]).asEagerSingleton()
  }
}
