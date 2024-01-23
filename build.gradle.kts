val lombokVersion: String by project
val openCsvVersion: String by project
val junitVersion: String by project

plugins {
    id("java")
}

group = "org.pasichniuk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    implementation("com.opencsv:opencsv:${openCsvVersion}")

    testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
}

tasks.test {
    useJUnitPlatform()
}