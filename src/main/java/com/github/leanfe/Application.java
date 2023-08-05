package com.github.leanfe;

import com.github.leanfe.options.OptionHelper;
import com.github.leanfe.platform.OutputHandler;
import cpw.mods.modlauncher.Launcher;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Optional;

public class Application {

    private static Optional<String> getImplementationVersion() {
        String pkgVersion = Application.class.getPackage().getImplementationVersion();
        return Optional.ofNullable(pkgVersion);
    }

    static {
        System.setProperty("jdk.internal.module.exports.java.base", "ALL-UNNAMED");
    }

    public static void main(String... args) {


        System.out.printf("\033[33m[PLATFORM]\033[0m: %s\n" +
                          "\nTRY STARTING... \n %s \n", Constants.OS, getImplementationVersion());

        OptionHelper.workWithArguments(args);

        try {
            OutputHandler.translateOutput();
        } catch (IOException e) {
            OutputHandler.showError2();
        }

        try {
            Class<?> agentClass = Class.forName("ClassReloaderAgent");
            java.lang.reflect.Method premainMethod = agentClass.getMethod("premain", String.class, Instrumentation.class);

            Instrumentation inst = (Instrumentation) premainMethod.invoke(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Launcher.main(args);

    }

}
