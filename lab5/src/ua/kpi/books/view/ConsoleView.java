package ua.kpi.books.view;

import ua.kpi.books.model.Book;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    public void printHeader(String text) {
        System.out.println("\n=== " + text + " ===");
    }

    public void printArray(Book[] arr) {
        if (arr.length == 0) {
            System.out.println("(порожньо)");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%2d) %s%n", i + 1, arr[i]);
        }
    }

    public void printList(List<Book> list) {
        if (list.isEmpty()) {
            System.out.println("Нічого не знайдено.");
            return;
        }
        int i = 1;
        for (Book b : list) {
            System.out.printf("%2d) %s%n", i++, b);
        }
    }

    public void printMenu() {
        System.out.println("""
                
                ---------------- МЕНЮ ----------------
                (Нові Завдання I/O)
                1. [Завдання 1] Знайти рядок з макс. слів у файлі
                2. [Завдання 2] Зберегти масив у файл (серіалізація)
                3. [Завдання 2] Завантажити масив з файлу (серіалізація)
                4. [Завдання 3] Зашифрувати текстовий файл
                5. [Завдання 3] Розшифрувати текстовий файл
                6. [Завдання 4] Порахувати HTML теги за URL
                
                (Функції базової програми OOP)
                7. Показати всі книги (поточний масив)
                8. Знайти книги за автором
                9. Знайти книги за видавництвом
                10. Знайти книги, видані пізніше за вказаний рік
                11. Відсортувати книги за видавництвами (Comparator)
                
                0. Вихід
                ---------------------------------------
                Виберіть пункт: """);
    }

    public void printChosenParam(String label, String value) {
        System.out.printf("Обраний параметр (%s): %s%n", label, value);
    }

    public void printChosenParam(String label, int value) {
        System.out.printf("Обраний параметр (%s): %d%n", label, value);
    }

    public String askString(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public int askInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Помилка: потрібно ввести ціле число. Спробуйте ще раз.");
            }
        }
    }
}