package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest

import javax.inject._
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GuestRepository @Inject()(mongoClient: MongoClient)(implicit ec: ExecutionContext) {
  private val database: MongoDatabase = mongoClient.getDatabase("FacilityDB")
  private val collection: MongoCollection[Guest] = database.getCollection("guests")

  def getAllGuests: Future[Seq[Guest]] = {
    collection.find().toFuture()
  }

  def getGuestById(id: String): Future[Option[Guest]] = {
    collection.find(equal("id", id)).headOption()
  }

  def addGuest(guest: Guest): Future[Unit] = {
    collection.insertOne(guest).toFuture().map(_ => ())
  }

  def deleteGuest(id: String): Future[Boolean] = {
    collection.deleteOne(equal("id", id)).toFuture().map(_.wasAcknowledged())
  }
}
