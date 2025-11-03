package ua.kpi.books.io;

import ua.kpi.books.model.Book;

import java.io.*;

public class BookFileStorage {

    public void save(Book[] data, String path) throws IOException {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(data);
        }
    }

    public Book[] load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            return (Book[]) obj;
        }
    }
}