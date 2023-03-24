package com.github.leanfe;

import com.github.leanfe.arguments.OptionHelper;

public class Application {

    public static void main(String... args) {
        System.out.printf("""
                \033[33m[PLATFORM]\033[0m: %s
                
                TRY STARTING... \n""", Constants.OS);

        //        DLLLoader.loadDLL();

        OptionHelper.workWithArguments(args);

        net.minecraft.client.main.Main.main(args);
    }

}
