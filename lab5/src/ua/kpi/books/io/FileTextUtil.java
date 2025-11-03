package ua.kpi.books.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileTextUtil {

    public static String findLineWithMaxWords(String path) throws IOException {
        String resultLine = null;
        int maxWords = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                int currentWords = 0;

                if (!trimmedLine.isEmpty()) {
                    currentWords = trimmedLine.split("\\s+").length;
                }

                if (currentWords > maxWords) {
                    maxWords = currentWords;
                    resultLine = line;
                }
            }
        }
        return resultLine;
    }
}