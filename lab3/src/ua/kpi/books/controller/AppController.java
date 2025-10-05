package ua.kpi.books.controller;

import ua.kpi.books.model.Book;
import ua.kpi.books.repository.BookRepository;
import ua.kpi.books.service.BookService;
import ua.kpi.books.util.ByPublisherComparator;
import ua.kpi.books.util.DataFactory;
import ua.kpi.books.view.ConsoleView;

import java.util.List;
import java.util.Scanner;

public class AppController {
    private final BookRepository repo;
    private final BookService service;
    private final ConsoleView view;

    public AppController(BookRepository repo, BookService service, ConsoleView view) {
        this.repo = repo;
        this.service = service;
        this.view = view;
    }

    public void start() {
        view.printHeader("Вихідний масив книг");
        view.printArray(repo.findAll());

        Scanner sc = new Scanner(System.in);
        while (true) {
            view.printMenu();
            String line = sc.nextLine().trim();

            switch (line) {
                case "1" -> {
                    view.printHeader("Всі книги (поточний масив)");
                    view.printArray(repo.findAll());
                }
                case "2" -> {
                    String author = DataFactory.randomAuthor(); // ВИМОГА 2: параметр з prepared даних, випадково
                    view.printChosenParam("Автор", author);
                    List<Book> res = service.byAuthor(repo.findAll(), author);
                    view.printList(res);
                }
                case "3" -> {
                    String publisher = DataFactory.randomPublisher();
                    view.printChosenParam("Видавництво", publisher);
                    List<Book> res = service.byPublisher(repo.findAll(), publisher);
                    view.printList(res);
                }
                case "4" -> {
                    int year = DataFactory.randomYear();
                    view.printChosenParam("Рік (пізніше за)", year);
                    List<Book> res = service.afterYear(repo.findAll(), year);
                    view.printList(res);
                }
                case "5" -> {
                    view.printHeader("Сортування за видавництвом (Comparator)");
                    Book[] sorted = service.sort(repo.findAll(), new ByPublisherComparator());
                    repo.replaceAll(sorted); // оновимо репозиторій відсортованою версією
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
}
