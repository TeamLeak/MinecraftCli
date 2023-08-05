package com.github.leanfe.agent;

import org.objectweb.asm.*;

public class EventBusTransformer {
    public static byte[] transform(byte[] classBytes) {
        ClassReader classReader = new ClassReader(classBytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM7, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);

                // Replace the method contents with the desired code
                if (name.equals("onInitialLoad")) {
                    MethodVisitor finalMethodVisitor = methodVisitor;
                    methodVisitor = new MethodVisitor(Opcodes.ASM7, finalMethodVisitor) {
                        @Override
                        public void visitCode() {
                            super.visitCode();
                            Label label = new Label();
                            finalMethodVisitor.visitLabel(label);
                            // Create and start custom EventBus
                            finalMethodVisitor.visitTypeInsn(Opcodes.NEW, "net/minecraftforge/eventbus/api/EventBus");
                            finalMethodVisitor.visitInsn(Opcodes.DUP);
                            finalMethodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "net/minecraftforge/eventbus/api/BusBuilder", "builder", "()Lnet/minecraftforge/eventbus/api/BusBuilder;", false);
                            finalMethodVisitor.visitInvokeDynamicInsn("start", "()Lnet/minecraftforge/eventbus/api/EventBus;", new Handle(Opcodes.H_INVOKESTATIC, "net/minecraftforge/eventbus/api/BusBuilder", "createExceptionHandler", "()Ljava/util/function/Function;"), Type.getType("Ljava/util/function/Function;"));
                            finalMethodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "net/minecraftforge/eventbus/api/EventBus", "<init>", "(Ljava/util/function/Function;)V", false);
                            finalMethodVisitor.visitVarInsn(Opcodes.ASTORE, 3);
                            finalMethodVisitor.visitVarInsn(Opcodes.ALOAD, 3);
                            finalMethodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/eventbus/api/EventBus", "start", "()V", false);
                        }
                    };
                }

                return methodVisitor;
            }
        };

        classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);

        return classWriter.toByteArray();
    }
}
