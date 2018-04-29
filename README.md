When I was debugging Spring AOP, it's difficult to inspect the source code of generated classes. A proxy class name would start with original class name, followed by some random chars. I wrote this tool to dump it into a file.

# How to build

    ./gradlew clean build

This builds both the main project and the sample app.

# How to use

Launch your application, using the lib as java agent:

    $ java -javaagent:build/libs/bytecode-dumper-1.0.0.jar=org/acme/Foo -jar sample-app/build/libs/sample-app-1.0.0.jar
    Writing org/acme/Foo to org_acme_Foo123.class
    Hello world

At runtime, it will intercept classes whose fully qualified class name starts with ``org.acme.Foo``. The content of the class definition is dumped to ``c:/git/tmp/<fully_qualified_class_name>.class`` You can then use `javap` or a de-compiler to view it.

    $ javap -c org_acme_Foo123.class
    Compiled from "Foo123.java"
    public class org.acme.Foo123 {
      public org.acme.Foo123();
        Code:
           0: aload_0
           1: invokespecial #1                  // Method java/lang/Object."<init>":()V
           4: return
    
      public java.lang.String say();
        Code:
           0: ldc           #2                  // String Hello world
           2: areturn
    }
    