FROM registry.cn-hangzhou.aliyuncs.com/library-bd/openjdk:8-jdk-alpine
LABEL maintainer="wf@soft1851.com"
# 指定工作目录
WORKDIR /app
COPY  spring-cloud-0.0.1-SNAPSHOT.jar ./content-center.jar
# 设置时区为上海
ENV TZ Asia/Shanghai
# 设置时区为上海
ENV JAVA_OPS -Duser.timezone=Asia/Shanghai
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./content-center.jar"]
