ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

val kyoVersion = "0.8.0"

lazy val root = (project in file("."))
  .settings(
    name := "kyo-tictactoe",
    libraryDependencies ++= Seq(
      "io.getkyo" %% "kyo-core"   % kyoVersion,
      "io.getkyo" %% "kyo-direct" % kyoVersion,
      "dev.zio"   %% "zio-parser" % "0.1.9"
    ),
    scalacOptions ++= Seq(
      "-Wunused:all",
      "-Werror"
    )
  )
