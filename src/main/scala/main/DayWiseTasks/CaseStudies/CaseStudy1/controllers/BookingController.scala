package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import play.api.mvc._
import play.api.libs.json._
import javax.inject._
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.BookingService
import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking

@Singleton
class BookingController @Inject()(
                                   cc: ControllerComponents,
                                   bookingService: BookingService
                                 ) extends AbstractController(cc) {

  implicit val bookingReads = Json.reads[Booking]
  implicit val bookingWrites = Json.writes[Booking]

  /**
   * Endpoint: Get all bookings
   * Method: GET
   */
  def getAllBookings: Action[AnyContent] = Action {
    val bookings = bookingService.getAllBookings()
    Ok(Json.obj("status" -> "success", "data" -> bookings))
  }

  /**
   * Endpoint: Get booking by ID
   * Method: GET
   * Path Parameter: bookingId
   */
  def getBookingById(bookingId: String): Action[AnyContent] = Action {
    bookingService.getBookingById(bookingId) match {
      case Some(booking) => Ok(Json.toJson(booking))
      case None          => NotFound(Json.obj("status" -> "error", "message" -> "Booking not found"))
    }
  }

  /**
   * Endpoint: Create a booking
   * Method: POST
   * Request Body: JSON with booking details
   */
  def createBooking: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Booking].fold(
      errors => BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))),
      booking => {
        bookingService.createBooking(booking)
        Created(Json.obj("status" -> "success", "message" -> s"Booking created successfully", "bookingId" -> booking.id))
      }
    )
  }

  /**
   * Endpoint: Update a booking
   * Method: PUT
   * Path Parameter: bookingId
   */
  def updateBooking(bookingId: String): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Booking].fold(
      errors => BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))),
      booking => {
        bookingService.updateBooking(bookingId, booking)
        Ok(Json.obj("status" -> "success", "message" -> "Booking updated successfully"))
      }
    )
  }

  /**
   * Endpoint: Cancel a booking
   * Method: DELETE
   * Path Parameter: bookingId
   */
  def cancelBooking(bookingId: String): Action[AnyContent] = Action {
    if (bookingService.cancelBooking(bookingId)) {
      Ok(Json.obj("status" -> "success", "message" -> "Booking cancelled successfully"))
    } else {
      NotFound(Json.obj("status" -> "error", "message" -> "Booking not found"))
    }
  }
}
