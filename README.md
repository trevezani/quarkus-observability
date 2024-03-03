# Observability

## Extensions

```shell script
./mvnw quarkus:add-extension -D"extensions=quarkus-smallrye-fault-tolerance"
./mvnw quarkus:add-extension -D"extensions=opentelemetry"
```

## Test

```shell
curl http://localhost:8081/hello
```

```shell
curl http://localhost:8081/hello/locale/es
```

## Observability

[Jaeger](http://localhost:16686/search)

```shell
docker run --name=jaeger -d -p 16686:16686 -p 4317:4317 -e COLLECTOR_OTLP_ENABLED=true jaegertracing/all-in-one:1.54
```

## References

[OpenTelemetry](https://pt.quarkus.io/guides/opentelemetry)

[Fault Tolerance](https://redhat-developer-demos.github.io/quarkus-tutorial/quarkus-tutorial/fault-tolerance.html)