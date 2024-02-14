import sbt._
import play.sbt.PlayImport._

object LibraryDependencies {
  def apply() = compileDependencies ++ testDependencies

  lazy val bootstrapVersion = "7.15.0"

  lazy val compileDependencies = Seq(
    "org.playframework" %% "play-json"     % "3.0.1",
    "org.raml"           % "raml-parser-2" % "1.0.13"
  )

  lazy val testDependencies = Seq(
    "com.vladsch.flexmark"    % "flexmark-all"            % "0.64.6",
    "org.mockito"            %% "mockito-scala-scalatest" % "1.17.29",
    "com.github.tomakehurst"  % "wiremock"                % "3.0.0-beta-7",
    "org.scalatest"          %% "scalatest"               % "3.2.17"
  ).map(_ % Test)
}
