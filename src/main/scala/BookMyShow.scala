import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger


class BookMyShowSystem extends Actor {

  val log = Logger.getLogger(this.getClass)

  var flag = true

  override def receive = {

    case bookSeat if (bookSeat.equals("Booking Seat")) => {
      if (flag == true) {
        flag = false
log.info(s"Thanks for $bookSeat Number 1 on BookMyShow ")
      }
      else {
        log.warn("Sorry this seat is already booked :( try for other available Seats")
      }

    }

    case cancelSeat if (cancelSeat.equals("Cancel Seat")) => {

      if (flag == false) {
        flag = true
      log.info(s"Successfully $cancelSeat Number 1 Done.... ")
      }
      else
      log.info("This seat is Already Canceled")
    }
  }
}

object BookMyShow extends App {

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /RouterPool {
      |   router = balancing-pool
      |   nr-of-instances = 5
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)
  val router = system.actorOf(FromConfig.props(Props[BookMyShowSystem]), "RouterPool")

  router ! "Booking Seat"
  Thread.sleep(2000)
  router ! "Booking Seat"
  Thread.sleep(500)
  router ! "Cancel Seat"
  Thread.sleep(500)
  router ! "Cancel Seat"
  Thread.sleep(500)
  router ! "Booking Seat"

}
