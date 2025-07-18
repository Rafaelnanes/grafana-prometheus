# grafana-prometheus

### Pending

- [ ] Add https requirement
- [ ] Grafana is not loading the datasources
- [ ] Add gatling to create a lot of requests
- [ ] Add endpoint to starve the cpu memory

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

#### Create multiple requests

```
ab -n 100 -c 10 http://localhost:8080/users/async
```

#### Start grafana in linux

```
sudo /bin/systemctl start grafana-server
```

Then run it on http://localhost:3000

#### To extract the java heap dump file

```
sudo ss -tulnp | grep ":8080"
jmap -dump:live,format=b,file=/path/to/your/heapdump.hprof 37869
```
