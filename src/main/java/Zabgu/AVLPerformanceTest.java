package Zabgu;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AVLPerformanceTest {
    /**
     * Тестирует и выводит время выполнения операций поиска
     * для AVL-дерева различных размеров.
     */
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в AVL-дереве:");
        System.out.println("Размер | Время (мс)");
        System.out.println("-------------------");

        int[] sizes = {100, 1000, 2000, 3000, 4000, 5000, 10000, 50000, 100000};
        int searchIterations = 100000;

        for (int size : sizes) {
            // Генерация уникальных значений
            Set<Integer> values = new HashSet<>();
            Random random = new Random();
            while (values.size() < size) {
                values.add(random.nextInt(size * 10));
            }

            // Построение AVL-дерева
            AVLTree<Integer> avl = new AVLTree<>();
            for (int value : values) {
                avl.insert(value);
            }

            // Тест поиска
            long startTime = System.nanoTime();
            for (int i = 0; i < searchIterations; i++) {
                avl.contains(random.nextInt(size * 10));
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;

            System.out.printf("%6d | %8d%n", size, durationMs);
        }
    }
}
