plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //testImplementation(platform("org.junit:junit-bom:5.10.0"))
    //testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0") // Версія JUnit
    testImplementation("org.mockito:mockito-core:5.5.0") // Версія Mockito
    testImplementation("org.mockito:mockito-junit-jupiter:5.5.0") // Інтеграція з JUnit 5
    compileOnly ("org.projectlombok:lombok:1.18.28")
    annotationProcessor ("org.projectlombok:lombok:1.18.28")
}

tasks.test {
    useJUnitPlatform()
}