package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentWithFilter((i) -> (i < 0x80));
    }

    public String getContent() throws IOException {
        return getContentWithFilter((i) -> (true));
    }

    private String getContentWithFilter(Predicate<Integer> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (Reader i = new BufferedReader(new FileReader(file))) {
            int ch;
            while ((ch = i.read()) != -1) {
                if (filter.test(ch)) {
                    output.append((char) ch);
                }
            }
        }
        return output.toString();
    }

}