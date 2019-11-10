package cn.edu.nju.cs;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MemoryTraceMethodAdapter extends MethodVisitor {

    public MemoryTraceMethodAdapter(MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, methodVisitor);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (mv == null)
            return;

        if (opcode == Opcodes.GETFIELD) {
            MemoryTraceLog.logf("getfield %s %s %s", owner, name, descriptor);
            mv.visitInsn(Opcodes.DUP);
            mv.visitLdcInsn(owner);
            mv.visitLdcInsn(name);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(),
                    "traceGETFIELD", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V",
                    false);
        } else if (opcode == Opcodes.PUTFIELD) {

        } else if (opcode == Opcodes.GETSTATIC) {

        } else if (opcode == Opcodes.PUTSTATIC) {

        }

        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 3, maxLocals);
    }

}
