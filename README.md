# grafana-prometheus

### Pending

- [ ] Add zipkin
- [ ] Add grafana
- [ ] Understand opentelemetry

#### Build Product-app

```
gradle -p /home/rafael/dev/projects/grafana-prometheus/apps/product-app bootJar
```

#### Build User-app

```
gradle -p /home/rafael/dev/projects/grafana-prometheus/apps/user-app bootJar
```

#### Create containers based on docker compose

```
docker compose -f docker-compose.yml up
```
