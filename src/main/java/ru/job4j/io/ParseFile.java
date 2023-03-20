package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            while ((i.read(dataBuffer, 0, 1024)) > 0) {
                for (int ch : dataBuffer) {
                    if (filter.test(ch)) {
                        output.append((char) ch);
                    }
                }
            }
        }
        return output.toString();
    }

}