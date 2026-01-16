plugins {
    id("java")
}

group = "me.david"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    compileOnly(files("libs/HytaleServer.jar"))
}

tasks.processResources {
    filesMatching("manifest.json") {
        expand(
            "group" to project.group,
            "name" to project.name,
            "version" to project.version
        )
    }
}
