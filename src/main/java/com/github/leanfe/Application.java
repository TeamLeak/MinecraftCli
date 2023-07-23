package com.github.leanfe;

import com.github.leanfe.options.OptionHelper;
import com.github.leanfe.platform.OutputHandler;

import java.io.IOException;

public class Application {

    static {
        System.setProperty("jdk.internal.module.exports.java.base", "ALL-UNNAMED");
    }

    public static void main(String... args) {

        System.out.printf("\033[33m[PLATFORM]\033[0m: %s\n" +
                          "\nTRY STARTING... \n", Constants.OS);

        OptionHelper.workWithArguments(args);

        try {
            OutputHandler.translateOutput();
        } catch (IOException e) {
            OutputHandler.showError2();
        }

        //net.minecraft.client.main.Main.main(args);

        cpw.mods.modlauncher.Launcher.main(args);
    }

}
