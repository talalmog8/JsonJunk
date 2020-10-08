I used jdk 11, maven and generator-windows-amd64.exe (in windows environment) to develop this project. 
to run use:     mvnw spring-boot:run
(in the project classpath)

or run using docker: (open cmd in classpath)
docker volume create --name maven-repo
docker run -p 8080:8080 -v maven-repo:/root/.m2 talalmog8/json-junk

this builds and executed the program 
