# First stage: JDK with GraalVM
FROM ghcr.io/graalvm/graalvm-community:21 AS build
#FROM ghcr.io/graalvm/native-image-community:21 AS build

# Update package lists and Install Maven
RUN microdnf install yum -y
RUN yum update --allowerasing -y && \
yum install -y gcc glibc-devel zlib-devel libstdc++-devel gcc-c++ && \
yum clean all

RUN yum install -y findutils

WORKDIR /code

COPY . .


#ENV PATH="${PATH}:/usr/lib64/graalvm/graalvm22-ce-java17/bin/gu"
RUN echo $(java --version)
RUN echo $GRAALVM_HOME
RUN chmod +x gradlew
RUN ./gradlew nativeCompile --no-daemon

# Second stage: Lightweight debian-slim image
FROM debian:bookworm-slim

WORKDIR /app

# Copy the native binary from the build stage
COPY --from=build /code/build/native/nativeCompile/modulith-project /app/modulith-project
EXPOSE 8080
# Run the application
ENTRYPOINT ["/app/modulith-project"]