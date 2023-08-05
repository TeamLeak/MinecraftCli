package com.github.leanfe;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.util.Optional;

public class AgentLoader {

    public static void run(String[] args) {
        String agentFilePath = "Z:\\Workspace\\MinecraftCli\\build\\libs\\Agent.jar";
        String applicationName = "MinecraftCli";

        //iterate all jvms and get the first one that matches our application name
        Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
                .stream()
                .filter(jvm -> jvm.displayName().contains(applicationName))
                .findFirst().get().id());

        if(!jvmProcessOpt.isPresent()) {
            return;
        }

        System.out.printf("JVM PID: %s\n", jvmProcessOpt.get());

        File agentFile = new File(agentFilePath);

        if (agentFile.exists())
            System.out.printf("AGENT PATH: %s \n", agentFile.getAbsolutePath());
        try {
            String jvmPid = jvmProcessOpt.get();
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);

            // TODO:

//            jvm.loadAgent(agentFile.getAbsolutePath());
//            jvm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}