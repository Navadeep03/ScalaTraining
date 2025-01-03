ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.15"


lazy val root = (project in file("."))
  .settings(
    name := "OptimizedDataPipeline"
  )

val sparkVersion = "3.3.3" // Updated for Scala 2.13

libraryDependencies ++= Seq(
  // Spark dependencies
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-yarn" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,


  // Play Framework dependencies
  "com.typesafe.play" %% "play" % "2.8.18",
  "com.typesafe.play" %% "play-guice" % "2.8.18",
  "com.typesafe.play" %% "play-json" % "2.9.4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,

  // Kafka dependencies
  "org.apache.kafka" % "kafka-clients" % "3.7.1",

  // MongoDB
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.9.0",

  // GCS and Database dependencies
  "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop3-2.2.5",
  "mysql" % "mysql-connector-java" % "8.0.28",
  //"com.datastax.spark" %% "spark-cassandra-connector" % "3.3.0", // Compatible with Scala 2.13

  // Other utilities
  "joda-time" % "joda-time" % "2.10.10",
  "com.github.jnr" % "jnr-posix" % "3.1.17",
  "com.typesafe.play" %% "filters-helpers" % "2.8.18" , // Play Filters dependency

  // Akka dependencies
  "com.typesafe.akka" %% "akka-actor" % "2.6.20",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.20",
  "com.typesafe.akka" %% "akka-stream" % "2.6.20",
  "com.typesafe.akka" %% "akka-http" % "10.2.10",
  "com.typesafe.akka" %% "akka-stream-kafka" % "3.0.0", // Updated for Scala 2.13

  // Logging dependencies
  "ch.qos.logback" % "logback-classic" % "1.4.12",
  "org.slf4j" % "slf4j-api" % "2.0.11",
  "com.typesafe.akka" %% "akka-slf4j" % "2.6.20",

  // JSON and JWT dependencies
  "io.spray" %% "spray-json" % "1.3.6",
  "com.github.jwt-scala" %% "jwt-play" % "9.1.1",

  // Test dependencies
  "org.scalatest" %% "scalatest" % "3.2.17" % Test
)

dependencyOverrides += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"

resolvers ++= Seq(
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Datastax Repository" at "https://maven.datastax.com/public-repo/",
  "Spark Packages Repo" at "https://repos.spark-packages.org/",
  "Maven Central" at "https://repo1.maven.org/maven2/"
)

evictionErrorLevel := Level.Warn

