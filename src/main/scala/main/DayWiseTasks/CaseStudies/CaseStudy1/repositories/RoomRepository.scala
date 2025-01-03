package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room.Room

import scala.collection.mutable

object RoomRepository {
  private val rooms = mutable.ListBuffer[Room]()

  def getAllRooms: List[Room] = rooms.toList

  def getRoomById(roomId: String): Option[Room] = rooms.find(_.roomId == roomId)

  def addRoom(room: Room): Unit = rooms += room

  def updateRoom(roomId: String, updatedRoom: Room): Boolean = {
    getRoomById(roomId).exists { room =>
      rooms -= room
      rooms += updatedRoom
      true
    }
  }

  def deleteRoom(roomId: String): Boolean = {
    getRoomById(roomId).exists { room =>
      rooms -= room
      true
    }
  }
}
