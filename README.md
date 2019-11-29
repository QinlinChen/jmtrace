# jmtrace

Trace memory access in Java programs.

## Build
We use `maven` as our build tool.
It is easy to build by 

    mvn package

and the result package is `target/jmtrace-<version>-jar-with-dependencies.jar`

## Usage

The usage is same to the `java` command. For example,
if you have a JAR package `Hello.jar` and want to trace
its memory accesses, you can type the following command:

    ./jmtrace -jar Hello.jar

and memory access logs will be printed to `stdout` in the format:

    R(ead)|W(rite) <threadId> <ObjectId> <ObjectType.fieldName|ClassName.staticFiledName|ArrayType[index]>

Note that `./jmtrace` is in fact a shell script
and you can indicate the path of JAR packaged by maven.
The default path is `target/jmtrace-<version>-jar-with-dependencies.jar`.