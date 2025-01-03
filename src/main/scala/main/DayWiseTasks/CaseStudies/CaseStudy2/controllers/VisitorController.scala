package main.DayWiseTasks.CaseStudies.CaseStudy2.controllers

import play.api.mvc._
import play.api.libs.json._
import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Visitor.Visitor
import main.DayWiseTasks.CaseStudies.CaseStudy2.services.VisitorService

object VisitorController extends BaseController {
  private val controllerComponents: ControllerComponents = stubControllerComponents()

  override protected def controllerComponents: ControllerComponents = controllerComponents

  // Get all visitors
  def getAllVisitors(): Action[AnyContent] = Action {
    val visitors = VisitorService.getAllVisitors()
    Ok(Json.toJson(visitors))
  }

  // Get a specific visitor by ID
  def getVisitor(visitorId: String): Action[AnyContent] = Action {
    VisitorService.getVisitorById(visitorId) match {
      case Some(visitor) => Ok(Json.toJson(visitor))
      case None          => NotFound(Json.obj("error" -> "Visitor not found"))
    }
  }

  // Add a new visitor
  def addVisitor(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Visitor].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid visitor data")),
      visitor => {
        VisitorService.addVisitor(visitor)
        Created(Json.obj("message" -> "Visitor added successfully"))
      }
    )
  }

  // Update visitor details
  def updateVisitor(visitorId: String): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Visitor].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid visitor data")),
      updatedVisitor => {
        if (VisitorService.updateVisitor(visitorId, updatedVisitor))
          Ok(Json.obj("message" -> "Visitor updated successfully"))
        else
          NotFound(Json.obj("error" -> "Visitor not found"))
      }
    )
  }

  // Check out a visitor
  def checkOutVisitor(visitorId: String): Action[AnyContent] = Action {
    if (VisitorService.checkOutVisitor(visitorId)) Ok(Json.obj("message" -> "Visitor checked out"))
    else NotFound(Json.obj("error" -> "Visitor not found or already checked out"))
  }

  // Delete a visitor
  def deleteVisitor(visitorId: String): Action[AnyContent] = Action {
    if (VisitorService.deleteVisitor(visitorId)) Ok(Json.obj("message" -> "Visitor deleted successfully"))
    else NotFound(Json.obj("error" -> "Visitor not found"))
  }
}
