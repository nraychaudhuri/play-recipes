import models._
import models.Speaker
import models.Talk
import play.api.GlobalSettings

object Global extends GlobalSettings{

  override def onStart(app: play.api.Application): Unit = {
    val chad = Speakers.insert(Speaker("Chad fowler", "CTO", "chadfowler"))
    val nilanjan = Speakers.insert(Speaker("Nilanjan Raychaudhuri", "Developer", "nraychaudhuri"))

    Talks.insert(Talk("Play vs. Rails", SessionTypes.LightingTalk, "scala, play", chad))
    Talks.insert(Talk("Play recipes", SessionTypes.Tutorial, "scala, play", chad))

    Talks.insert(Talk("Play async", SessionTypes.LightingTalk, "scala, play", nilanjan))
    Talks.insert(Talk("Play slick", SessionTypes.Tutorial, "scala, play", nilanjan))
  }

}
