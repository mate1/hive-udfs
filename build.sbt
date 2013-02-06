import AssemblyKeys._
assemblySettings

name := "hive-udfs"

version := "0.0.1"

scalaVersion := "2.9.2"

organization := "com.sharethrough"

resolvers += "Cascading repo" at "http://conjars.org/repo"

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies += "org.apache.hive" % "hive-exec" % "0.9.0" % "provided"

libraryDependencies ++= Seq(
  "org.kohsuke" % "geoip" % "1.2.8",
  "org.apache.hadoop" % "hadoop-core" % "1.1.0" % "provided",
  "org.specs2" %% "specs2" % "1.12.3" % "test"
)
