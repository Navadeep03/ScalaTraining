package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import main.DayWiseTasks.CaseStudies.FacilityManagement.models.Booking
import main.DayWiseTasks.CaseStudies.FacilityManagement.services.BookingService
import play.api.mvc._
import play.api.libs.json._

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class BookingController @Inject()(cc: ControllerComponents, bookingService: BookingService) extends AbstractController(cc) {

  def getAllBookings: Action[AnyContent] = Action.async {
    bookingService.getAllBookings.map { bookings =>
      Ok(Json.toJson(bookings))
    }
  }

  def getBookingById(id: String): Action[AnyContent] = Action.async {
    bookingService.getBookingById(id).map {
      case Some(booking) => Ok(Json.toJson(booking))
      case None => NotFound(Json.obj("error" -> "Booking not found"))
    }
  }

  def createBooking: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Booking].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid data"))),
      booking => bookingService.createBooking(booking).map { _ =>
        Created(Json.obj("message" -> "Booking created"))
      }
    )
  }

  def deleteBooking(id: String): Action[AnyContent] = Action.async {
    bookingService.deleteBooking(id).map {
      case true => Ok(Json.obj("message" -> "Booking deleted"))
      case false => NotFound(Json.obj("error" -> "Booking not found"))
    }
  }
}
