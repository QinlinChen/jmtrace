package cn.edu.nju.cs;

import java.lang.instrument.Instrumentation;

public class JMTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Adding transformer...");
        inst.addTransformer(new MemoryAccessLogTransformer());
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
