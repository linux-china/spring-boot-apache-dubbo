Dubbo Registry Server
=====================

Dubbo simple registry server in memory.

### ports

* Web port: 20888
* Dubbo port: 9090

### docker compose

```yaml
 dubbo-simple-registry:
    image: dubbo/simple-registry
    ports:
      - "20888:20888"
      - "9090:9090"
```