import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory


class BookMyShowSystem extends Actor {

  var flag = true

  override def receive = {

    case bookSeat if (bookSeat.equals("Booking Seat")) => {
      if (flag == true) {
        flag = false
        println(s"Thanks for $bookSeat Number 1 on BookMyShow ")
      }
      else {
        println("Sorry this seat is already booked :( try for other available Seats")
      }

    }

    case cancelSeat if (cancelSeat.equals("Cancel Seat")) => {

      if (flag == false) {
        flag = true
        println(s"Successfully $cancelSeat Number 1 Done.... ")
      }
      else
        println("This seat is Already Canceled")
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
  Thread.sleep(500)
  router ! "Booking Seat"
  Thread.sleep(500)
  router ! "Cancel Seat"
  Thread.sleep(500)
  router ! "Cancel Seat"
  Thread.sleep(500)
  router ! "Booking Seat"

}
