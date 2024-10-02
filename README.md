# web-sqlite-spark-sample

This project is spring boot base, with `Maven` as build tools.
so for running you have to run `mvn package` and run  `java -jar app.jar`.

this project for simplifying is used sqlite database, and you can address your 
db file with parameter via command like this: 

`java -Dfile.encoding=UTF8 -Dspring.datasource.url=jdbc:sqlite:C:/webmetric.db  -jar app.jar`