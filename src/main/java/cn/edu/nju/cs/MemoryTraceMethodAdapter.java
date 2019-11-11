package cn.edu.nju.cs;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MemoryTraceMethodAdapter extends MethodVisitor {

    public MemoryTraceMethodAdapter(MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, methodVisitor);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (mv == null) {
            return;
        }

        if (opcode == Opcodes.GETFIELD) {
            visitTraceGETFIELD(owner, name, descriptor);
        } else if (opcode == Opcodes.PUTFIELD) {
            visitTracePUTFIELD(owner, name, descriptor);
        } else if (opcode == Opcodes.GETSTATIC) {
            visitTraceGETSTATIC(owner, name, descriptor);
        } else if (opcode == Opcodes.PUTSTATIC) {
            visitTracePUTSTATIC(owner, name, descriptor);
        }

        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    private void visitTraceGETFIELD(String owner, String name, String descriptor) {
        MemoryTraceLog.logf("getfield %s %s %s\n", owner, name, descriptor);
        mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logGETFIELD",
                "(Ljava/lang/Object;Ljava/lang/String;)V", false);
    }

    private void visitTracePUTFIELD(String owner, String name, String descriptor) {
        MemoryTraceLog.logf("putfield %s %s %s\n", owner, name, descriptor);
        mv.visitInsn(Opcodes.DUP2);
        mv.visitInsn(Opcodes.POP);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logPUTFIELD",
                "(Ljava/lang/Object;Ljava/lang/String;)V", false);
    }

    private void visitTraceGETSTATIC(String owner, String name, String descriptor) {
        MemoryTraceLog.logf("getstatic %s %s %s\n", owner, name, descriptor);
        mv.visitLdcInsn(owner);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logGETSTATIC",
                "(Ljava/lang/String;Ljava/lang/String;)V", false);
    }

    private void visitTracePUTSTATIC(String owner, String name, String descriptor) {
        MemoryTraceLog.logf("putstatic %s %s %s\n", owner, name, descriptor);
        mv.visitLdcInsn(owner);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logPUTSTATIC",
                "(Ljava/lang/String;Ljava/lang/String;)V", false);
    }

    @Override
    public void visitInsn(int opcode) {
        // int IALOAD = 46; // visitInsn
        // int LALOAD = 47; // -
        // int FALOAD = 48; // -
        // int DALOAD = 49; // -
        // int AALOAD = 50; // -
        // int BALOAD = 51; // -
        // int CALOAD = 52; // -
        // int SALOAD = 53; // -

        super.visitInsn(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 2, maxLocals);
    }

}
