name := """play-bigquery"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  guice,
  "com.lightbend.akka" %% "akka-stream-alpakka-google-cloud-bigquery" % "8.0.0",
)

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.14.2"

resolvers += "Akka library repository".at("https://repo.akka.io/maven")

