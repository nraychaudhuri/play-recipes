package models

import scala.slick.lifted.MappedTypeMapper
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import play.api.Play.current


object SessionTypes extends Enumeration {
  type SessionType = Value
  val LightingTalk, OneHourTalk, Tutorial = Value

  private val ser: SessionType => Int = s => s.id
  private val deser: Int => SessionType = i => SessionTypes.apply(i)

  implicit val sessionTypeMapper = MappedTypeMapper.base[SessionType, Int](ser, deser)

}

import SessionTypes._
case class Talk(description: String, sessionType: SessionType, tags: String, speakerId: Long, id: Option[Long] = None)

case class Speaker(name: String, bio: String, twitterId: String, id: Option[Long] = None) {
  def talks = Talks.findTalks(this)
}

object Speakers extends Table[Speaker]("SPEAKERS") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def bio = column[String]("bio", O.NotNull)
  def twitterId = column[String]("twitterId", O.NotNull)
  def * = name ~ bio ~ twitterId ~ id.? <> (Speaker.apply _, Speaker.unapply _)

  def autoInc = * returning id

  val byId = createFinderBy(_.id)

  def insert(s: Speaker) = {
    DB.withSession { implicit session =>
      Speakers.autoInc.insert(s)
    }
  }

  def findById(id: Long): Option[Speaker] = DB.withSession { implicit session =>
    byId(id).firstOption
  }


  def findAll: List[Speaker] = DB.withSession {implicit session =>
    Query(Speakers).list
  }

}


object Talks extends Table[Talk]("TALKS") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("description", O.NotNull)
  def sessionType = column[SessionType]("sessionType", O.NotNull)
  def tags = column[String]("tags", O.NotNull)
  def speakerId = column[Long]("speakerId")

  def speaker = foreignKey("SUP_FK", speakerId, Speakers)(_.id)

  def * = description ~ sessionType ~ tags ~ speakerId ~ id.? <> (Talk.apply _, Talk.unapply _)

  def autoInc = * returning id

  def insert(t: Talk) = {
    DB.withSession { implicit session =>
      Talks.autoInc.insert(t)
    }
  }


  def findTalks(s: Speaker): List[Talk] = {
    DB.withSession { implicit session =>

      val q = for(t <- Talks if t.speakerId === s.id.get) yield t
      q.list()
    }

  }
}