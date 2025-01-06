package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest
import scala.collection.mutable

class GuestRepository {

  private val guests = mutable.Map[String, Guest]()

  def findAll(): Seq[Guest] = guests.values.toSeq

  def findById(id: String): Option[Guest] = guests.get(id)

  def save(guest: Guest): Unit = guests.put(guest.id, guest)

  def update(id: String, updatedGuest: Guest): Unit = guests.update(id, updatedGuest)

  def delete(id: String): Boolean = guests.remove(id).isDefined
}
