// Project settings
name := "FacilityManagementSystem"
version := "1.0"
scalaVersion := "2.13.15"

// Dependencies
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.8.20",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.21",
  "com.typesafe.akka" %% "akka-stream" % "2.6.21",
  "com.typesafe.akka" %% "akka-http" % "10.2.10",
  "com.typesafe.akka" %% "akka-stream-kafka" % "3.0.1",
  "org.apache.kafka" %% "kafka" % "3.6.2",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.9.1",
  "com.typesafe.akka" %% "akka-email" % "6.0.0",
  "org.scalatest" %% "scalatest" % "3.2.17" % Test,
  "org.mockito" %% "mockito-scala" % "1.17.12" % Test,
  "ch.qos.logback" % "logback-classic" % "1.4.9",
  "com.typesafe.play" %% "play-json" % "2.9.4",
  "com.google.inject" % "guice" % "5.1.0"
)

// Enable sbt-native-packager
enablePlugins(JavaAppPackaging)
