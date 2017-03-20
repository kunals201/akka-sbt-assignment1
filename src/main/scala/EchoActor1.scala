import akka.actor.Actor

/**
  * Created by knoldus on 3/16/17.
  */

  class EchoActor1 extends Actor {

    var count = 0;

    override def receive = {
      case msg =>
        count += 1
        println(s"This is what i am $msg ")
    }
  }


