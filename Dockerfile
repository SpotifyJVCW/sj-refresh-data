FROM maven:3-openjdk-11

ADD . /cxfbootsimple
WORKDIR /cxfbootsimple

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install

FROM openjdk:11-oracle
ARG RELEASE
ENV BUILD_RELEASE=$RELEASE
LABEL "vendor"="Spotify Wudarski" \
        "version"="${BUILD_RELEASE}"
RUN groupadd -r springapp && useradd --no-log-init -r -g springapp springapp
RUN mkdir /app

WORKDIR /app

COPY ./target/sj-refresh-data.jar /app/app.jar
COPY ./application-docker.yaml /app/

USER springapp
CMD [ "sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar --spring.config.location=file:///app/application-docker.yaml" ]
