import akka.actor.{Actor, ActorSystem, Props}

class EchoActor extends Actor {

  var count = 0;

  override def receive = {
    case msg =>
      count += 1
      println(s"This is my msg $msg ")
  }
}


object EchoActor extends App{
  val s=ActorSystem("EchoSystem")
  val p=Props[EchoActor]
  val p1=Props[EchoActor1]
 val ref =s.actorOf(p)
  val ref1=s.actorOf(p1)

  ref ! "wlcome to the company1"

  ref1 ! "wlcome to the company3"
  ref1 ! "wlcome to the company4"
ref ! "wlcome to the company2"
}
