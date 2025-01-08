package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import main.DayWiseTasks.CaseStudies.FacilityManagement.models.Room.Room
import main.DayWiseTasks.CaseStudies.FacilityManagement.services.RoomService
import play.api.mvc._
import play.api.libs.json._

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class RoomController @Inject()(cc: ControllerComponents, roomService: RoomService) extends AbstractController(cc) {

  def getAllRooms: Action[AnyContent] = Action.async {
    roomService.getAllRooms.map { rooms =>
      Ok(Json.toJson(rooms))
    }
  }

  def getRoomById(id: String): Action[AnyContent] = Action.async {
    roomService.getRoomById(id).map {
      case Some(room) => Ok(Json.toJson(room))
      case None => NotFound(Json.obj("error" -> "Room not found"))
    }
  }

  def addRoom: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Room].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid data"))),
      room => roomService.addRoom(room).map { _ =>
        Created(Json.obj("message" -> "Room added"))
      }
    )
  }

  def deleteRoom(id: String): Action[AnyContent] = Action.async {
    roomService.deleteRoom(id).map {
      case true => Ok(Json.obj("message" -> "Room deleted"))
      case false => NotFound(Json.obj("error" -> "Room not found"))
    }
  }
}
