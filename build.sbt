import sbt._
import sbt.Keys._
import bloop.integrations.sbt.BloopDefaults

lazy val appName = "raml-tools"

Global / bloopAggregateSourceDependencies := true

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"


inThisBuild(
  List(
    scalaVersion := "2.12.15",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision
  )
)


lazy val library = Project(appName, file("."))
  .settings(
    scalaVersion := "2.12.15",
    majorVersion := 1,
    libraryDependencies ++= LibraryDependencies()
  )
  .settings(
    ScoverageSettings()
  )
  .settings(
    inConfig(Test)(BloopDefaults.configSettings),
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT"),
    Test / unmanagedSourceDirectories ++= Seq(baseDirectory.value / "src" / "test"),
  )
