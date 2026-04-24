# Используем легкий образ Java
FROM openjdk:17-jdk-slim

# Копируем наш файл в контейнер
COPY Decclapp.java /app/Decclapp.java
WORKDIR /app

# Компилируем Java файл напрямую
RUN javac Decclapp.java

# Запускаем
CMD ["java", "Decclapp"]
