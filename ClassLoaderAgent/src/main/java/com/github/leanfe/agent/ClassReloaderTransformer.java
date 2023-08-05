package com.github.leanfe.agent;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class ClassReloaderTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (className.equals("net.minecraftforge.fml.loading.FMLLoader")) {
            return EventBusTransformer.transform(classfileBuffer);
        }
        return classfileBuffer;
    }
}
