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

    private static PrintStream logStream = System.out;

    public static void logln(String x) {
        logStream.println(x);
    }

    public static PrintStream logf(String format, Object... args) {
        return logStream.printf(format, args);
    }

    // --------------------------------------------------------------------------------------------
    // Trace utilities.
    // --------------------------------------------------------------------------------------------

    public static void logGETFIELD(Object obj, String fieldName) {
        logAccessField("R", obj, fieldName);
    }

    public static void logPUTFIELD(Object obj, String fieldName) {
        logAccessField("W", obj, fieldName);
    }

    public static void logGETSTATIC(String className, String fieldName) {
        logAccessStaticField("R", className, fieldName);
    }

    public static void logPUTSTATIC(String className, String fieldName) {
        logAccessStaticField("W", className, fieldName);
    }

    public static void logXALOAD(Object obj, int index) {
        logAccessArray("R", obj, index);
    }

    public static void logXASTORE(Object obj, int index) {
        logAccessArray("W", obj, index);
    }

    private static void logAccessField(String op, Object obj, String fieldName) {
        String className = obj.getClass().getCanonicalName();
        logAccess(op, obj, className + "." + fieldName);
    }

    private static void logAccessStaticField(String op, String className, String fieldName) {
        className = className.replace("/", ".");
        Object classObj = null;
        try {
            classObj = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logln(e.getMessage());
        }
        logAccess(op, classObj, className + "." + fieldName);
    }

    private static void logAccessArray(String op, Object obj, int index) {
        String arrayType = obj.getClass().getComponentType().getCanonicalName();
        String descriptor = String.format("%s[%d]", arrayType, index);
        logAccess(op, obj, descriptor);
    }

    private static void logAccess(String op, Object obj, String descriptor) {
        logf("%s %d %x %s\n", op, Thread.currentThread().getId(), System.identityHashCode(obj),
                descriptor);
    }

}
