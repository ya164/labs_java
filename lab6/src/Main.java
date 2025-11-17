import java.util.HashMap;
import java.util.Scanner;

class Translator {

    private HashMap<String, String> dictionary;

    public Translator() {
        dictionary = new HashMap<>();
    }

    public void addWord(String englishWord, String ukrainianWord) {
        dictionary.put(englishWord.toLowerCase(), ukrainianWord);
    }

    public String translate(String phrase) {
        StringBuilder translatedPhrase = new StringBuilder();
        String[] words = phrase.split("\\s+");

        for (String word : words) {
            String translatedWord = dictionary.get(word.toLowerCase());
            if (translatedWord != null) {
                translatedPhrase.append(translatedWord).append(" ");
            } else {
                translatedPhrase.append(word).append(" ");
            }
        }
        return translatedPhrase.toString().trim();
    }
}

public class Main {
    public static void main(String[] args) {
        Translator translator = new Translator();
        Scanner scanner = new Scanner(System.in);

        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("java", "джава");
        translator.addWord("is", "це");
        translator.addWord("fun", "весело");

        System.out.println("Наповнення словника. Введіть 'exit' для завершення.");
        while (true) {
            System.out.print("Введіть англійське слово: ");
            String englishWord = scanner.nextLine();
            if (englishWord.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.print("Введіть український переклад: ");
            String ukrainianWord = scanner.nextLine();
            translator.addWord(englishWord, ukrainianWord);
        }

        System.out.println("\nВведіть фразу англійською мовою для перекладу:");
        String englishPhrase = scanner.nextLine();

        String translatedPhrase = translator.translate(englishPhrase);
        System.out.println("Переклад українською мовою:");
        System.out.println(translatedPhrase);

        scanner.close();
    }
}