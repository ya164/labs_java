package ua.kpi.books.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagCounter {

    public static String download(String urlStr) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(urlStr);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    public static Map<String, Integer> countTags(String html) {
        Map<String, Integer> freq = new HashMap<>();

        Pattern p = Pattern.compile("<([a-zA-Z][a-zA-Z0-9-]*)");
        Matcher m = p.matcher(html);

        while (m.find()) {
            // m.group(1) - це те, що в дужках (...)
            String tag = m.group(1).toLowerCase(Locale.ROOT);
            freq.put(tag, freq.getOrDefault(tag, 0) + 1);
        }
        return freq;
    }

    public static void printReports(Map<String, Integer> freq) {
        if (freq.isEmpty()) {
            System.out.println("Теги не знайдено.");
            return;
        }

        System.out.println("=== (a) Лексикографічно за тегом ===");
        freq.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e ->
                        System.out.printf("%s : %d%n", e.getKey(), e.getValue()));

        System.out.println("\n=== (b) За зростанням частоти ===");
        freq.entrySet().stream()
                .sorted(
                        Comparator
                                .comparingInt(Map.Entry<String, Integer>::getValue)
                                .thenComparing(Map.Entry::getKey)
                )
                .forEach(e ->
                        System.out.printf("%s : %d%n", e.getKey(), e.getValue()));
    }
}