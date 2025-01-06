// sbt-native-packager plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.9.16")

// Resolvers
resolvers ++= Seq(
  Resolver.sbtPluginRepo("releases"),
  Resolver.typesafeRepo("releases"),
  "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)
