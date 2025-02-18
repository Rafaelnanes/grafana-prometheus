# grafana-prometheus

### Pending

- [ ] Add https requirement
- [ ] Grafana is not loading the datasources
- [ ] Add gatling to create a lot of requests
- [ ] Add endpoint to starve the cpu memory
- [ ] Add endpoint to wait for a long time

#### Build Product-app

```
gradle -p /home/rafael/dev/projects/grafana-prometheus/apps/product-app bootBuildImage
```

#### Build User-app

```
gradle -p /home/rafael/dev/projects/grafana-prometheus/apps/user-app bootBuildImage
```

#### Create containers based on docker compose

```
docker compose -f docker-compose.yml up
```
