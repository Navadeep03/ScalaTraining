package main.DayWiseTasks.CaseStudies.CaseStudy2.controllers

import play.api.mvc._
import play.api.libs.json._
import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Notification.Notification
import main.DayWiseTasks.CaseStudies.CaseStudy2.services.NotificationService

object NotificationController extends BaseController {
  private val controllerComponents: ControllerComponents = stubControllerComponents()

  override protected def controllerComponents: ControllerComponents = controllerComponents

  // Get all notifications
  def getAllNotifications(): Action[AnyContent] = Action {
    val notifications = NotificationService.getAllNotifications()
    Ok(Json.toJson(notifications))
  }

  // Get a specific notification by ID
  def getNotification(notificationId: String): Action[AnyContent] = Action {
    NotificationService.getNotificationById(notificationId) match {
      case Some(notification) => Ok(Json.toJson(notification))
      case None               => NotFound(Json.obj("error" -> "Notification not found"))
    }
  }

  // Send a notification
  def sendNotification(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Notification].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid notification data")),
      notification => {
        NotificationService.sendNotification(notification)
        Created(Json.obj("message" -> "Notification sent successfully"))
      }
    )
  }

  // Delete a notification
  def deleteNotification(notificationId: String): Action[AnyContent] = Action {
    if (NotificationService.deleteNotification(notificationId)) Ok(Json.obj("message" -> "Notification deleted successfully"))
    else NotFound(Json.obj("error" -> "Notification not found"))
  }
}
