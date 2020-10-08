FROM maven:3.6.3-jdk-11
ENV spring_profiles_active=linux
WORKDIR /app
COPY . .
ENTRYPOINT ["mvn","clean", "install", "exec:java"]