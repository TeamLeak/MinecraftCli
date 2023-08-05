package com.github.leanfe;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class AgentLoader {

    public static void run() {
        String agentFilePath = "Z:\\Workspace\\MinecraftCli\\build\\libs\\Agent.jar";
        String applicationName = "MinecraftCli";

        AtomicReference<VirtualMachine> mainJVM = new AtomicReference<>();

        //iterate all jvms and get the first one that matches our application name

        VirtualMachine.list().forEach(jvm -> {
            if (jvm.displayName().contains(applicationName)) {
                try {
                    mainJVM.set(VirtualMachine.attach(jvm));
                } catch (AttachNotSupportedException | IOException e) {
                    System.err.printf("Message: %s\nCause: %s\n", e.getMessage(), e.getCause());
                    e.printStackTrace();
                }
            }
        });

        System.err.printf("JVM PID: %s\n", mainJVM.get().id());

        File agentFile = new File(agentFilePath);

        if (agentFile.exists())
            System.err.printf("AGENT PATH: %s \n", agentFile.getAbsolutePath());

        try {

            mainJVM.get().loadAgent(agentFile.getAbsolutePath());
        } catch (AgentLoadException | AgentInitializationException e) {
            System.err.printf("AGENT ERROR MESSAGE: %s\nCause: %s\n", e.getMessage(), e.getCause());
        } catch (IOException e) {
            System.err.printf("IO ERROR WHEN LOAD: %s\nCause: %s\n", e.getMessage(), e.getCause());
        }
    }

}