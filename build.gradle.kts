plugins {
    kotlin("jvm") version "2.0.0"
    id("maven-publish")
}

group = "com.mirage.utils"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

publishing {
    repositories {
        maven {
            name = "myDomainRepository"
            url = uri("http://localhost/private")
            credentials {
                username = "airdead"
                password = "9dBEWfzwOtDjCN00rTV7cyqUKbpirEOVVQCUlKgLoi8gAwZqY18OYqbefY9FQXZ/"
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
            isAllowInsecureProtocol = true
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.mirage"
            artifactId = "utils"
            version = "1.0.5"
            from(components["java"])
        }
    }
}
