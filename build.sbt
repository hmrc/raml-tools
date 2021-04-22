import sbtassembly.AssemblyKeys.assembly
import sbtassembly.AssemblyPlugin
import uk.gov.hmrc.SbtArtifactory

lazy val appName = "raml-tools"
lazy val appOrganization = "uk.gov.hmrc"

// Coverage configuration
lazy val scoverageSettings = Seq(
  coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo",
  coverageMinimum := 72,
  coverageFailOnMinimum := false,
  coverageHighlighting := true
)
lazy val library = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(
    scoverageSettings,
    scalaVersion := "2.12.12",
    majorVersion := 1,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.6.14",
      "org.raml" % "raml-parser-2" % "1.0.13",
      "org.pegdown" % "pegdown" % "1.6.0" % "test",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      "com.github.tomakehurst" % "wiremock" % "2.8.0" % "test"
    ),
    addArtifact(artifact in(Compile, assembly), assembly)
  )
  .settings(AssemblyPlugin.assemblySettings: _*)
  .settings(
    organization := appOrganization
  )

