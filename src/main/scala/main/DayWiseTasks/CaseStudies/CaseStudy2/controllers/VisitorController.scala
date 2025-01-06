package controllers

import services.VisitorService
import models.Visitor

class VisitorController(visitorService: VisitorService) {

  def addVisitor(visitor: Visitor): String = {
    visitorService.addVisitor(visitor)
    s"Visitor ${visitor.name} added successfully."
  }

  def getAllVisitors(): List[Visitor] = {
    visitorService.getAllVisitors()
  }

  def getVisitorById(id: String): Option[Visitor] = {
    visitorService.getVisitorById(id)
  }

  def updateVisitor(id: String, updatedVisitor: Visitor): String = {
    visitorService.updateVisitor(id, updatedVisitor)
    s"Visitor with ID $id updated successfully."
  }

  def deleteVisitor(id: String): String = {
    visitorService.deleteVisitor(id)
    s"Visitor with ID $id deleted successfully."
  }
}
