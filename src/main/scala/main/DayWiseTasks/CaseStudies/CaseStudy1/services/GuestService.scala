package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest.Guest
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.GuestRepository

object GuestService {
  def getAllGuests(): List[Guest] = GuestRepository.getAllGuests

  def getGuestById(guestId: String): Option[Guest] = GuestRepository.getGuestById(guestId)

  def addGuest(guest: Guest): Unit = GuestRepository.addGuest(guest)

  def updateGuest(guestId: String, updatedGuest: Guest): Boolean =
    GuestRepository.updateGuest(guestId, updatedGuest)

  def deleteGuest(guestId: String): Boolean = GuestRepository.deleteGuest(guestId)
}
