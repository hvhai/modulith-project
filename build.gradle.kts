plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.hibernate.orm") version "6.6.1.Final"
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

extra["springModulithVersion"] = "1.2.4"

dependencies {
	// spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework:spring-webflux")

	// modulith
	implementation("org.springframework.modulith:spring-modulith-starter-core")
	implementation("org.springframework.modulith:spring-modulith-starter-jpa")
	testImplementation("org.springframework.modulith:spring-modulith-starter-test")

	// security
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

	// Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	// database
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.github.gavlyukovskiy:datasource-proxy-spring-boot-starter:1.10.0")
	runtimeOnly("com.h2database:h2")
	implementation("com.mysql:mysql-connector-j")
	testImplementation("org.testcontainers:mysql")

	// utilities
	implementation("org.apache.commons:commons-collections4:4.4")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")

	// mapstruct
	implementation("org.mapstruct:mapstruct:1.6.2")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")

	// monitoring
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.modulith:spring-modulith-starter-insight")
	implementation("io.micrometer:micrometer-tracing-bridge-otel")
	implementation("io.opentelemetry:opentelemetry-exporter-zipkin")

	// UI
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("com.okta.spring:okta-spring-boot-starter:3.0.7")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.c4-soft.springaddons:spring-addons-oauth2-test:7.1.10")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.wiremock:wiremock-standalone:3.9.2")

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

