export PATH=$PATH:/opt/gradle/gradle-3.4.1/bin
gradle build -x test

java -jar  /root/.jenkins/workspace/SpotsFinderServer/build/libs/spotsfinder-server-1.0.0-SNAPSHOT.war --server.baseurl=http://80.211.223.50:8080

//Mysql utf8 problem.
SET collation_connection = 'utf8_general_ci';
ALTER DATABASE spots_finder1 CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE spots_finder1.geocoding_information CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;