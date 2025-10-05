package ua.kpi.books;

import ua.kpi.books.controller.AppController;
import ua.kpi.books.repository.BookRepository;
import ua.kpi.books.service.BookService;
import ua.kpi.books.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        BookRepository repo = new BookRepository();
        BookService service = new BookService();
        ConsoleView view = new ConsoleView();
        AppController controller = new AppController(repo, service, view);
        controller.start();
    }
}
