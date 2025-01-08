package main.DayWiseTasks.CaseStudies.CaseStudy1.notifications

import main.DayWiseTasks.CaseStudies.CaseStudy1.kafka.producer.KafkaProducerService

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RestaurantServiceNotifications @Inject()(kafkaProducerService: KafkaProducerService)(implicit ec: ExecutionContext) {

  private val topic = "RestaurantServiceNotifications"

  def sendDailyMenu(guestId: String, menu: String): Future[Unit] = {
    val message = s"Today's menu: $menu"
    kafkaProducerService.publishMessage(topic, guestId, message)
  }

  def stopMenuUpdates(guestId: String): Future[Unit] = {
    val message = s"Menu updates for Guest $guestId have been stopped."
    kafkaProducerService.publishMessage(topic, guestId, message)
  }
}
