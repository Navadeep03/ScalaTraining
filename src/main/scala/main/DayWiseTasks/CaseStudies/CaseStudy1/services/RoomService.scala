package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.RoomRepository

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RoomService @Inject()(roomRepository: RoomRepository)(implicit ec: ExecutionContext) {

  def getAllRooms: Future[Seq[Room]] = {
    roomRepository.getAllRooms
  }

  def getRoomById(id: String): Future[Option[Room]] = {
    roomRepository.getRoomById(id)
  }

  def addRoom(room: Room): Future[Unit] = {
    roomRepository.addRoom(room)
  }

  def deleteRoom(id: String): Future[Boolean] = {
    roomRepository.deleteRoom(id)
  }
}
