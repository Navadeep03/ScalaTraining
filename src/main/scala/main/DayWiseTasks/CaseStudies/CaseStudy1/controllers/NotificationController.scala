package main.DayWiseTasks.CaseStudies.CaseStudy1.controllers

import main.DayWiseTasks.CaseStudies.FacilityManagement.services.NotificationService
import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Notification.Notification
import play.api.mvc._
import play.api.libs.json._

import javax.inject._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class NotificationController @Inject()(cc: ControllerComponents, notificationService: NotificationService) extends AbstractController(cc) {

  def getAllNotifications: Action[AnyContent] = Action.async {
    notificationService.getAllNotifications.map { notifications =>
      Ok(Json.toJson(notifications))
    }
  }

  def sendNotification: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Notification].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid data"))),
      notification => notificationService.sendNotification(notification).map { _ =>
        Created(Json.obj("message" -> "Notification sent"))
      }
    )
  }
}
