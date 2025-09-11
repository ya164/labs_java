/*
Лабораторна 1, Варіант 3
Група ІА-32, Діденко Я.О
*/

import java.util.ArrayList;
import java.util.List;

public class LatinWordsFilter {

    // метод перевірки, чи слово складається лише з латинських букв
    private static boolean isLatinWord(String word) {
        return word.matches("[a-zA-Z]+");
    }

    // метод перевірки, чи у слові рівна кількість голосних та приголосних
    private static boolean hasEqualVowelsAndConsonants(String word) {
        String vowels = "aeiouAEIOU";
        int vowelCount = 0;
        int consonantCount = 0;

        for (char c : word.toCharArray()) {
            if (vowels.indexOf(c) >= 0) {
                vowelCount++;
            } else {
                consonantCount++;
            }
        }

        return vowelCount == consonantCount;
    }

    public static String[] filterWords(String input) {
        String[] words = input.split("\\s+");
        List<String> result = new ArrayList<>();

        for (String word : words) {
            if (isLatinWord(word) && hasEqualVowelsAndConsonants(word)) {
                result.add(word);
            }
        }

        return result.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String input = "chinazes java util aadd aаbb";
        String[] output = filterWords(input);

        System.out.println("Результат:");
        for (String word : output) {
            System.out.println(word);
        }
    }
}
