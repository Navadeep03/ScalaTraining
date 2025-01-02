ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.18" // Downgrade to Scala 2.12 for compatibility

lazy val root = (project in file("."))
  .settings(
    name := "OptimizedDataPipeline"
  )

val sparkVersion = "3.2.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-yarn" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,

  "com.typesafe.play" %% "play" % "2.8.18",
  "com.typesafe.play" %% "play-guice" % "2.8.18",
  "com.typesafe.play" %% "play-json" % "2.9.4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,

  "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop3-2.2.5",
  "mysql" % "mysql-connector-java" % "8.0.19",
  "com.datastax.spark" %% "spark-cassandra-connector" % "3.1.0", // Compatible with Scala 2.12
  "joda-time" % "joda-time" % "2.10.10",
  "com.github.jnr" % "jnr-posix" % "3.1.7",

  "org.scalatest" %% "scalatest" % "3.2.2" % Test
)

resolvers ++= Seq(
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Spark Packages Repo" at "https://repos.spark-packages.org/",
  "Maven Central" at "https://repo1.maven.org/maven2/"
)
