package ua.kpi.books.service;

import ua.kpi.books.model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BookService {

    public List<Book> byAuthor(Book[] src, String author) {
        List<Book> out = new ArrayList<>();
        for (Book b : src) {
            if (b.getAuthor().equalsIgnoreCase(author)) {
                out.add(b);
            }
        }
        return out;
    }

    public List<Book> byPublisher(Book[] src, String publisher) {
        List<Book> out = new ArrayList<>();
        for (Book b : src) {
            if (b.getPublisher().equalsIgnoreCase(publisher)) {
                out.add(b);
            }
        }
        return out;
    }

    public List<Book> afterYear(Book[] src, int year) {
        List<Book> out = new ArrayList<>();
        for (Book b : src) {
            if (b.getYear() > year) {
                out.add(b);
            }
        }
        return out;
    }

    public Book[] sort(Book[] src, Comparator<Book> cmp) {
        Book[] copy = Arrays.copyOf(src, src.length);
        Arrays.sort(copy, cmp);
        return copy;
    }
}
