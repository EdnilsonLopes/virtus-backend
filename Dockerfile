FROM maven:3.9.3-eclipse-temurin-11 as builder
#Definir pasta da build como espaço de trabalho
WORKDIR /app/source
#Copiar arquivos do projeto para pasta da build
COPY . .
#Rodar o maven e realizar o build
RUN mkdir /root/.m2
RUN chmod +x /root/.m2
RUN mv settings.xml /root/.m2/
RUN mvn -f pom.xml -DskipTests -P production clean package
RUN ls
RUN ls target
ENTRYPOINT ["java","-jar","./target/virtus-backend-0.0.1-SNAPSHOT.jar"]