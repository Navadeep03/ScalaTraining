package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import play.api.mvc._
import play.api.libs.json._
import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest.Guest
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.GuestService

object GuestController extends BaseController {
  private val controllerComponents: ControllerComponents = stubControllerComponents()

  override protected def controllerComponents: ControllerComponents = controllerComponents

  def getAllGuests(): Action[AnyContent] = Action {
    val guests = GuestService.getAllGuests()
    Ok(Json.toJson(guests))
  }

  def getGuest(guestId: String): Action[AnyContent] = Action {
    GuestService.getGuestById(guestId) match {
      case Some(guest) => Ok(Json.toJson(guest))
      case None        => NotFound(Json.obj("error" -> "Guest not found"))
    }
  }

  def addGuest(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Guest].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid guest data")),
      guest => {
        GuestService.addGuest(guest)
        Created(Json.obj("message" -> "Guest added successfully"))
      }
    )
  }

  def deleteGuest(guestId: String): Action[AnyContent] = Action {
    if (GuestService.deleteGuest(guestId)) Ok(Json.obj("message" -> "Guest deleted"))
    else NotFound(Json.obj("error" -> "Guest not found"))
  }
}
