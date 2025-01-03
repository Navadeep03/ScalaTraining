package main.DayWiseTasks.CaseStudies.CaseStudy1

import play.api.mvc._
import play.api.routing.Router
import play.api.routing.sird._
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, Application}
import play.filters.HttpFiltersComponents
import akka.actor.ActorSystem
import main.DayWiseTasks.CaseStudies.CaseStudy1.controllers._

object MainApp extends ApplicationLoader {

  override def load(context: ApplicationLoader.Context): Application = {
    new BuiltInComponentsFromContext(context) with HttpFiltersComponents {

      // Actor System Initialization
      override implicit val actorSystem: ActorSystem = ActorSystem("CaseStudy1System")

      // Controllers
      val roomController: RoomController.type = RoomController
      val bookingController: BookingController.type = BookingController
      val guestController: GuestController.type = GuestController
      val notificationController: NotificationController.type = NotificationController

      // Router Definition
      override def router: Router = Router.from {
        case GET(p"/rooms") => roomController.getAllRooms()
        case POST(p"/rooms") => roomController.addRoom()
        case GET(p"/bookings") => bookingController.getAllBookings()
        case POST(p"/bookings") => bookingController.createBooking()
        case GET(p"/guests") => guestController.getAllGuests()
        case POST(p"/guests") => guestController.addGuest()
        case GET(p"/notifications") => notificationController.getAllNotifications()
        case POST(p"/notifications") => notificationController.sendNotification()
      }

    }.application
  }
}
