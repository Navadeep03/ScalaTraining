package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import javax.inject._
import play.api.routing.Router
import play.api.routing.sird._
import play.api.mvc._

@Singleton
class CustomRouter @Inject()(
                              bookingController: BookingController,
                              guestController: GuestController,
                              notificationController: NotificationController,
                              roomController: RoomController
                            ) extends Router {

  override def routes: Router.Routes = {

    // Booking Routes
    case GET(p"/bookings") =>
      bookingController.getAllBookings()

    case GET(p"/bookings/$bookingId") =>
      bookingController.getBookingById(bookingId)

    case POST(p"/bookings") =>
      bookingController.createBooking()

    case PUT(p"/bookings/$bookingId") =>
      bookingController.updateBooking(bookingId)

    case DELETE(p"/bookings/$bookingId") =>
      bookingController.cancelBooking(bookingId)

    // Guest Routes
    case GET(p"/guests") =>
      guestController.getAllGuests()

    case GET(p"/guests/$guestId") =>
      guestController.getGuestDetails(guestId)

    case POST(p"/guests") =>
      guestController.addGuest()

    case PUT(p"/guests/$guestId") =>
      guestController.updateGuest(guestId)

    case DELETE(p"/guests/$guestId") =>
      guestController.deleteGuest(guestId)

    // Notification Routes
    case GET(p"/notifications") =>
      notificationController.getAllNotifications()

    case GET(p"/notifications/$notificationId") =>
      notificationController.getNotification(notificationId)

    case POST(p"/notifications") =>
      notificationController.createNotification()

    case DELETE(p"/notifications/$notificationId") =>
      notificationController.deleteNotification(notificationId)

    // Room Routes
    case GET(p"/rooms") =>
      roomController.getAllRooms()

    case GET(p"/rooms/$roomId") =>
      roomController.getRoomDetails(roomId)

    case POST(p"/rooms") =>
      roomController.addRoom()

    case PUT(p"/rooms/$roomId") =>
      roomController.updateRoom(roomId)

    case DELETE(p"/rooms/$roomId") =>
      roomController.deleteRoom(roomId)
  }

  override def documentation: Seq[(String, String, String)] = Seq(
    // Booking Documentation
    ("GET", "/bookings", "Get all bookings"),
    ("GET", "/bookings/:bookingId", "Get booking by ID"),
    ("POST", "/bookings", "Create a booking"),
    ("PUT", "/bookings/:bookingId", "Update a booking"),
    ("DELETE", "/bookings/:bookingId", "Cancel a booking"),

    // Guest Documentation
    ("GET", "/guests", "Get all guests"),
    ("GET", "/guests/:guestId", "Get guest details"),
    ("POST", "/guests", "Add a guest"),
    ("PUT", "/guests/:guestId", "Update guest details"),
    ("DELETE", "/guests/:guestId", "Delete a guest"),

    // Notification Documentation
    ("GET", "/notifications", "Get all notifications"),
    ("GET", "/notifications/:notificationId", "Get notification by ID"),
    ("POST", "/notifications", "Create a notification"),
    ("DELETE", "/notifications/:notificationId", "Delete a notification"),

    // Room Documentation
    ("GET", "/rooms", "Get all rooms"),
    ("GET", "/rooms/:roomId", "Get room details"),
    ("POST", "/rooms", "Add a room"),
    ("PUT", "/rooms/:roomId", "Update room details"),
    ("DELETE", "/rooms/:roomId", "Delete a room")
  )

  override def withPrefix(prefix: String): Router = {
    val prefixedRoutes: Router.Routes = {
      case path if routes.isDefinedAt(path) => routes(path)
    }
    Router.from(prefixedRoutes)
  }
}
