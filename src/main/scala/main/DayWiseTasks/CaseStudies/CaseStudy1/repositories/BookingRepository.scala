package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Booking
import scala.collection.mutable

class BookingRepository {

  private val bookings = mutable.Map[String, Booking]()

  def findAll(): Seq[Booking] = bookings.values.toSeq

  def findById(id: String): Option[Booking] = bookings.get(id)

  def save(booking: Booking): Unit = bookings.put(booking.id, booking)

  def update(id: String, updatedBooking: Booking): Unit = bookings.update(id, updatedBooking)

  def delete(id: String): Boolean = bookings.remove(id).isDefined
}
