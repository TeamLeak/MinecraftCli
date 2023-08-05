package com.github.leanfe.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClassReloader {
    private static final AtomicBoolean retransformed = new AtomicBoolean(false);

    public static void premain(String agentArgs, Instrumentation inst) {
        ClassFileTransformer transformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (className.equals("net.minecraftforge.fml.loading.FMLLoader")) {
                    // Add your class transformation logic here
                    byte[] transformedClass = EventBusTransformer.transform(classfileBuffer);
                    retransformed.set(true);
                    return transformedClass;
                }
                return classfileBuffer;
            }
        };

        inst.addTransformer(transformer, true);

        // Retransform the classes to apply the changes
        try {
            Class<?> targetClass = Class.forName("net.minecraftforge.fml.loading.FMLLoader");
            inst.retransformClasses(targetClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Wait for the retransformation to complete
        while (!retransformed.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
