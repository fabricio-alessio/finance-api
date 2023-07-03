import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

group = "com.fasolutions"
version = "1.0"
description = "finance-api"
java.sourceCompatibility = JavaVersion.VERSION_17

extra["springCloudVersion"] = "2021.0.0"
extra["testcontainersVersion"] = "1.16.2"
extra["springCloudAwsVersion"] = "2.3.3"
extra["openApiUiVersion"] = "1.6.4"
extra["kotlinLoggingVersion"] = "2.1.21"
extra["logbackGelfVersion"] = "4.0.2"
extra["mockkVersion"] = "1.12.2"
extra["springMockkVersion"] = "3.1.0"
extra["testContainerVersion"] = "1.16.3"
extra["jjwtVersion"] = "0.11.2"
extra["javaJwtVersion"] = "3.18.3"
extra["mockServerVersion"] = "5.11.2"
extra["equalsverifierVersion"] = "3.7.2"
extra["cloudwatchLogbackAppenderVersion"] = "2.1"
extra["logbackContribJacksonVersion"] = "0.1.5"
extra["okhttpVersion"] = "4.10.0"
extra["agentExtractionRoot"] = "$buildDir/jib-agents"

plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("info.solidsoft.pitest") version "1.5.1"
    id("com.google.cloud.tools.jib") version "3.2.0"
    id("org.sonarqube") version "3.1.1"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("net.linguica.maven-settings") version "0.5"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    jacoco
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")
    implementation("com.auth0:java-jwt:${property("javaJwtVersion")}")
    implementation("io.awspring.cloud:spring-cloud-starter-aws-messaging:${property("springCloudAwsVersion")}")
    implementation("org.springdoc:springdoc-openapi-ui:${property("openApiUiVersion")}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${property("openApiUiVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging:${property("kotlinLoggingVersion")}")
    implementation("com.j256.cloudwatchlogbackappender:cloudwatchlogbackappender:${property("cloudwatchLogbackAppenderVersion")}")
    implementation("ch.qos.logback.contrib:logback-jackson:${property("logbackContribJacksonVersion")}")
    implementation("ch.qos.logback.contrib:logback-json-classic:${property("logbackContribJacksonVersion")}")
    implementation("com.squareup.okhttp3:okhttp:${property("okhttpVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:${property("mockkVersion")}")
    testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")
    testImplementation("org.testcontainers:testcontainers:${property("testContainerVersion")}")
    testImplementation("org.testcontainers:localstack:${property("testContainerVersion")}")
    testImplementation("org.testcontainers:junit-jupiter:${property("testContainerVersion")}")
    testImplementation("org.mock-server:mockserver-spring-test-listener:${property("mockServerVersion")}")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:${property("equalsverifierVersion")}")
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.test {
    useJUnitPlatform {
        filter {
            excludeTags("Integration")
            excludeTags("Component")
        }
    }
}

tasks.create<Test>("integrationTests") {
    useJUnitPlatform {
        filter {
            includeTags("Integration")
        }
    }
}

tasks.create<Test>("componentTests") {
    useJUnitPlatform {
        filter {
            includeTags("Component")
        }
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    executionData.setFrom(fileTree(buildDir).include("/jacoco/*.exec"))
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacocoHtml"))
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {

        rule {
            element = "CLASS"
            excludes = listOf(
                "com.fasolutions.finance.adapter.*",
                "com.fasolutions.finance.application.port.*",
                "**.ApplicationKt",
                "**.*.log.*",
                "**.lambda.*",
                "**.1*",
                "**.1.*",
                "**.2.*"
            )

            limit {
                enabled = true
                counter = "LINE"
                value = "COVEREDRATIO"
                maximum = "0.9".toBigDecimal()
            }
        }
    }
}

ktlint {
    reporters {
        reporter(ReporterType.CHECKSTYLE)
    }
}

sonarqube {
    properties {
        property(
            "sonar.exclusions",
            "**/finance/adapter/**, " +
                "**/finance/application/port/**, " +
                "**/ApplicationKt.*"
        )
    }
}

pitest {
    junit5PluginVersion.set("0.15")
    timestampedReports.set(false)
    mutationThreshold.set(90)
    timeoutConstInMillis.set(4000)
    mutators.set(setOf("CONDITIONALS_BOUNDARY", "INCREMENTS", "INVERT_NEGS", "MATH", "NEGATE_CONDITIONALS"))
    targetClasses.set(setOf("com.fasolutions.finance.application.*"))
    targetTests.set(setOf("*Test", "*Tests"))
    excludedMethods.set(setOf("hashCode"))
    outputFormats.set(setOf("HTML"))
    configurations { failWhenNoMutations.set(false) }
}
