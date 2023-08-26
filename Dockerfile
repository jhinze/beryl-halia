FROM vegardit/graalvm-maven:17.0.8 as builder
WORKDIR /code
ADD . /code
RUN mvn clean package -Pnative

FROM alpine:3
ENV STATIC_LOCATIONS="file:/opt/application/resources"
WORKDIR /opt/application
COPY --from=builder /code/beryl-halia-service/target/beryl-halia-service /opt/application/service
COPY --from=builder /code/beryl-halia-web/build/* /opt/application/resources
EXPOSE 8080
CMD ["./service"]
