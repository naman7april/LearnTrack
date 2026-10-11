# JVM Basics

## JDK, JRE and JVM

- **JVM (Java Virtual Machine)** is the program that actually runs Java bytecode. It loads classes, checks them, manages memory (including garbage collection) and executes the code on your operating system.
- **JRE (Java Runtime Environment)** is the JVM plus the standard class libraries (strings, collections, I/O...). It is everything you need to *run* a Java program.
- **JDK (Java Development Kit)** is the JRE plus developer tools such as `javac` (the compiler), `jar` and `javadoc`. You need the JDK to *write and compile* Java programs.

A simple way to remember it: **JDK ⊃ JRE ⊃ JVM**. (Modern JDK distributions no longer ship a separate JRE, but the idea still helps.)

## What is bytecode?

When you compile a `.java` file with `javac`, it is not turned into machine code for your specific processor. Instead it becomes **bytecode** in a `.class` file: a compact, platform-independent set of instructions designed for the JVM. The JVM then interprets this bytecode, and its JIT (just-in-time) compiler translates frequently used parts into fast native machine code while the program runs.

```
Student.java --javac--> Student.class (bytecode) --JVM--> runs on Windows / Linux / macOS
```

## "Write once, run anywhere"

Because Java is compiled to bytecode rather than to machine code for one platform, the same `.class` files can run on any machine that has a JVM. Windows, Linux and macOS each have their own JVM, but they all understand the same bytecode, so you do not need to recompile your program for each operating system.

The JVM acts as a translator between the portable bytecode and the specific computer underneath. The developer writes and compiles once; the platform-specific work is handled by the JVM installed on each machine.
