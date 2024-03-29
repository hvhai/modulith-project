plugins {
	java
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.hibernate.orm") version "6.4.1.Final"
	id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "com.codehunter"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springModulithVersion"] = "1.1.2"

dependencies {
	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")

	// modulith
	implementation("org.springframework.modulith:spring-modulith-starter-core")
	implementation("org.springframework.modulith:spring-modulith-starter-jpa")
	testImplementation("org.springframework.modulith:spring-modulith-starter-test")

	// security
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

	// Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// database
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	// utilities
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")

	// monitoring
//	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
//	runtimeOnly("org.springframework.modulith:spring-modulith-observability")
//	runtimeOnly("org.springframework.modulith:spring-modulith-starter-insight")
//	runtimeOnly("io.micrometer:micrometer-tracing-bridge-otel")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// custom
	implementation(project(":common"))
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

hibernate {
	enhancement {
		enableAssociationManagement.set(true)
	}
}

