package cn.edu.nju.cs;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MemoryTraceClassAdapter extends ClassVisitor {

    private boolean enableMemoryTrace = false;

    public MemoryTraceClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
            String[] interfaces) {
        if (!isLibClass(name)) {
            enableMemoryTrace = true;
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    private boolean isLibClass(String className) {
        return !className.startsWith("cn/edu/nju/cs");
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

}
