# 使用OpenJDK 11 作为基础镜像
FROM openjdk:11.0

# 在容器内部创建目录
RUN mkdir -p /app/uploads
RUN mkdir -p /app/logs

# 设置工作目录
WORKDIR /app

# 复制应用程序 JAR 包到容器中
COPY jyadmin-starter-1.0-SNAPSHOT.jar ./

# 暴露应用程序端口
EXPOSE 8081

# 启动容器时执行的命令
CMD ["java", "-jar", "jyadmin-starter-1.0-SNAPSHOT.jar"]