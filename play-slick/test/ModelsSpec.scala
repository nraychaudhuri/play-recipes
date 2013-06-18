package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import models._
import alltables._
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ModelsSpec extends Specification {
  "Models" should {
     "find talks for given speaker" in {
       running(FakeApplication()) {
         val speakerId = speakers.insert(Speaker("Chad fowler", "CTO", "chadfowler"))

         talks.insert(Talk("About Scala", SessionTypes.LightingTalk, "scala, play", speakerId))
         talks.insert(Talk("About Play", SessionTypes.Tutorial, "scala, play", speakerId))

         val descriptions = for {
           speaker <- speakers.findById(speakerId)
         } yield  for(talk <-  talks.findTalks(speaker)) yield talk.description

         descriptions must beSome(List("About Scala", "About Play"))
       }
     }
  }
}