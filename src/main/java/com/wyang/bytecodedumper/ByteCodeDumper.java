package com.wyang.bytecodedumper;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.ProtectionDomain;

/**
 * @author weichangyang
 */
public class ByteCodeDumper implements ClassFileTransformer {
  private String targetClassName;
  private Path dumpDirectory;

  public ByteCodeDumper(String targetClassName, Path dumpDirectory) {
    this.targetClassName = targetClassName;
    this.dumpDirectory = dumpDirectory;
  }

  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

    if (className.startsWith(targetClassName)) {
      Path classFile = dumpDirectory.resolve(class2fileName(className));
      try {
        Files.write(classFile, classfileBuffer);
        System.out.println(String.format("Writing %s to %s", targetClassName, classFile.toAbsolutePath()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return classfileBuffer;
  }

  private String class2fileName(String className) {
    return className.replace('/', '_').replace('$', '_') + ".class";
  }
}
