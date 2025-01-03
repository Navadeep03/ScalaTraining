package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import play.api.mvc._
import play.api.libs.json._
import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room.Room
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.RoomService

object RoomController extends BaseController {
  private val controllerComponents: ControllerComponents = stubControllerComponents()

  override protected def controllerComponents: ControllerComponents = controllerComponents

  def getAllRooms(): Action[AnyContent] = Action {
    val rooms = RoomService.getAllRooms()
    Ok(Json.toJson(rooms))
  }

  def getRoom(roomId: String): Action[AnyContent] = Action {
    RoomService.getRoomById(roomId) match {
      case Some(room) => Ok(Json.toJson(room))
      case None       => NotFound(Json.obj("error" -> "Room not found"))
    }
  }

  def addRoom(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Room].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid room data")),
      room => {
        RoomService.addRoom(room)
        Created(Json.obj("message" -> "Room added successfully"))
      }
    )
  }

  def deleteRoom(roomId: String): Action[AnyContent] = Action {
    if (RoomService.deleteRoom(roomId)) Ok(Json.obj("message" -> "Room deleted"))
    else NotFound(Json.obj("error" -> "Room not found"))
  }
}
