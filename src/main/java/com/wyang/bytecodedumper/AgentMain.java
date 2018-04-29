package com.wyang.bytecodedumper;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author weichangyang
 */
public class AgentMain {
  public static void premain(String agentArgs, Instrumentation inst) throws IOException {
    String targetClassName = agentArgs;
    if(!isValidClassName(targetClassName)) {
      throw new RuntimeException("Error: missing or invalid target class name: " + targetClassName);
    }

    ByteCodeDumper byteCodeDumper = new ByteCodeDumper(agentArgs, Paths.get(""));
    inst.addTransformer(byteCodeDumper);
  }

  private static boolean isValidClassName(String name) {
    return name != null && name.contains("/");
  }
}
