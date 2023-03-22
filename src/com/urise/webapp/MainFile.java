package com.urise.webapp;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static final String PATH = "C:\\Users\\KOSTUA\\Desktop\\BaseJAva";

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\KOSTUA\\Desktop\\BaseJAva\\basejava\\.gitignore");
        File file2 = new File(".\\.gitignore");
        System.out.println(file.getCanonicalPath());
        System.out.println(file2.getCanonicalPath());

        StringBuilder tabSpace = new StringBuilder();
        getDir(PATH, tabSpace);

    }

    public static void getDir(String PATH, StringBuilder tabSpace) {
        File dir = new File(PATH);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory() && !file.isHidden()) {
                System.out.println(tabSpace + "- " + file.getName());
                tabSpace.append("   ");
                getDir(PATH + "\\" + file.getName(), tabSpace);
                tabSpace.delete(0, 3);
            } else {
                System.out.println(tabSpace + "- " + file.getName());
            }
        }
    }
}
