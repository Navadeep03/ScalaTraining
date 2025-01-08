package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.BookingRepository

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookingService @Inject()(bookingRepository: BookingRepository)(implicit ec: ExecutionContext) {

  def getAllBookings: Future[Seq[Booking]] = {
    bookingRepository.getAllBookings
  }

  def getBookingById(id: String): Future[Option[Booking]] = {
    bookingRepository.getBookingById(id)
  }

  def createBooking(booking: Booking): Future[Unit] = {
    bookingRepository.createBooking(booking)
  }

  def deleteBooking(id: String): Future[Boolean] = {
    bookingRepository.deleteBooking(id)
  }
}
