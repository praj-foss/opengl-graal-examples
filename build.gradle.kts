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
    option("--no-fallback")
    option("--verbose")
    option("--no-server")
    option("-H:+ReportExceptionStackTraces")
}

repositories {
    mavenCentral()
}

dependencies {
    val graalVer = graal.graalVersion.get()

    compileOnly("org.graalvm.sdk", "graal-sdk", graalVer)
    compileOnly("org.graalvm.nativeimage", "svm", graalVer)
}

tasks.register<Exec>("runNative") {
    dependsOn("nativeImage")
    commandLine("$buildDir/graal/${graal.outputName.get()}")
}