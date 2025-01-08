package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import main.DayWiseTasks.CaseStudies.FacilityManagement.models.Guest.Guest
import main.DayWiseTasks.CaseStudies.FacilityManagement.services.GuestService
import play.api.mvc._
import play.api.libs.json._

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class GuestController @Inject()(cc: ControllerComponents, guestService: GuestService) extends AbstractController(cc) {

  def getAllGuests: Action[AnyContent] = Action.async {
    guestService.getAllGuests.map { guests =>
      Ok(Json.toJson(guests))
    }
  }

  def getGuestById(id: String): Action[AnyContent] = Action.async {
    guestService.getGuestById(id).map {
      case Some(guest) => Ok(Json.toJson(guest))
      case None => NotFound(Json.obj("error" -> "Guest not found"))
    }
  }

  def addGuest: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Guest].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid data"))),
      guest => guestService.addGuest(guest).map { _ =>
        Created(Json.obj("message" -> "Guest added"))
      }
    )
  }

  def deleteGuest(id: String): Action[AnyContent] = Action.async {
    guestService.deleteGuest(id).map {
      case true => Ok(Json.obj("message" -> "Guest deleted"))
      case false => NotFound(Json.obj("error" -> "Guest not found"))
    }
  }
}
