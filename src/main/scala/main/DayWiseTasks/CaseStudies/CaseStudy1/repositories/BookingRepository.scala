package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking

import javax.inject._
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookingRepository @Inject()(mongoClient: MongoClient)(implicit ec: ExecutionContext) {
  private val database: MongoDatabase = mongoClient.getDatabase("FacilityDB")
  private val collection: MongoCollection[Booking] = database.getCollection("bookings")

  def getAllBookings: Future[Seq[Booking]] = {
    collection.find().toFuture()
  }

  def getBookingById(id: String): Future[Option[Booking]] = {
    collection.find(equal("id", id)).headOption()
  }

  def createBooking(booking: Booking): Future[Unit] = {
    collection.insertOne(booking).toFuture().map(_ => ())
  }

  def deleteBooking(id: String): Future[Boolean] = {
    collection.deleteOne(equal("id", id)).toFuture().map(_.wasAcknowledged())
  }
}
