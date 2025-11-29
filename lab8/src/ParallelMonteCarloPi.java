import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelMonteCarloPi {

    private static final long ITERATIONS = 1_000_000_000L;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of threads: ");
        int threads = scanner.nextInt();

        if (threads <= 0) {
            System.out.println("Threads must be > 0");
            return;
        }

        long start = System.nanoTime();

        Thread[] workers = new Thread[threads];
        long[] insideCounts = new long[threads];

        long base = ITERATIONS / threads;
        long extra = ITERATIONS % threads;

        for (int i = 0; i < threads; i++) {
            final int index = i;
            final long iter = base + (i < extra ? 1 : 0);

            workers[i] = new Thread(() -> {
                long inside = 0;
                ThreadLocalRandom rnd = ThreadLocalRandom.current();

                for (long j = 0; j < iter; j++) {
                    double x = rnd.nextDouble();
                    double y = rnd.nextDouble();
                    if (x*x + y*y <= 1.0) inside++;
                }

                insideCounts[index] = inside;
            });

            workers[i].start();
        }

        for (int i = 0; i < threads; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                return;
            }
        }

        long totalInside = 0;
        for (long c : insideCounts) totalInside += c;

        double pi = 4.0 * ((double) totalInside / ITERATIONS);

        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;

        System.out.printf("PI is %.5f%n", pi);
        System.out.printf("THREADS %d%n", threads);
        System.out.printf("ITERATIONS %,d%n", ITERATIONS);
        System.out.printf("TIME %.2fms%n", ms);
    }
}