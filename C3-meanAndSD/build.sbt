name := "app-template"

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

// libraryDependencies += "com.github.darrenjw" %% "scala-glm" % "0.3"
// libraryDependencies  += "org.ddahl" %% "rscala" % "2.5.3"

scalaVersion := "2.12.6"

