package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room.Room
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.RoomRepository

object RoomService {
  def getAllRooms(): List[Room] = RoomRepository.getAllRooms

  def getRoomById(roomId: String): Option[Room] = RoomRepository.getRoomById(roomId)

  def addRoom(room: Room): Unit = RoomRepository.addRoom(room)

  def updateRoom(roomId: String, updatedRoom: Room): Boolean =
    RoomRepository.updateRoom(roomId, updatedRoom)

  def deleteRoom(roomId: String): Boolean = RoomRepository.deleteRoom(roomId)
}
