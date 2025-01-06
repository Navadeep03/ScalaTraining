package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room
import scala.collection.mutable

class RoomRepository {

  private val rooms = mutable.Map[String, Room]()

  def findAll(): Seq[Room] = rooms.values.toSeq

  def findById(id: String): Option[Room] = rooms.get(id)

  def save(room: Room): Unit = rooms.put(room.id, room)

  def update(id: String, updatedRoom: Room): Unit = rooms.update(id, updatedRoom)

  def delete(id: String): Boolean = rooms.remove(id).isDefined
}
