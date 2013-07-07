package utils

object BootstrapConsole {
  //your code goes here
  implicit val application = new play.core.StaticApplication(new java.io.File("."))
}