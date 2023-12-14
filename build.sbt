ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "kyo-tictactoe",
    libraryDependencies ++= Seq(
      "io.getkyo" %% "kyo-core"   % "0.7.6",
      "dev.zio"   %% "zio-parser" % "0.1.9"
    ),
    scalacOptions ++= Seq(
      "-Wunused:all",
      "-Werror"
    )
  )
