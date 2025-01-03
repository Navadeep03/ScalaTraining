package main.DayWiseTasks.CaseStudies.CaseStudy2

import play.api.mvc._
import play.api.routing.Router
import play.api.routing.sird._
import play.api.{ApplicationLoader, BuiltInComponentsFromContext, Application}
import play.filters.HttpFiltersComponents
import akka.actor.ActorSystem
import main.DayWiseTasks.CaseStudies.CaseStudy2.controllers._

object MainApp extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application = {
    new BuiltInComponentsFromContext(context) with HttpFiltersComponents {

      // Initialize ActorSystem
      override implicit val actorSystem: ActorSystem = ActorSystem("VisitorManagementSystem")

      // Controllers
      val visitorController: VisitorController.type = VisitorController
      val notificationController: NotificationController.type = NotificationController

      // Router
      override def router: Router = Router.from {
        // Visitor Routes
        case GET(p"/visitors") => visitorController.getAllVisitors()
        case GET(p"/visitors/$visitorId") => visitorController.getVisitor(visitorId)
        case POST(p"/visitors") => visitorController.addVisitor()
        case PUT(p"/visitors/$visitorId") => visitorController.updateVisitor(visitorId)
        case PUT(p"/visitors/$visitorId/checkout") => visitorController.checkOutVisitor(visitorId)
        case DELETE(p"/visitors/$visitorId") => visitorController.deleteVisitor(visitorId)

        // Notification Routes
        case GET(p"/notifications") => notificationController.getAllNotifications()
        case GET(p"/notifications/$notificationId") => notificationController.getNotification(notificationId)
        case POST(p"/notifications") => notificationController.sendNotification()
        case DELETE(p"/notifications/$notificationId") => notificationController.deleteNotification(notificationId)
      }

    }.application
  }
}
