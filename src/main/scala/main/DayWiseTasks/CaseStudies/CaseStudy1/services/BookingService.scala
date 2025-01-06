package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.BookingRepository
import javax.inject.Inject

class BookingService @Inject()(bookingRepository: BookingRepository) {

  def getAllBookings(): Seq[Booking] = bookingRepository.findAll()

  def getBookingById(id: String): Option[Booking] = bookingRepository.findById(id)

  def createBooking(booking: Booking): Unit = bookingRepository.save(booking)

  def updateBooking(id: String, updatedBooking: Booking): Unit = bookingRepository.update(id, updatedBooking)

  def cancelBooking(id: String): Boolean = bookingRepository.delete(id)
}
