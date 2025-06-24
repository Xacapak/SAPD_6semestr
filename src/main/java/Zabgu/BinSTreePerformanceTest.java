package Zabgu;

import java.util.Random;

public class BinSTreePerformanceTest {
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в 'Бинарном дереве поиска':");
        System.out.println("Размер  | Время (мс)");
        System.out.println("--------------------");

        int[] sizes = {100, 1_000, 10_000, 100_000, 1_000_000};
        int searchIterations = 100_000;
        Random rand = new Random();

        for (int size : sizes) {
            BinSTree<Integer> tree = new BinSTree<>();
            for (int i = 0; i < size; i++) {
                int value = rand.nextInt(size * 10);
                while (tree.find(value) != null) {
                    value = rand.nextInt(size * 10);
                }
                tree.insert(value);
            }

            int[] searchValues = new int[searchIterations];
            for (int i = 0; i < searchIterations; i++) {
                if (rand.nextBoolean()) {
                    searchValues[i] = rand.nextInt(size * 10);
                } else {
                    searchValues[i] = size * 10 + rand.nextInt(size * 10);
                }
            }

            long startTime = System.nanoTime();
            for (int value : searchValues) {
                tree.find(value);
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;

            System.out.printf("%6d  | %8d%n", size, durationMs);
        }
    }
}