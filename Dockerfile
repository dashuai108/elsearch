From openjdk:8u222 
COPY index.jar /app.jar
EXPOSE 9099
WORKDIR /
#统一时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/app.jar"]
