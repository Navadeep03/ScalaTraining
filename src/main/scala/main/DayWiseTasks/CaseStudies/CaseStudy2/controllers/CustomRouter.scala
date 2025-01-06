package main.DayWiseTasks.CaseStudies.CaseStudy2.controllers

import controllers.VisitorController
import controllers.NotificationController

import javax.inject._
import play.api.routing.Router
import play.api.routing.sird._
import play.api.mvc._

@Singleton
class CustomRouter @Inject()(
                              visitorController: VisitorController,
                              notificationController: NotificationController
                            ) extends Router {

  override def routes: Router.Routes = {

    // Visitor Routes
    case GET(p"/visitors") =>
      visitorController.getAllVisitors

    case GET(p"/visitors/$visitorId") =>
      visitorController.getVisitorById(visitorId)

    case POST(p"/visitors") =>
      visitorController.addVisitor

    case PUT(p"/visitors/$visitorId") =>
      visitorController.updateVisitor(visitorId)

    case DELETE(p"/visitors/$visitorId") =>
      visitorController.deleteVisitor(visitorId)

    // Notification Routes
    case GET(p"/notifications") =>
      notificationController.getAllNotifications

    case GET(p"/notifications/$notificationId") =>
      notificationController.getNotificationById(notificationId)

    case POST(p"/notifications") =>
      notificationController.sendNotification

    case DELETE(p"/notifications/$notificationId") =>
      notificationController.deleteNotification(notificationId)
  }

  override def documentation: Seq[(String, String, String)] = Seq(
    ("GET", "/visitors", "Get all visitors"),
    ("GET", "/visitors/:visitorId", "Get visitor by ID"),
    ("POST", "/visitors", "Add a visitor"),
    ("PUT", "/visitors/:visitorId", "Update visitor details"),
    ("DELETE", "/visitors/:visitorId", "Delete a visitor"),
    ("GET", "/notifications", "Get all notifications"),
    ("GET", "/notifications/:notificationId", "Get notification by ID"),
    ("POST", "/notifications", "Send a notification"),
    ("DELETE", "/notifications/:notificationId", "Delete a notification")
  )

  override def withPrefix(prefix: String): Router = Router.from {
    case prefixed if routes.isDefinedAt(prefixed) =>
      routes(prefixed)
  }
}
