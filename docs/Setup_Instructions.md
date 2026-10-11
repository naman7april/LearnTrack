# Setup Instructions

## JDK version used

```
openjdk version "21.0.12.1" 2026-08-18
OpenJDK Runtime Environment (build 21.0.12.1+1-1-24.04.4-Ubuntu)
OpenJDK 64-Bit Server VM (build 21.0.12.1+1-1-24.04.4-Ubuntu, mixed mode, sharing)
```

The project only uses features available since Java 8 (plus `java.time.LocalDate`), so any JDK 11+ will work.

## Installing the JDK

1. Download a JDK (for example Eclipse Temurin or OpenJDK) from https://adoptium.net.
2. Install it and make sure `JAVA_HOME` is set and `bin/` is on your `PATH`.
3. Verify in a terminal:
   ```bash
   java -version
   javac -version
   ```
   Both commands should print the same major version.

## Hello World

Create `HelloWorld.java`:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Compile and run:

```bash
javac HelloWorld.java    # produces HelloWorld.class (bytecode)
java HelloWorld          # the JVM runs the bytecode
```

Expected output:

```
Hello, World!
```

`javac` is the compiler (part of the JDK); it turns source code into bytecode. `java` starts the JVM, which loads the `.class` file and runs it.

## Running LearnTrack

See the [README](../README.md#how-to-compile-and-run). A sample of real output from the program:

```
=== LearnTrack v1.0 ===

--- Main Menu ---
1. Student Management
2. Course Management
3. Enrollment Management
0. Exit
Choose an option:
```
