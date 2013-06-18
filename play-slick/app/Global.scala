import models._
import alltables._
import play.api.GlobalSettings

object Global extends GlobalSettings{

  override def onStart(app: play.api.Application): Unit = {
    val chad = speakers.insert(Speaker("Chad fowler", "CTO", "chadfowler"))
    val nilanjan = speakers.insert(Speaker("Nilanjan Raychaudhuri", "Developer", "nraychaudhuri"))

    talks.insert(Talk("Play vs. Rails", SessionTypes.LightingTalk, "scala, play", chad))
    talks.insert(Talk("Play recipes", SessionTypes.Tutorial, "scala, play", chad))

    talks.insert(Talk("Play async", SessionTypes.LightingTalk, "scala, play", nilanjan))
    talks.insert(Talk("Play slick", SessionTypes.Tutorial, "scala, play", nilanjan))
  }

}
