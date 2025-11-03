package ua.kpi.books.controller;

import ua.kpi.books.crypto.StreamCipher;
import ua.kpi.books.io.BookFileStorage;
import ua.kpi.books.io.FileTextUtil;
import ua.kpi.books.model.Book;
import ua.kpi.books.repository.BookRepository;
import ua.kpi.books.service.BookService;
import ua.kpi.books.util.ByPublisherComparator;
import ua.kpi.books.view.ConsoleView;
import ua.kpi.books.web.TagCounter;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AppController {
    private final BookRepository repo;
    private final BookService service;
    private final ConsoleView view;
    private final BookFileStorage storage;

    public AppController(BookRepository repo, BookService service, ConsoleView view) {
        this.repo = repo;
        this.service = service;
        this.view = view;
        this.storage = new BookFileStorage();
    }

    public void start() {
        view.printHeader("Початковий масив книг");
        view.printArray(repo.findAll());

        Scanner sc = new Scanner(System.in);

        while (true) {
            view.printMenu();
            String line = sc.nextLine().trim();

            switch (line) {
                case "1" -> handleMaxWordsLine(sc);

                case "2" -> {
                    String path = view.askString(sc,
                            "Введіть шлях/ім'я файлу для ЗБЕРЕЖЕННЯ (наприклад data.ser): ");
                    try {
                        storage.save(repo.findAll(), path);
                        System.out.println("Масив успішно збережено у файл: " + path);
                    } catch (IOException e) {
                        System.out.println("Помилка збереження у файл: " + e.getMessage());
                    }
                }
                case "3" -> {
                    String path = view.askString(sc,
                            "Введіть шлях/ім'я файлу для ЗАВАНТАЖЕННЯ: ");
                    try {
                        Book[] loaded = storage.load(path);
                        repo.replaceAll(loaded);
                        System.out.println("Масив успішно завантажено з файлу: " + path);
                        view.printArray(repo.findAll());
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Помилка читання файлу: " + e.getMessage());
                    } catch (ClassCastException e) {
                        System.out.println("У файлі не масив Book[].");
                    }
                }

                case "4" -> handleEncryptFile(sc);
                case "5" -> handleDecryptFile(sc);
                case "6" -> handleTagCounter(sc);

                case "7" -> {
                    view.printHeader("Всі книги (поточний масив)");
                    view.printArray(repo.findAll());
                }
                case "8" -> {
                    String author = view.askString(sc, "Введіть автора: ");
                    view.printChosenParam("Автор", author);
                    List<Book> res = service.byAuthor(repo.findAll(), author);
                    view.printList(res);
                }
                case "9" -> {
                    String publisher = view.askString(sc, "Введіть видавництво: ");
                    view.printChosenParam("Видавництво", publisher);
                    List<Book> res = service.byPublisher(repo.findAll(), publisher);
                    view.printList(res);
                }
                case "10" -> {
                    int year = view.askInt(sc, "Введіть рік (показати книги пізніше цього року): ");
                    view.printChosenParam("Рік (пізніше за)", year);
                    List<Book> res = service.afterYear(repo.findAll(), year);
                    view.printList(res);
                }
                case "11" -> {
                    view.printHeader("Сортування за видавництвом (Comparator)");
                    Book[] sorted = service.sort(repo.findAll(), new ByPublisherComparator());
                    repo.replaceAll(sorted);
                    view.printArray(sorted);
                }

                case "0" -> {
                    System.out.println("Вихід. Гарного дня!");
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private void handleMaxWordsLine(Scanner sc) {
        view.printHeader("[I/O 1] Знайти рядок з макс. слів у файлі");
        String path = view.askString(sc, "Введіть шлях до текстового файлу: ");
        try {
            String line = FileTextUtil.findLineWithMaxWords(path);
            if (line == null) {
                System.out.println("Файл порожній або не вдалося прочитати.");
            } else {
                System.out.println("Знайдений рядок (" + line.trim().split("\\s+").length + " слів):");
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private void handleEncryptFile(Scanner sc) {
        view.printHeader("[I/O 3] Зашифрувати текстовий файл");
        String inPath = view.askString(sc, "Введіть шлях до ВХІДНОГО файлу: ");
        String outPath = view.askString(sc, "Введіть шлях до ВИХІДНОГО файлу: ");
        String keyStr = view.askString(sc, "Введіть ключ (1 символ): ");

        if (keyStr.length() != 1) {
            System.out.println("Помилка: Ключ має бути одним символом.");
            return;
        }
        char key = keyStr.charAt(0);

        try (Reader reader = new BufferedReader(new FileReader(inPath));
             Writer writer = new StreamCipher.EncryptingWriter(
                     new BufferedWriter(new FileWriter(outPath)), key)) {

            reader.transferTo(writer);

            System.out.println("Файл успішно зашифровано: " + outPath);

        } catch (IOException e) {
            System.out.println("Помилка шифрування файлу: " + e.getMessage());
        }
    }

    private void handleDecryptFile(Scanner sc) {
        view.printHeader("[I/O 3] Розшифрувати текстовий файл");
        String inPath = view.askString(sc, "Введіть шлях до ЗАШИФРОВАНОГО файлу: ");
        String outPath = view.askString(sc, "Введіть шлях до ВИХІДНОГО (розшифр.) файлу: ");
        String keyStr = view.askString(sc, "Введіть той самий ключ (1 символ): ");

        if (keyStr.length() != 1) {
            System.out.println("Помилка: Ключ має бути одним символом.");
            return;
        }
        char key = keyStr.charAt(0);

        try (Reader reader = new StreamCipher.DecryptingReader(
                new BufferedReader(new FileReader(inPath)), key);
             Writer writer = new BufferedWriter(new FileWriter(outPath))) {

            reader.transferTo(writer);

            System.out.println("Файл успішно розшифровано: " + outPath);

        } catch (IOException e) {
            System.out.println("Помилка розшифрування файлу: " + e.getMessage());
        }
    }

    private void handleTagCounter(Scanner sc) {
        view.printHeader("[I/O 4] Порахувати HTML теги за URL");
        String url = view.askString(sc, "Введіть URL (напр. https://example.org): ");
        try {
            view.printHeader("Завантаження... " + url);

            // Ми знову використовуємо підхід у два кроки
            String html = TagCounter.download(url);

            view.printHeader("Підрахунок тегів...");
            Map<String, Integer> freq = TagCounter.countTags(html);

            TagCounter.printReports(freq);

        } catch (IOException e) {
            System.out.println("Помилка завантаження або обробки URL: " + e.getMessage());
        }
    }
}