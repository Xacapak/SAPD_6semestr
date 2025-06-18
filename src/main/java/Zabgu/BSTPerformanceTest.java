package Zabgu;

import java.util.*;

/**
 * Класс для тестирования производительности операций поиска
 * в бинарном дереве поиска (Binary Search Tree).
 *
 * Содержит методы для измерения времени выполнения операций
 * поиска в зависимости от размера дерева.
 */
public class BSTPerformanceTest {

    /**
     * Тестирует и выводит время выполнения операций поиска
     * для бинарного дерева поиска различных размеров.
     *
     * Метод выполняет следующие действия:
     *
     *   Создает деревья заданных размеров
     *   Заполняет их случайными уникальными значениями
     *   Выполняет заданное количество операций поиска
     *   Измеряет и выводит время выполнения
     */
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в BST:");
        System.out.println("Размер | Время (мс)");
        System.out.println("-------------------");

        // Размеры деревьев для теста
        int[] sizes = {100, 1000, 2000, 3000, 4000, 5000};
        int searchIterations = 100000;

        for (int size : sizes) {
            /**
             * Генерирует набор случайных уникальных значений.
             *
             * @param size количество необходимых уникальных значений
             * @return множество случайных значений
             */
            Set<Integer> values = new HashSet<>();
            Random random = new Random();
            while (values.size() < size) {
                values.add(random.nextInt(size * 10));
            }

            /**
             * Строит бинарное дерево поиска на основе переданных значений.
             *
             * @param values значения для вставки в дерево
             * @return построенное бинарное дерево поиска
             */
            BinSTree<Integer> bst = new BinSTree<>();
            for (int value : values) {
                bst.insert(value);
            }

            // Тест поиска
            long startTime = System.nanoTime();
            for (int i = 0; i < searchIterations; i++) {
                bst.contains(random.nextInt(size * 10));
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;

            System.out.printf("%6d | %8d%n", size, durationMs);
        }
    }
}