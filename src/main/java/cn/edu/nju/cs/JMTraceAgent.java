package cn.edu.nju.cs;

import java.lang.instrument.Instrumentation;

public class JMTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new MemoryTraceTransformer());
    }

    public static void main(String[] args) {
        System.out.println("Hello, JMTraceAgent!");
    }

}
