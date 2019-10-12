FROM openjdk:8-jre-alpine
#Alpine docker image doesn't have bash installed by default. 
#You will need to add following commands to get bash:
RUN apk add --no-cache bash
# Adding curl
RUN apk --no-cache add curl
ENV APPROOT="/app"
WORKDIR $APPROOT
COPY target/kubia-1.0.jar $APPROOT
ENTRYPOINT ["java"]
CMD ["-jar", "-Xms256m", "-Xmx256m", "kubia-1.0.jar"]