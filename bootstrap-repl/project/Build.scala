import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "console"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )


  val main = play.Project(appName,   appVersion, appDependencies).settings(
    // Add your own project settings here  
    initialCommands in console := """
      import play.api._
      import play.api.mvc._
      import play.api.libs.json._ 
      import utils.BootstrapConsole._
    """,
    initialCommands in (Test, console) <<= (initialCommands in console)(_ + "; import play.api.test.Helpers._")
    
  )

}
