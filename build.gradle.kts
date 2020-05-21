plugins {
    java
    id("com.palantir.graal") version "0.7.0"
}

group = "in.praj"
version = "0.0.1-SNAPSHOT"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

graal {
    graalVersion("20.0.0")
    javaVersion("11")

    mainClass("in.praj.glexamples.Main")
    outputName("glExample")
    option("--verbose")
    option("--no-server")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.graalvm.sdk", "graal-sdk", graal.graalVersion.get())
    testImplementation("junit", "junit", "4.12")
}