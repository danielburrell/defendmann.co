name := "defendmannco"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "commons-validator" % "commons-validator" % "1.4.0",
  "org.springframework" % "spring-jdbc" % "4.0.3.RELEASE",
  "org.springframework" % "spring-core" % "4.0.3.RELEASE",
  "org.springframework" % "spring-context" % "4.0.3.RELEASE",
  "org.springframework" % "spring-expression" % "4.0.3.RELEASE",
  "joda-time" % "joda-time" % "2.3",
  "com.google.inject" % "guice" % "3.0"
)

play.Project.playJavaSettings
