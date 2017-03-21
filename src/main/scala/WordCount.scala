import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.apache.log4j.Logger
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.io.Source

class TotalWords(fileName: String) extends Actor {
  var wordCount = 0
  val log = Logger.getLogger(this.getClass)
  import scala.concurrent.ExecutionContext.Implicits.global

  val messageActor = context.actorOf(Props[WordsInLine])

  override def receive = {
    case msg: String => {
      for (line <- Source.fromFile(fileName).getLines()) {
        implicit val timeout = Timeout(1000 seconds)
        val f: Future[Int] = (messageActor ask line).mapTo[Int]
        Thread.sleep(1000)
        f.foreach { words =>log.info(words);wordCount += words }
      }
         log.info(s"$msg $wordCount")
    }
  }
}

class WordsInLine extends Actor {
  override def receive = {
    case msg: String => {
      val words = msg.split(" ").length
      sender() ! words
    }
  }
}

object WordCount extends App {
  val source = "/home/knoldus/assignment/akka-kip/src/main/scala/com/knoldus/day1/source.txt"
  val system = ActorSystem("ActorSystem")
  val props = Props(classOf[TotalWords], source)
  val ref = system.actorOf(props)
  ref ! "totalNumberOfWords:- "
}

