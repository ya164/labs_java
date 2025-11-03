package ua.kpi.books.util;

import ua.kpi.books.model.Book;
import java.util.Comparator;

public class ByPublisherComparator implements Comparator<Book> {
    @Override
    public int compare(Book a, Book b) {
        int byPub = a.getPublisher().compareToIgnoreCase(b.getPublisher());
        if (byPub != 0) return byPub;
        int byYearDesc = Integer.compare(b.getYear(), a.getYear());
        if (byYearDesc != 0) return byYearDesc;
        return a.getTitle().compareToIgnoreCase(b.getTitle());
    }
}