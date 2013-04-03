import AssemblyKeys._
assemblySettings

name := "hive-udfs"

version := "0.0.1"

scalaVersion := "2.9.2"

organization := "com.sharethrough"

test in assembly := {}

resolvers += "Cascading repo" at "http://conjars.org/repo"

resolvers ++= Seq(
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "cloudera"  at "https://repository.cloudera.com/artifactory/cloudera-repos/",
	"typesafe" at "http://repo.typesafe.com/typesafe/releases"
)

libraryDependencies += "org.apache.hive" % "hive-exec" % "0.9.0" % "provided"

libraryDependencies ++= Seq(
  "org.kohsuke" % "geoip" % "1.2.8",
  "org.apache.hadoop" % "hadoop-core" % "1.1.1" % "provided",
  "org.specs2" %% "specs2" % "1.12.3" % "test",
	"com.jolbox" % "bonecp" % "0.8.0-rc1",
	"mysql" % "mysql-connector-java" % "5.1.24",
	"com.typesafe.akka" % "akka-actor" % "2.0.5"
)
