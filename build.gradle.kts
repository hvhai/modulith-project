plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.hibernate.orm") version "6.5.2.Final"
	id("org.graalvm.buildtools.native") version "0.10.2"
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

extra["springModulithVersion"] = "1.2.0"

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
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// database
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	// utilities
	implementation("org.apache.commons:commons-collections4:4.4")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")

	// mapstruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	// monitoring
//	implementation("org.springframework.boot:spring-boot-starter-actuator")
//	runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
//	runtimeOnly("org.springframework.modulith:spring-modulith-observability")
//	runtimeOnly("org.springframework.modulith:spring-modulith-starter-insight")
//	runtimeOnly("io.micrometer:micrometer-tracing-bridge-otel")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")

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

