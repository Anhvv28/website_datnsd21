# Sử dụng OpenJDK làm base image
FROM openjdk:17-jdk-slim

# Tạo và chuyển đến thư mục làm việc
WORKDIR /app

# Copy file JAR vào container
COPY target/nice-shoes-be-0.0.1-SNAPSHOT.jar app.jar

# Lệnh để chạy hàm DBGenerator
ENTRYPOINT ["java", "-cp", "app.jar", "com.fpt.tool.DBGenerator"]
