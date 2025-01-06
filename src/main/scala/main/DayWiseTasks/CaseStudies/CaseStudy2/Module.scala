package main.DayWiseTasks.CaseStudies.CaseStudy2

import com.google.inject.AbstractModule
import play.api.inject._
import play.api.inject.guice._
import main.DayWiseTasks.CaseStudies.CaseStudy1.controllers.NotificationController
import controllers.{CustomRouter, VisitorController}
import play.api.routing.Router
import repositories._
import services._

class Module extends AbstractModule {
  override def configure(): Unit = {
    // Bind Controllers
    bind(classOf[VisitorController]).asEagerSingleton()
    bind(classOf[NotificationController]).asEagerSingleton()

    // Bind Services
    bind(classOf[VisitorService]).to(classOf[VisitorService]).asEagerSingleton()
    bind(classOf[NotificationService]).to(classOf[NotificationService]).asEagerSingleton()

    // Bind Repositories
    bind(classOf[VisitorRepository]).to(classOf[VisitorRepository]).asEagerSingleton()
    bind(classOf[NotificationRepository]).to(classOf[NotificationRepository]).asEagerSingleton()

    // Bind Custom Router
    bind(classOf[Router]).to(classOf[CustomRouter]).asEagerSingleton()
  }
}
