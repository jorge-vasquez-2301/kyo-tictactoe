ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "kyo-tictactoe",
    libraryDependencies ++= Seq(
      "io.getkyo"    %% "kyo-core"  % "0.7.2",
      "org.tpolecat" %% "atto-core" % "0.9.5"
    )
  )
