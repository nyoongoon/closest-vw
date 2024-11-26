package com.example.closestv2.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static String readFileAsString(String fileName) throws IOException {
        // ClassLoader를 사용해 클래스패스 기준으로 파일 위치를 가져옴
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        Path path = Paths.get(classLoader.getResource(fileName).getPath());
        return Files.readString(path);
    }
}
