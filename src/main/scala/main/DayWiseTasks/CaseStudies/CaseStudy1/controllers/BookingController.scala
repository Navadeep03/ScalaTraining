package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import play.api.mvc._
import play.api.libs.json._
import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking.Booking
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.BookingService

object BookingController extends BaseController {
  private val controllerComponents: ControllerComponents = stubControllerComponents()

  override protected def controllerComponents: ControllerComponents = controllerComponents

  def getAllBookings(): Action[AnyContent] = Action {
    val bookings = BookingService.getAllBookings()
    Ok(Json.toJson(bookings))
  }

  def getBooking(bookingId: String): Action[AnyContent] = Action {
    BookingService.getBookingById(bookingId) match {
      case Some(booking) => Ok(Json.toJson(booking))
      case None          => NotFound(Json.obj("error" -> "Booking not found"))
    }
  }

  def createBooking(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Booking].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid booking data")),
      booking => {
        BookingService.createBooking(booking)
        Created(Json.obj("message" -> "Booking created successfully"))
      }
    )
  }

  def deleteBooking(bookingId: String): Action[AnyContent] = Action {
    if (BookingService.cancelBooking(bookingId)) Ok(Json.obj("message" -> "Booking canceled"))
    else NotFound(Json.obj("error" -> "Booking not found"))
  }
}
