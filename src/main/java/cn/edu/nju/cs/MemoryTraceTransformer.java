package cn.edu.nju.cs;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class MemoryTraceTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        MemoryTraceLog.logln("Transform " + className + "...");

        ClassReader reader = new ClassReader(classfileBuffer);
        ClassWriter writer = new ClassWriter(reader, 0);
        MemoryTraceClassAdapter adapter = new MemoryTraceClassAdapter(writer);
        reader.accept(adapter, 0);

        return writer.toByteArray();
    }

}
