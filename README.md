export PATH=$PATH:/opt/gradle/gradle-3.4.1/bin
gradle build -x test

java -jar  /root/.jenkins/workspace/SpotsFinderServer/build/libs/spotsfinder-server-1.0.0-SNAPSHOT.war --server.baseurl=http://80.211.223.50:8080