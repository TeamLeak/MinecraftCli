package com.github.leanfe;

import com.github.leanfe.options.OptionHelper;
import com.github.leanfe.platform.OutputHandler;
import cpw.mods.modlauncher.Launcher;

import java.io.IOException;
import java.util.Optional;

public class Application {

    // C:\Users\lkapi\.jdks\liberica-1.8.0_382\bin\java.exe -jar .\MinecraftCli-1.0-SNAPSHOT-all.jar
    // --version 1.16.5 --accessToken null --username Leanfe --fml.forgeVersion 36.2.39
    // --fml.mcpVersion 20210115.111550  --launchTarget fmlclient --gameDir .
    // --fml.mcVersion 1.16.5 --fml.forgeGroup net.minecraftforge --versionType modified

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

        AgentLoader.run();

        Launcher.main(args);

    }

}
