package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.RoomRepository
import javax.inject.Inject

class RoomService @Inject()(roomRepository: RoomRepository) {

  def getAllRooms(): Seq[Room] = roomRepository.findAll()

  def getRoomById(id: String): Option[Room] = roomRepository.findById(id)

  def addRoom(room: Room): Unit = roomRepository.save(room)

  def updateRoom(id: String, updatedRoom: Room): Unit = roomRepository.update(id, updatedRoom)

  def deleteRoom(id: String): Boolean = roomRepository.delete(id)
}
