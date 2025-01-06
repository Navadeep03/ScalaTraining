package services

import repositories.VisitorRepository
import models.Visitor

class VisitorService(visitorRepository: VisitorRepository) {

  def addVisitor(visitor: Visitor): Unit = {
    if (visitor.name.isEmpty || visitor.contactNumber.isEmpty) {
      throw new IllegalArgumentException("Name and contact number are required.")
    }
    visitorRepository.addVisitor(visitor)
  }

  def getAllVisitors(): List[Visitor] = {
    visitorRepository.getAllVisitors()
  }

  def getVisitorById(id: String): Option[Visitor] = {
    visitorRepository.getVisitorById(id)
  }

  def updateVisitor(id: String, updatedVisitor: Visitor): Unit = {
    visitorRepository.updateVisitor(id, updatedVisitor)
  }

  def deleteVisitor(id: String): Unit = {
    visitorRepository.deleteVisitor(id)
  }
}
