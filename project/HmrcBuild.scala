import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin._
import scoverage.ScoverageKeys.{coverageExcludedPackages, coverageFailOnMinimum, coverageMinimum, coverageHighlighting}
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

object HmrcBuild extends Build {

  val appName = "raml-tools"
  val appOrganization = "uk.gov.hmrc"

  // Coverage configuration
  lazy val scoverageSettings = Seq(
    coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo",
    coverageMinimum := 72,
    coverageFailOnMinimum := false,
    coverageHighlighting := true
  )

  val library = Project(appName, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(
      scoverageSettings,
      scalaVersion := "2.11.11",
      resolvers += Resolver.bintrayRepo("hmrc", "releases"),
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play-json" % "2.5.12",
        "org.raml" % "raml-parser-2" % "1.0.13",
        "org.pegdown" % "pegdown" % "1.6.0" % "test",
        "org.scalatest" %% "scalatest" % "3.0.5" % "test",
        "com.github.tomakehurst" % "wiremock" % "2.8.0" % "test"
      ),
      excludeDependencies ++= Seq(
        SbtExclusionRule("com.google.code.findbugs", "annotations")
      ),
      crossScalaVersions := Seq("2.11.11"),
      AssemblySettings(),
      addArtifact(artifact in(Compile, assembly), assembly)
    )
    .settings(assemblySettings: _*)
    .settings(
      organization := appOrganization
    )
}

object AssemblySettings {
  def apply() = Seq(
    mainClass in assembly := Some("uk.gov.hmrc.ramltools.Raml2Html"),
    artifact in(Compile, assembly) := {
      val art = (artifact in(Compile, assembly)).value
      art.copy(`classifier` = Some("assembly"))
    }
  )
}
