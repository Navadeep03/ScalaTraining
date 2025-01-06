package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import play.api.mvc._
import play.api.libs.json._
import javax.inject._
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.GuestService
import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest

@Singleton
class GuestController @Inject()(
                                 cc: ControllerComponents,
                                 guestService: GuestService
                               ) extends AbstractController(cc) {

  implicit val guestReads = Json.reads[Guest]
  implicit val guestWrites = Json.writes[Guest]

  /**
   * Endpoint: Get all guests
   * Method: GET
   */
  def getAllGuests: Action[AnyContent] = Action {
    val guests = guestService.getAllGuests()
    Ok(Json.obj("status" -> "success", "data" -> guests))
  }

  /**
   * Endpoint: Get guest by ID
   * Method: GET
   * Path Parameter: guestId
   */
  def getGuestDetails(guestId: String): Action[AnyContent] = Action {
    guestService.getGuestById(guestId) match {
      case Some(guest) => Ok(Json.toJson(guest))
      case None        => NotFound(Json.obj("status" -> "error", "message" -> "Guest not found"))
    }
  }

  /**
   * Endpoint: Add a new guest
   * Method: POST
   * Request Body: JSON with guest details
   */
  def addGuest: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Guest].fold(
      errors => BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))),
      guest => {
        guestService.addGuest(guest)
        Created(Json.obj("status" -> "success", "message" -> s"Guest ${guest.name} added successfully"))
      }
    )
  }

  /**
   * Endpoint: Update guest details
   * Method: PUT
   * Path Parameter: guestId
   */
  def updateGuest(guestId: String): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Guest].fold(
      errors => BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))),
      guest => {
        guestService.updateGuest(guestId, guest)
        Ok(Json.obj("status" -> "success", "message" -> s"Guest ${guest.name} updated successfully"))
      }
    )
  }

  /**
   * Endpoint: Delete a guest
   * Method: DELETE
   * Path Parameter: guestId
   */
  def deleteGuest(guestId: String): Action[AnyContent] = Action {
    if (guestService.deleteGuest(guestId)) {
      Ok(Json.obj("status" -> "success", "message" -> "Guest deleted successfully"))
    } else {
      NotFound(Json.obj("status" -> "error", "message" -> "Guest not found"))
    }
  }
}
