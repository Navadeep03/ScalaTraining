package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking.Booking
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.BookingRepository

object BookingService {
  def getAllBookings(): List[Booking] = BookingRepository.getAllBookings

  def getBookingById(bookingId: String): Option[Booking] = BookingRepository.getBookingById(bookingId)

  def createBooking(booking: Booking): Unit = BookingRepository.addBooking(booking)

  def updateBooking(bookingId: String, updatedBooking: Booking): Boolean =
    BookingRepository.updateBooking(bookingId, updatedBooking)

  def cancelBooking(bookingId: String): Boolean = BookingRepository.deleteBooking(bookingId)
}
