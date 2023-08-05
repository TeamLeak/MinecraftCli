package com.github.leanfe.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class ClassReloaderAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        ClassFileTransformer transformer = (loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            if (className.equals("net.minecraftforge.fml.loading.FMLLoader")) {
                return EventBusTransformer.transform(classfileBuffer);
            }
            return classfileBuffer;
        };

        inst.addTransformer(transformer, true);
        retransformClasses(inst);
    }

    private static void retransformClasses(Instrumentation inst) {
        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> clazz : classes) {
            if (clazz.getName().equals("net.minecraftforge.fml.loading.FMLLoader")) {
                try {
                    inst.retransformClasses(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
