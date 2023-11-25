
# Modulith project using Spring Native and Spring Modulith

```shell
docker build . --tag modulith-project:latest --platform=linux/amd64
docker run -p 8080:8080 modulith-project:latest
```
```shell
docker tag modulith-project:latest codehunter6323/modulith-project:latest
docker push codehunter6323/modulith-project:latest
```
```shell
# show docker account list
less ~/.docker/config.json
```

https://hilla.dev/blog/ai-chatbot-in-java/deploying-a-spring-boot-app-as-a-graalvm-native-image-with-docker/