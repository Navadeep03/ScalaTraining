package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy2.models.Notification.Notification

import javax.inject._
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class NotificationRepository @Inject()(mongoClient: MongoClient)(implicit ec: ExecutionContext) {
  private val database: MongoDatabase = mongoClient.getDatabase("FacilityDB")
  private val collection: MongoCollection[Notification] = database.getCollection("notifications")

  def getAllNotifications: Future[Seq[Notification]] = {
    collection.find().toFuture()
  }

  def saveNotification(notification: Notification): Future[Unit] = {
    collection.insertOne(notification).toFuture().map(_ => ())
  }
}
