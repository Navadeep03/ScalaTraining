ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.13" // Use Scala 2.12.x for Spark 3.2.x compatibility

lazy val root = (project in file("."))
  .settings(
    name := "Spark"
  )

// Define Spark version that has better compatibility with newer Java versions
val sparkVersion = "3.2.1"

libraryDependencies ++= Seq(
  // Spark Core and SQL
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-yarn" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,

  // Google Cloud Storage (GCS) connector for Hadoop
  "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop3-2.2.5",

  // MySQL JDBC driver
  "mysql" % "mysql-connector-java" % "8.0.19",

  // Cassandra Spark Connector
  "com.datastax.spark" %% "spark-cassandra-connector" % "3.0.1",

  // Joda Time (time manipulation library)
  "joda-time" % "joda-time" % "2.10.10",

  // JNR POSIX (Java Native Runtime library for POSIX systems)
  "com.github.jnr" % "jnr-posix" % "3.1.7",

  // ScalaTest for testing (only for test scope)
  "org.scalatest" %% "scalatest" % "3.2.2" % "test"
)
