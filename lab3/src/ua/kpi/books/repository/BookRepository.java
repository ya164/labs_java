package ua.kpi.books.repository;

import ua.kpi.books.model.Book;
import ua.kpi.books.util.DataFactory;

import java.util.Arrays;

public class BookRepository {
    private Book[] data;

    public BookRepository() {
        this.data = DataFactory.preparedArray(13);
    }

    public Book[] findAll() {
        return Arrays.copyOf(data, data.length);
    }

    public void replaceAll(Book[] newData) {
        this.data = Arrays.copyOf(newData, newData.length);
    }

    public int size() {
        return data.length;
    }
}
