FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java", "-jar", "/usr/local/app/grep/lib/grep.jar"]
