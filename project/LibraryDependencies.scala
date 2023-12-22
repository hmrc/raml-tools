import sbt._
import play.sbt.PlayImport._

object LibraryDependencies {
  def apply() = compileDependencies ++ testDependencies

  lazy val bootstrapVersion = "7.15.0"

  lazy val compileDependencies = Seq(
    "com.typesafe.play"       %% "play-json"                        % "2.9.2",
    "org.raml"                %  "raml-parser-2"                    % "1.0.13"
  )

  lazy val testDependencies = Seq(
    "com.vladsch.flexmark"    %  "flexmark-all"                     % "0.62.2",
    "com.github.tomakehurst"  %  "wiremock-jre8-standalone"         % "2.31.0",
    "org.mockito"             %% "mockito-scala-scalatest"          % "1.17.29",
    "org.scalatest"           %% "scalatest"                        % "3.2.17"
  ).map(_ % Test)
}
