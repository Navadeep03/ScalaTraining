package main.DayWiseTasks.CaseStudies.CaseStudy2.services

import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Visitor.Visitor
import main.DayWiseTasks.CaseStudies.CaseStudy2.repositories.VisitorRepository

object VisitorService {
  def getAllVisitors(): List[Visitor] = VisitorRepository.getAllVisitors

  def getVisitorById(visitorId: String): Option[Visitor] = VisitorRepository.getVisitorById(visitorId)

  def addVisitor(visitor: Visitor): Unit = VisitorRepository.addVisitor(visitor)

  def updateVisitor(visitorId: String, updatedVisitor: Visitor): Boolean =
    VisitorRepository.updateVisitor(visitorId, updatedVisitor)

  def checkOutVisitor(visitorId: String): Boolean = VisitorRepository.checkOutVisitor(visitorId)

  def deleteVisitor(visitorId: String): Boolean = VisitorRepository.deleteVisitor(visitorId)
}
