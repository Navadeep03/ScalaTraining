package main.DayWiseTasks.CaseStudies.CaseStudy1.modules

import akka.stream.Materializer
import play.api.inject.{Binding, Module}
import play.api.{Configuration, Environment}
import play.api.mvc._
import play.api.routing.Router
import play.api.routing.sird._
import play.filters.HttpFiltersComponents
import play.api.http.{HttpConfiguration, HttpErrorHandler}
import main.DayWiseTasks.CaseStudies.CaseStudy1.controllers._
import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.consumer.KafkaConsumerService
import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.producer.KafkaProducerService
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories._
import main.DayWiseTasks.CaseStudies.CaseStudy1.services._
import org.mongodb.scala._

import javax.inject.{Inject, Provider}
import scala.concurrent.ExecutionContext

// Module class should extend the play.api.inject.Module class
class Module extends play.api.inject.Module {

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      // Bind RouterProvider as a dependency for routing purposes
      bind[Router].toProvider[RouterProvider]
    )
  }
}

class RouterProvider @Inject()(
                                components: ControllerComponents,
                                configuration: Configuration,
                                httpErrorHandler: HttpErrorHandler,    // Manually inject HttpErrorHandler
                                materializer: Materializer             // Manually inject Materializer
                              )(implicit ec: ExecutionContext)
  extends Provider[Router] {

  // MongoDB Client
  private val mongoClient: MongoClient = MongoClient(configuration.get[String]("mongo.uri"))

  // Repositories
  private val bookingRepository = new BookingRepository(mongoClient)
  private val guestRepository = new GuestRepository(mongoClient)
  private val roomRepository = new RoomRepository(mongoClient)
  private val notificationRepository = new NotificationRepository(mongoClient)

  // Kafka Services
  private val kafkaProducerService = new KafkaProducerService()
  private val kafkaConsumerService = new KafkaConsumerService()

  // Application Services
  private val bookingService = new BookingService(bookingRepository)
  private val guestService = new GuestService(guestRepository)
  private val roomService = new RoomService(roomRepository)
  private val notificationService = new NotificationService(notificationRepository, kafkaProducerService)

  // Controllers
  private val bookingController = new BookingController(components, bookingService)
  private val guestController = new GuestController(components, guestService)
  private val roomController = new RoomController(components, roomService)
  private val notificationController = new NotificationController(components, notificationService)

  // Router for handling routes and mapping them to controllers
  override def get(): Router = Router.from {
    case GET(p"/bookings") => bookingController.getAllBookings
    case GET(p"/bookings/$id") => bookingController.getBookingById(id)
    case POST(p"/bookings") => bookingController.createBooking
    case DELETE(p"/bookings/$id") => bookingController.deleteBooking(id)

    case GET(p"/guests") => guestController.getAllGuests
    case GET(p"/guests/$id") => guestController.getGuestById(id)
    case POST(p"/guests") => guestController.addGuest
    case DELETE(p"/guests/$id") => guestController.deleteGuest(id)

    case GET(p"/rooms") => roomController.getAllRooms
    case GET(p"/rooms/$id") => roomController.getRoomById(id)
    case POST(p"/rooms") => roomController.addRoom
    case DELETE(p"/rooms/$id") => roomController.deleteRoom(id)

    case GET(p"/notifications") => notificationController.getAllNotifications
    case POST(p"/notifications") => notificationController.sendNotification
  }
}
