package cn.edu.nju.cs;

import java.io.PrintStream;

public class MemoryTraceLog {

    // --------------------------------------------------------------------------------------------
    // Metadata for MethodAdapter use.
    // --------------------------------------------------------------------------------------------

    private static final String internalName =
            MemoryTraceLog.class.getCanonicalName().replace(".", "/");

    public static String getInternelName() {
        return internalName;
    }

    // --------------------------------------------------------------------------------------------
    // Log utilities.
    // --------------------------------------------------------------------------------------------

    private static PrintStream logStream = System.err;

    public static void logln(String x) {
        logStream.println(x);
    }

    public static PrintStream logf(String format, Object... args) {
        return logStream.printf(format, args);
    }

    // --------------------------------------------------------------------------------------------
    // Trace utilities.
    // --------------------------------------------------------------------------------------------

    private static void traceAccess(String op, Object obj, String descriptor) {
        logf("%s %d %x %s\n", op, Thread.currentThread().getId(), System.identityHashCode(obj),
                descriptor);
    }

    private static void traceAccessField(String op, Object obj, String className,
            String fieldName) {
        String descriptor = String.format("%s.%s", className.replace("/", "."), fieldName);
        traceAccess(op, obj, descriptor);
    }

    // private static void traceAccessArray(String op, Object obj, String className,
    // int index) {
    // String descriptor = String.format("%s[%d]", className.replace("/", "."),
    // index);
    // traceAccess(op, obj, descriptor);
    // }

    public static void traceGETFIELD(Object obj, String className, String fieldName) {
        traceAccessField("R", obj, className, fieldName);
    }

    public static void tracePUTFIELD(Object obj, String className, String fieldName) {
        traceAccessField("W", obj, className, fieldName);
    }

    public static void traceGETSTATIC(String className, String fieldName) {
        // traceAccessField("R", className, fieldName);
    }

    public static void tracePUTSTATIC(String className, String fieldName) {
        // traceAccessField("W", className, fieldName);
    }

    public static void traceASTORE() {

    }

    public static void traceALOAD() {

    }

}
