package com.github.lkapitman.utils;

public class OsUtil {
    public static EnumOS getOs(){
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win") ? EnumOS.WINDOWS
                :(os.contains("mac") ? EnumOS.MAC
                :(os.contains("linux") ? EnumOS.LINUX
                :(os.contains("unix") ? EnumOS.MAC : EnumOS.UNKNOWN)));
    }

    public static enum EnumOS {
        LINUX("LINUX", 0),
        WINDOWS("WINDOWS", 1),
        MAC("MAC", 2),
        UNKNOWN("UNKNOWN", 3);

        private EnumOS(String name, int id) {}
    }
}
