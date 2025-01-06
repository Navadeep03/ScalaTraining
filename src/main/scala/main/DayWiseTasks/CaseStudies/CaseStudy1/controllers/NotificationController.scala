package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Notification
import play.api.mvc._
import play.api.libs.json._

import javax.inject._
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.NotificationService

@Singleton
class NotificationController @Inject()(
                                        cc: ControllerComponents,
                                        notificationService: NotificationService
                                      ) extends AbstractController(cc) {

  implicit val notificationWrites = Json.writes[Notification]

  /**
   * Endpoint: Get all notifications
   * Method: GET
   */
  def getAllNotifications: Action[AnyContent] = Action {
    val notifications = notificationService.getAllNotifications()
    Ok(Json.obj("status" -> "success", "data" -> notifications))
  }

  /**
   * Endpoint: Get notification by ID
   * Method: GET
   * Path Parameter: notificationId
   */
  def getNotification(notificationId: String): Action[AnyContent] = Action {
    notificationService.getNotificationById(notificationId) match {
      case Some(notification) => Ok(Json.toJson(notification))
      case None               => NotFound(Json.obj("status" -> "error", "message" -> "Notification not found"))
    }
  }

  /**
   * Endpoint: Create a notification
   * Method: POST
   * Request Body: JSON with recipient and message
   */
  def createNotification: Action[JsValue] = Action(parse.json) { request =>
    (request.body \ "recipient").asOpt[String].flatMap { recipient =>
      (request.body \ "message").asOpt[String].map { message =>
        val notification = notificationService.createNotification(recipient, message)
        Created(Json.obj("status" -> "success", "message" -> "Notification created", "data" -> notification))
      }
    }.getOrElse {
      BadRequest(Json.obj("status" -> "error", "message" -> "Invalid recipient or message"))
    }
  }

  /**
   * Endpoint: Delete a notification
   * Method: DELETE
   * Path Parameter: notificationId
   */
  def deleteNotification(notificationId: String): Action[AnyContent] = Action {
    if (notificationService.deleteNotification(notificationId)) {
      Ok(Json.obj("status" -> "success", "message" -> "Notification deleted"))
    } else {
      NotFound(Json.obj("status" -> "error", "message" -> "Notification not found"))
    }
  }
}
