package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import play.api.mvc._
import play.api.libs.json._
import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Notification.Notification
import main.DayWiseTasks.CaseStudies.CaseStudy1.services.NotificationService

object NotificationController extends BaseController {
  private val controllerComponents: ControllerComponents = stubControllerComponents()

  override protected def controllerComponents: ControllerComponents = controllerComponents

  def getAllNotifications(): Action[AnyContent] = Action {
    val notifications = NotificationService.getAllNotifications()
    Ok(Json.toJson(notifications))
  }

  def sendNotification(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Notification].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid notification data")),
      notification => {
        NotificationService.sendNotification(notification)
        Created(Json.obj("message" -> "Notification sent"))
      }
    )
  }
}
