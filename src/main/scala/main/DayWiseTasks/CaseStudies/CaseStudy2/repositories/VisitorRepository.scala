package repositories

import models.Visitor
import scala.collection.mutable

class VisitorRepository {

  private val visitors = mutable.Map[String, Visitor]()

  def addVisitor(visitor: Visitor): Unit = {
    visitors(visitor.id) = visitor
  }

  def getAllVisitors(): List[Visitor] = {
    visitors.values.toList
  }

  def getVisitorById(id: String): Option[Visitor] = {
    visitors.get(id)
  }

  def updateVisitor(id: String, updatedVisitor: Visitor): Unit = {
    if (visitors.contains(id)) {
      visitors(id) = updatedVisitor
    } else {
      throw new NoSuchElementException(s"Visitor with ID $id not found.")
    }
  }

  def deleteVisitor(id: String): Unit = {
    visitors.remove(id)
  }
}
