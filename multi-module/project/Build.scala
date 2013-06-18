import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "multi-module"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )

  lazy val backend = play.Project(appName + "-backend", appVersion, path = file("subprojects/backend")).dependsOn(commonAssets, common)
  
  lazy val frontend = play.Project(appName + "-frontend", appVersion, path = file("subprojects/frontend")).dependsOn(commonAssets, common)
  
  lazy val common = play.Project(appName + "-common", appVersion, path = file("subprojects/common"))
  
  lazy val commonAssets = play.Project(appName + "-common-assets", appVersion, path = file("subprojects/common-assets"))

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  ).dependsOn(backend, frontend, commonAssets)
   .aggregate(backend, frontend, commonAssets)

}
