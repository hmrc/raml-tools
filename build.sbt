import sbt._
import sbt.Keys._
import bloop.integrations.sbt.BloopDefaults

lazy val appName = "raml-tools"
lazy val scala212 = "2.12.15"
lazy val scala213 = "2.13.8"
lazy val supportedScalaVersions = List(scala212, scala213)

Global / bloopAggregateSourceDependencies := true

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

scalaVersion := scala213

inThisBuild(
  List(
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision
  )
)


lazy val library = Project(appName, file("."))
  .settings(
    crossScalaVersions := supportedScalaVersions,
  )
  .settings(
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
