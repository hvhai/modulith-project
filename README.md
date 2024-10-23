# Modulith project using Spring Native and Spring Modulith

## Commands
``` shell 
docker build . --tag modulith-project:latest --platform=linux/amd64
docker run --rm -p 8080:8080 modulith-project:latest
docker run --rm -p 8080:8080 -e APP_METHOD_API_TOKEN='' -e APP_H2_PASS='' -e CLIENT_ID='' -e CLIENT_SECRET='' -e DOMAIN='' modulith-project:latest
```
```shell
docker tag modulith-project:latest codehunter6323/modulith-project:latest
docker push codehunter6323/modulith-project:latest
```
```shell
# show docker account list
less ~/.docker/config.json
# run docker image test
docker run -it --rm --entrypoint /bin/bash ghcr.io/graalvm/native-image-community:21
 
docker run -it --rm --entrypoint /bin/bash ghcr.io/graalvm/graalvm-community:21
docker run -it --rm --entrypoint /bin/bash ghcr.io/graalvm/native-image-community:21-muslib
docker run -it --rm --entrypoint /bin/bash ghcr.io/graalvm/jdk-community:21
```

```shell
# run local zipkin
docker run -d -p 9411:9411 openzipkin/zipkin  
```

https://hilla.dev/blog/ai-chatbot-in-java/deploying-a-spring-boot-app-as-a-graalvm-native-image-with-docker/

[GraalVM gu remove](https://github.com/oracle/graal/issues/6855)


## Food Ordering flow
![fruits-ordering-flow.png](doc%2Ffruits-ordering-flow.png)
