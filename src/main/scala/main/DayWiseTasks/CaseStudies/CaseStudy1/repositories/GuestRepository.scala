package main.DayWiseTasks.CaseStudies.CaseStudy1.repositories

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest.Guest

import scala.collection.mutable

object GuestRepository {
  private val guests = mutable.ListBuffer[Guest]()

  def getAllGuests: List[Guest] = guests.toList

  def getGuestById(guestId: String): Option[Guest] = guests.find(_.guestId == guestId)

  def addGuest(guest: Guest): Unit = guests += guest

  def updateGuest(guestId: String, updatedGuest: Guest): Boolean = {
    getGuestById(guestId).exists { guest =>
      guests -= guest
      guests += updatedGuest
      true
    }
  }

  def deleteGuest(guestId: String): Boolean = {
    getGuestById(guestId).exists { guest =>
      guests -= guest
      true
    }
  }
}
