opengl-graal-examples
=====================
Demo of a rotating Utah teapot, built using OpenGL and 
[GraalVM](https://github.com/oracle/graal). 

![Demo](https://raw.githubusercontent.com/praj-foss/opengl-graal-examples/master/teapot-small.gif)

It's written completely in Java and uses GraalVM's native-image 
utility to compile down to a stand-alone native binary as small 
as **6mb**. This binary does not require any Java runtime and launch
as fast as their native equivalents written in C/C++. The source
code is almost a direct translation of the examples from
[Rosetta Code](https://rosettacode.org/wiki/OpenGL/Utah_Teapot).

## Building
You'll need JDK 11 or higher installed. The project uses Gradle, 
and it should automatically download the GraalVM distribution for
you. Use the following command to build:
```shell script
./gradlew nativeImage
```

GraalVM would take a while to do all the hard work. After the
compilation is over, you can launch the executable using Gradle:
```shell script
./gradlew runNative
```
or run it directly as: 
```shell script
./build/graal/glExample
```
