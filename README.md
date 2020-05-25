opengl-graal-examples
===============
Demo of a native [GLFW](https://www.glfw.org) app by built
using Java and [GraalVM](https://github.com/oracle/graal).

The executable opens a blank window and closes it on pressing
ESC. It's written completely in Java, making use of GraalVM
SDK to call the native methods without messing with JNI.
The binary file generated for Linux weighs around 6mb,
boots up in an instant and is completely stand-alone.
This makes GraalVM a potential alternative to develop
simple, lightweight tools using plain Java or other
JVM-based languages.

## Building
The project uses Gradle, and it should automatically download
the GraalVM distribution for you. To build this project, use the
following command:
```shell script
./gradlew nativeImage
```

It would take a while to do all necessary setup. After the
compilation is done, you can launch the executable:
```shell script
./build/graal/glExample
```

