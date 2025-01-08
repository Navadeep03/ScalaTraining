package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Room

import javax.inject._
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RoomRepository @Inject()(mongoClient: MongoClient)(implicit ec: ExecutionContext) {
  private val database: MongoDatabase = mongoClient.getDatabase("FacilityDB")
  private val collection: MongoCollection[Room] = database.getCollection("rooms")

  def getAllRooms: Future[Seq[Room]] = {
    collection.find().toFuture()
  }

  def getRoomById(id: String): Future[Option[Room]] = {
    collection.find(equal("id", id)).headOption()
  }

  def addRoom(room: Room): Future[Unit] = {
    collection.insertOne(room).toFuture().map(_ => ())
  }

  def deleteRoom(id: String): Future[Boolean] = {
    collection.deleteOne(equal("id", id)).toFuture().map(_.wasAcknowledged())
  }
}
