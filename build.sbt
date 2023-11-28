val languageVersion = "2.13.12"

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := languageVersion

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % languageVersion,
      "org.scala-lang" % "scala-compiler" % languageVersion,
      "junit" % "junit" % "4.13.2"
      ),
    name := "parser-gen"
  )
