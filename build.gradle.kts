plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-cli:commons-cli:1.5.0")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes("Main-Class" to "app.Main")
    }
    archiveBaseName.set("util")
    archiveClassifier.set("")
    archiveVersion.set("")
}
