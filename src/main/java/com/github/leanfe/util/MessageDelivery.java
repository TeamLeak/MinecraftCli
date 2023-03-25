package com.github.leanfe.util;

import javax.swing.*;

public class MessageDelivery {

    public static void sendWarning(String body) {
        JOptionPane.showMessageDialog(new JFrame(), body, "WARNING!",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void sendError(String body) {
        JOptionPane.showMessageDialog(new JFrame(), body, "ERROR!",
                JOptionPane.ERROR_MESSAGE);

    }

    public static void sendInformation(String body) {
        JOptionPane.showMessageDialog(new JFrame(), body, "INFO!",
                JOptionPane.INFORMATION_MESSAGE);

    }

}
