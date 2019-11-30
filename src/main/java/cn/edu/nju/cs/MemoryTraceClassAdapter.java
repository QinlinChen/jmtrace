package cn.edu.nju.cs;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MemoryTraceClassAdapter extends ClassVisitor {

    private boolean enableMemoryTrace = true;

    private static String[] libList = new String[] {"java", "jdk", "sun/launcher"};

    public MemoryTraceClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
            String[] interfaces) {
        if (isLibClass(name)) {
            enableMemoryTrace = false;
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
            String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (enableMemoryTrace) {
            return new MemoryTraceMethodAdapter(mv);
        }
        return mv;
    }

    private static boolean isLibClass(String className) {
        for (String lib : libList) {
            if (className.startsWith(lib)) {
                return true;
            }
        }
        return false;
    }
}
