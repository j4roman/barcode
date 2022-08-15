FROM openjdk:8-jdk-alpine

RUN apk add --no-cache curl tar bash procps

ARG MAVEN_VERSION=3.8.6
ARG USER_HOME_DIR="/root"
ARG SHA=f790857f3b1f90ae8d16281f902c689e4f136ebe584aba45e4b1fa66c80cba826d3e0e52fdd04ed44b4c66f6d3fe3584a057c26dfcac544a60b301e6d0f91c26
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

ARG PROJECT_NAME=BarCode
ARG PROJECT_DIR=/usr/local/projects/${PROJECT_NAME}

COPY src ${PROJECT_DIR}/src
COPY pom.xml ${PROJECT_DIR}/pom.xml
WORKDIR ${PROJECT_DIR}
RUN mvn package

WORKDIR /

ARG APP_USERNAME=barcodeuser
ARG APP_GROUP=barcodegroup

RUN addgroup -S ${APP_GROUP} && adduser -S ${APP_USERNAME} -G ${APP_GROUP}
RUN mkdir /home/logs && chown ${APP_USERNAME}:${APP_GROUP} /home/logs && chmod u+w /home/logs

RUN mkdir docker-entrypoint && cp ${PROJECT_DIR}/target/${PROJECT_NAME}.jar /docker-entrypoint/app.jar

USER ${APP_USERNAME}:${APP_GROUP}
ENTRYPOINT ["java","-jar","/docker-entrypoint/app.jar"]