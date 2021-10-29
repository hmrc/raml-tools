lazy val appName = "raml-tools"

// Coverage configuration
lazy val scoverageSettings = Seq(
  coverageExcludedPackages := "<empty>;com.kenshoo.play.metrics.*;.*definition.*;prod.*;testOnlyDoNotUseInAppConf.*;app.*;uk.gov.hmrc.BuildInfo",
  coverageMinimum := 72,
  coverageFailOnMinimum := false,
  coverageHighlighting := true
)
lazy val library = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
  .settings(
    scoverageSettings,
    scalaVersion := "2.12.12",
    majorVersion := 1,
    libraryDependencies ++= Seq(
      "com.typesafe.play"       %% "play-json"                  % "2.9.2",
      "org.raml"                %  "raml-parser-2"              % "1.0.13",
      "org.scalatest"           %% "scalatest"                  % "3.2.9"     % "test",
      "com.github.tomakehurst"  %  "wiremock-jre8-standalone"   % "2.27.2"    % "test",
      "com.vladsch.flexmark"    %  "flexmark-all"               % "0.35.10"   % "test"
    )
  )

