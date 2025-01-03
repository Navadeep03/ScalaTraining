package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking.Booking

import scala.collection.mutable

object BookingRepository {
  private val bookings = mutable.ListBuffer[Booking]()

  def getAllBookings: List[Booking] = bookings.toList

  def getBookingById(bookingId: String): Option[Booking] = bookings.find(_.bookingId == bookingId)

  def addBooking(booking: Booking): Unit = bookings += booking

  def updateBooking(bookingId: String, updatedBooking: Booking): Boolean = {
    getBookingById(bookingId).exists { booking =>
      bookings -= booking
      bookings += updatedBooking
      true
    }
  }

  def deleteBooking(bookingId: String): Boolean = {
    getBookingById(bookingId).exists { booking =>
      bookings -= booking
      true
    }
  }
}
