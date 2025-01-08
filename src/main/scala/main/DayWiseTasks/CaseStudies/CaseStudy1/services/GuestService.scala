package main.DayWiseTasks.CaseStudies.CaseStudy1.services

import main.DayWiseTasks.CaseStudies.CaseStudy1.models.Guest
import main.DayWiseTasks.CaseStudies.CaseStudy1.repositories.GuestRepository\

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class GuestService @Inject()(guestRepository: GuestRepository)(implicit ec: ExecutionContext) {

  def getAllGuests: Future[Seq[Guest]] = {
    guestRepository.getAllGuests
  }

  def getGuestById(id: String): Future[Option[Guest]] = {
    guestRepository.getGuestById(id)
  }

  def addGuest(guest: Guest): Future[Unit] = {
    guestRepository.addGuest(guest)
  }

  def deleteGuest(id: String): Future[Boolean] = {
    guestRepository.deleteGuest(id)
  }
}
