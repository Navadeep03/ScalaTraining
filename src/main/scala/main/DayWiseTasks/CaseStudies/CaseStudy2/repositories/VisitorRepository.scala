package main.DayWiseTasks.CaseStudies.CaseStudy2.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Visitor.Visitor

import scala.collection.mutable

object VisitorRepository {
  private val visitors = mutable.ListBuffer[Visitor]()

  def getAllVisitors: List[Visitor] = visitors.toList

  def getVisitorById(visitorId: String): Option[Visitor] = visitors.find(_.visitorId == visitorId)

  def addVisitor(visitor: Visitor): Unit = visitors += visitor

  def updateVisitor(visitorId: String, updatedVisitor: Visitor): Boolean = {
    getVisitorById(visitorId).exists { visitor =>
      visitors -= visitor
      visitors += updatedVisitor
      true
    }
  }

  def checkOutVisitor(visitorId: String): Boolean = {
    getVisitorById(visitorId).exists { visitor =>
      val updatedVisitor = visitor.copy(checkOutTime = Some(java.time.Instant.now.toString))
      visitors -= visitor
      visitors += updatedVisitor
      true
    }
  }

  def deleteVisitor(visitorId: String): Boolean = {
    getVisitorById(visitorId).exists { visitor =>
      visitors -= visitor
      true
    }
  }
}
