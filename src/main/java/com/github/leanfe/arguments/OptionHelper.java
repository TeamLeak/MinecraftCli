package com.github.leanfe.arguments;

public class OptionHelper {

    public static void workWithArguments(String... args) {
        if (args.length < 2) printError0();

        if (args.length == 2) printHelpInformation();

        if (args.length > 2) parsingArguments(args);
    }

    private static void parsingArguments(String... args) {

    }

    private static void printError0() {
        System.err.println("\033[31;1mYou need to at least specify VERSION and accessToken!!!\033[0m");

        System.exit(1);
    }

    private static void printHelpInformation() {
        String informationBody = "The main class of your client. " +
                "Approximate path: net.minecraft.client.main.Main.class." +
                " Also, look on the Internet for information about the parameters.";

        System.out.printf(
                "Yes, of course you can do that.. " +
                        "BUT, let me point out to you that it is possible to use such keys as %s, %s, %s, %s! \nMore info: %s",
                "--gameDir", "--server & --port", "--username", "--uuid", informationBody);
    }
}
