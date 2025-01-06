package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.GuestRepository
import javax.inject.Inject

class GuestService @Inject()(guestRepository: GuestRepository) {

  def getAllGuests(): Seq[Guest] = guestRepository.findAll()

  def getGuestById(id: String): Option[Guest] = guestRepository.findById(id)

  def addGuest(guest: Guest): Unit = guestRepository.save(guest)

  def updateGuest(id: String, updatedGuest: Guest): Unit = guestRepository.update(id, updatedGuest)

  def deleteGuest(id: String): Boolean = guestRepository.delete(id)
}
