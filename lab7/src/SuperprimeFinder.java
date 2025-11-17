import java.util.stream.IntStream;

public class SuperprimeFinder {

    public static void main(String[] args) {
        int limit = 1000;
        long superprimeCount = countSuperprimes(limit);

        System.out.println("Кількість надпростих чисел в діапазоні до " + limit + ": " + superprimeCount);
    }

    public static long countSuperprimes(int n) {
        return IntStream.rangeClosed(1, n)
                .filter(num -> isPrime(num) && isPrime(reverse(num)))
                .count();
    }

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        return IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(i -> number % i == 0);
    }

    private static int reverse(int number) {
        int reversed = 0;
        while (number != 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }
        return reversed;
    }
}