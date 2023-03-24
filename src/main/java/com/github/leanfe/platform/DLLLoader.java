package com.github.leanfe.platform;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;
import java.util.Objects;

public class DLLLoader {

    public static void loadDLL() {
        try {
            // Get the path to the "natives" folder next to the JAR file
            String jarPath = DLLLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            File jarFile = new File(jarPath);
            File nativesFolder = new File(jarFile.getParentFile(), "natives");

            // Get all DLL files in the "natives" folder
            File[] dllFiles = nativesFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".dll"));

            // Create a new URLClassLoader using the parent class loader of the application's class loader
            ClassLoader appClassLoader = DLLLoader.class.getClassLoader();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{}, appClassLoader.getParent());

            // Add each DLL file to the CLASSPATH using reflection to call addURL
            for (File dllFile : Objects.requireNonNull(dllFiles)) {
                URL url = dllFile.toURI().toURL();
                Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURLMethod.setAccessible(true);
                addURLMethod.invoke(urlClassLoader, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
