package de.tammo.cloud.hub;

public class OsChecker {
    /**
     * Checks if the host-system is running Windows
     *
     * @return is Running windows
     */
    public static boolean isOnWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}
