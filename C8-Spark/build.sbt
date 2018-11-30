name := "LogRegOnSpark"

version := "0.1"

scalacOptions ++= Seq(
  "-unchecked", "-deprecation", "-feature"
)

libraryDependencies  ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.typelevel" %% "spire" % "0.15.0",
  "org.scalanlp" %% "breeze" % "0.13.2",
  "org.scalanlp" %% "breeze-natives" % "0.13.2",
  "org.scalanlp" %% "breeze-viz" % "0.13.2"
)

libraryDependencies  ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0",
  "org.apache.spark" %% "spark-sql" % "2.3.0",
  "org.apache.spark" %% "spark-mllib" % "2.3.0"
//"org.apache.spark" %% "spark-launcher" % "2.3.0",
//"org.apache.spark" %% "spark-mesos" % "2.3.0",
//"org.apache.spark" %% "spark-hive" % "2.3.0",
//"org.apache.spark" %% "spark-hive-thriftserver" % "2.3.0",
//"org.apache.spark" %% "spark-yarn" % "2.3.0",
//"org.apache.spark" %% "spark-repl" % "2.3.0"
)

/*
// See https://sanori.github.io/2017/06/Using-sbt-instead-of-spark-shell/
initialCommands in console := """
  import org.apache.spark.sql.SparkSession
  import org.apache.spark.sql.functions._
  val spark = SparkSession.builder().
    master("local").
    appName("spark-shell").
    getOrCreate()
  import spark.implicits._
  val sc = spark.sparkContext

  import org.apache.log4j.Logger
  import org.apache.log4j.Level
  Logger.getRootLogger.setLevel(Level.ERROR)
"""
cleanupCommands in console := """
  println("In case spark is not stopped: ignore the message for spark.stop.")
  spark.stop()
"""
*/

// see https://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/Level.html
initialCommands in console := """
  import org.apache.log4j.Logger
  import org.apache.log4j.Level

//Logger.getLogger("org").setLevel(Level.ERROR)
//Logger.getLogger("akka").setLevel(Level.ERROR)
  Logger.getRootLogger.setLevel(Level.ERROR)
"""
cleanupCommands in console := """
  println("In case spark is not stopped: ignore the message for spark.stop.")
  spark.stop()
"""


// libraryDependencies  += "org.ddahl" %% "rscala" % "2.5.3"

// libraryDependencies  += "me.shadaj" %% "scalapy" % "0.1.0-SNAPSHOT"

scalaVersion := "2.11.12"

