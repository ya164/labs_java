package ua.kpi.books.view;

import ua.kpi.books.model.Book;

import java.util.List;

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
                1. Показати всі книги (вихідний масив)
                2. Отримати список книг зазначеного автора (параметр обирається випадково)
                3. Отримати список книг зазначеного видавництва (випадково)
                4. Отримати список книг, виданих пізніше випадкового року
                5. Відсортувати книги за видавництвами (Comparator)
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
}
