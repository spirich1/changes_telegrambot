import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
	kotlin("plugin.jpa") version "1.9.20"
}

group = "com.spirich"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.hibernate:hibernate-core:6.4.2.Final")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.7.1")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("org.telegram:telegrambots:6.8.0")
	implementation("org.jsoup:jsoup:1.16.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
