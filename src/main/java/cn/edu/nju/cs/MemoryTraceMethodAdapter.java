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

    @Override
    public void visitInsn(int opcode) {
        if (mv == null) {
            return;
        }

        if (opcode >= Opcodes.IALOAD && opcode <= Opcodes.SALOAD) {
            visitTraceXALOAD(opcode);
        } else if (opcode >= Opcodes.IASTORE && opcode <= Opcodes.SASTORE) {
            visitTraceXASTORE(opcode);
        }

        super.visitInsn(opcode);
    }

    private void visitTraceGETFIELD(String owner, String name, String descriptor) {
        // ..., obj
        mv.visitInsn(Opcodes.DUP);
        // ..., obj, obj
        mv.visitLdcInsn(name);
        // ..., obj, obj, str
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logGETFIELD",
                "(Ljava/lang/Object;Ljava/lang/String;)V", false);
        // ..., obj
    }

    private void visitTracePUTFIELD(String owner, String name, String descriptor) {
        // ..., obj, val...
        if (isTwoSlotSizeValue(descriptor)) {
            visitSwap21();
        } else {
            mv.visitInsn(Opcodes.SWAP);
        }
        // ..., val..., obj
        mv.visitInsn(Opcodes.DUP);
        // ..., val..., obj, obj
        mv.visitLdcInsn(name);
        // ..., val..., obj, obj, str
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logPUTFIELD",
                "(Ljava/lang/Object;Ljava/lang/String;)V", false);
        // ..., val..., obj
        if (isTwoSlotSizeValue(descriptor)) {
            visitSwap12();
        } else {
            mv.visitInsn(Opcodes.SWAP);
        }
        // ..., obj, val...
    }

    private void visitTraceGETSTATIC(String owner, String name, String descriptor) {
        // ...
        mv.visitLdcInsn(owner);
        // ..., str
        mv.visitLdcInsn(name);
        // ..., str, str
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logGETSTATIC",
                "(Ljava/lang/String;Ljava/lang/String;)V", false);
        // ...
    }

    private void visitTracePUTSTATIC(String owner, String name, String descriptor) {
        // ...
        mv.visitLdcInsn(owner);
        // ..., str
        mv.visitLdcInsn(name);
        // ..., str, str
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logPUTSTATIC",
                "(Ljava/lang/String;Ljava/lang/String;)V", false);
        // ...
    }

    private void visitTraceXALOAD(int opcode) {
        // ..., arr, index
        mv.visitInsn(Opcodes.DUP2);
        // ..., arr, index, arr, index
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logXALOAD",
                "(Ljava/lang/Object;I)V", false);
        // ..., arr, index
    }

    private void visitTraceXASTORE(int opcode) {
        // ..., arr, index, val...
        if (isTwoSlotSizeValue(opcode)) {
            visitSwap22();
        } else {
            visitSwap12();
        }
        // ..., val..., arr, index
        mv.visitInsn(Opcodes.DUP2);
        // ..., val..., arr, index, arr, index
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, MemoryTraceLog.getInternelName(), "logXASTORE",
                "(Ljava/lang/Object;I)V", false);
        // ..., val..., arr, index,
        if (isTwoSlotSizeValue(opcode)) {
            visitSwap22();
        } else {
            visitSwap21();
        }
        // ..., arr, index, val...
    }

    private void visitSwap21() {
        // ..., u, v1, v2
        mv.visitInsn(Opcodes.DUP2_X1);
        // ..., v1, v2, u, v1, v2
        mv.visitInsn(Opcodes.POP2);
        // ..., v1, v2, u
    }

    private void visitSwap22() {
        // ..., u1, u2, v1, v2
        mv.visitInsn(Opcodes.DUP2_X2);
        // ..., v1, v2, u1, u2, v1, v2
        mv.visitInsn(Opcodes.POP2);
        // ..., v1, v2, u1, u2
    }

    private void visitSwap12() {
        // ..., u1, u2, v
        mv.visitInsn(Opcodes.DUP_X2);
        // ..., v, u1, u2, v
        mv.visitInsn(Opcodes.POP);
        // ..., v, u1, u2
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 2, maxLocals);
    }

    private boolean isTwoSlotSizeValue(String descriptor) {
        return descriptor.equals("J") || descriptor.equals("D");
    }

    private boolean isTwoSlotSizeValue(int opcode) {
        return opcode == Opcodes.LASTORE || opcode == Opcodes.DASTORE;
    }

}
