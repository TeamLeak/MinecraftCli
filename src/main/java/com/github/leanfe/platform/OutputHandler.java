package com.github.leanfe.platform;

import com.github.leanfe.util.MessageDelivery;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class OutputHandler {

    public static void translateOutput() throws IOException {
        System.out.println("\033[33mRedirecting OUTPUT to launcher-log.log\033[0m");

        var file = new File("launcher-log.log");

        if (!file.exists() || file.isDirectory())
            file.createNewFile();

        System.setOut(new PrintStream("launcher-log.log"));

    }

    public static void showError2() {
        var message = "Some errors occurred while trying to translate std.out to a file!";

        MessageDelivery.sendWarning(message);
    }


}
