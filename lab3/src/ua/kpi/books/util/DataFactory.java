package ua.kpi.books.util;

import ua.kpi.books.model.Book;

import java.util.Random;

public class DataFactory {
    private static final String[] TITLES = {
            "The Name of the Rose", "All Quiet on the Western Front", "1984",
            "Solaris", "Brave New World", "The Trial", "Fahrenheit 451",
            "The Master and Margarita", "Animal Farm", "Do Androids Dream of Electric Sheep?",
            "The Hobbit", "Crime and Punishment", "The Catcher in the Rye"
    };

    private static final String[] AUTHORS = {
            "Umberto Eco", "Erich Maria Remarque", "George Orwell",
            "Stanislaw Lem", "Aldous Huxley", "Franz Kafka",
            "Ray Bradbury", "Mikhail Bulgakov", "Philip K. Dick",
            "J.R.R. Tolkien", "Fyodor Dostoevsky", "J.D. Salinger"
    };

    private static final String[] PUBLISHERS = {
            "Penguin", "HarperCollins", "KyivBooks", "Vintage", "Orbit"
    };

    private static final int[] YEARS = {
            1950, 1954, 1967, 1973, 1984, 1990, 2001, 2012, 2019, 2024
    };

    private static final int[] PAGES = {160, 220, 288, 320, 420, 512, 640, 720, 864};
    private static final double[] PRICES = {8.99, 11.49, 12.99, 14.50, 19.99, 24.90, 29.99, 34.90, 39.99};

    private static final Random RND = new Random(42); // фіксований seed для відтворюваності

    public static String randomAuthor() { return AUTHORS[RND.nextInt(AUTHORS.length)]; }
    public static String randomPublisher() { return PUBLISHERS[RND.nextInt(PUBLISHERS.length)]; }
    public static int randomYear() { return YEARS[RND.nextInt(YEARS.length)]; }

    public static Book[] preparedArray(int sizeMin10) {
        int n = Math.max(10, sizeMin10);
        Book[] arr = new Book[n];

        for (int i = 0; i < n; i++) {
            String title = TITLES[RND.nextInt(TITLES.length)];
            String author = AUTHORS[RND.nextInt(AUTHORS.length)];
            String publisher = PUBLISHERS[RND.nextInt(PUBLISHERS.length)];
            int year = YEARS[RND.nextInt(YEARS.length)];
            int pages = PAGES[RND.nextInt(PAGES.length)];
            double price = PRICES[RND.nextInt(PRICES.length)];
            arr[i] = new Book(title, author, publisher, year, pages, price);
        }
        return arr;
    }
}
