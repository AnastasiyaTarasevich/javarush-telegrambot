# Используем образ с Java 17
FROM openjdk:17

# Копируем JAR файл из папки target в контейнер
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Устанавливаем точку входа для приложения
ENTRYPOINT ["java", "-jar", "/app.jar"]
