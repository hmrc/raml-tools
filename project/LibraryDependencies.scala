import sbt._
import play.sbt.PlayImport._

object LibraryDependencies {
  def apply() = compileDependencies ++ testDependencies

  lazy val bootstrapVersion = "7.12.0"

  lazy val compileDependencies = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"        % bootstrapVersion,
    "com.typesafe.play"       %% "play-json"                        % "2.9.2",
    "org.raml"                %  "raml-parser-2"                    % "1.0.13"
  )

  lazy val testDependencies = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-28"           % bootstrapVersion,
    "org.scalatest"           %% "scalatest"                        % "3.2.9",
    "com.github.tomakehurst"  %  "wiremock-jre8-standalone"         % "2.27.2",
    "com.vladsch.flexmark"    %  "flexmark-all"                     % "0.35.10"       
  ).map(_ % Test)
}
