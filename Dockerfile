FROM vegardit/graalvm-maven:17.0.8 as builder
WORKDIR /code
ADD . /code
RUN mvn clean package -Pnative

FROM ubuntu:22.04
ENV STATIC_LOCATIONS="file:/opt/application/resources"
RUN apk add libc6-compat
WORKDIR /opt/application/
RUN chown 1001 /opt/application/ \
    && chmod "g+rwX" /opt/application/ \
    && chown 1001:root /opt/application/
COPY --from=builder --chown=1001:root /code/beryl-halia-service/target/beryl-halia-service service
COPY --chown=1001:root beryl-halia-web/build resources
EXPOSE 8080
USER 1001
CMD ["./service"]
