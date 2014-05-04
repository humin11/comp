name := "comp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "net.vz.mongodb.jackson" %% "play-mongo-jackson-mapper" % "1.1.0",
  "mysql" % "mysql-connector-java" % "5.1.30"
)     

play.Project.playJavaSettings
