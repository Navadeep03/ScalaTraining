package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import play.api.mvc._
import play.api.libs.json._
import javax.inject._
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.RoomService
import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room

@Singleton
class RoomController @Inject()(
                                cc: ControllerComponents,
                                roomService: RoomService
                              ) extends AbstractController(cc) {

  implicit val roomReads = Json.reads[Room]
  implicit val roomWrites = Json.writes[Room]

  /**
   * Endpoint: Get all rooms
   * Method: GET
   */
  def getAllRooms: Action[AnyContent] = Action {
    val rooms = roomService.getAllRooms()
    Ok(Json.obj("status" -> "success", "data" -> rooms))
  }

  /**
   * Endpoint: Get room details by ID
   * Method: GET
   * Path Parameter: roomId
   */
  def getRoomDetails(roomId: String): Action[AnyContent] = Action {
    roomService.getRoomById(roomId) match {
      case Some(room) => Ok(Json.toJson(room))
      case None       => NotFound(Json.obj("status" -> "error", "message" -> "Room not found"))
    }
  }

  /**
   * Endpoint: Add a new room
   * Method: POST
   * Request Body: JSON with room details
   */
  def addRoom: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Room].fold(
      errors => BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))),
      room => {
        roomService.addRoom(room)
        Created(Json.obj("status" -> "success", "message" -> s"Room ${room.roomNumber} added successfully"))
      }
    )
  }

  /**
   * Endpoint: Update room details
   * Method: PUT
   * Path Parameter: roomId
   */
  def updateRoom(roomId: String): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Room].fold(
      errors => BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))),
      room => {
        roomService.updateRoom(roomId, room)
        Ok(Json.obj("status" -> "success", "message" -> s"Room ${room.roomNumber} updated successfully"))
      }
    )
  }

  /**
   * Endpoint: Delete a room
   * Method: DELETE
   * Path Parameter: roomId
   */
  def deleteRoom(roomId: String): Action[AnyContent] = Action {
    if (roomService.deleteRoom(roomId)) {
      Ok(Json.obj("status" -> "success", "message" -> "Room deleted successfully"))
    } else {
      NotFound(Json.obj("status" -> "error", "message" -> "Room not found"))
    }
  }
}
