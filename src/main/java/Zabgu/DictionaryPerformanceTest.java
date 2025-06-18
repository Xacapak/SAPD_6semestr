package Zabgu;

import java.util.Random;

public class DictionaryPerformanceTest {
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в словаре:");
        System.out.println("Размер | Время (мс)");
        System.out.println("-------------------");

        int[] sizes = {100, 1000, 10000, 100000, 500000, 1000000};
        double loadFactor = 0.75; // Фиксированный коэффициент заполнения
        int searchIterations = 100000;

        for (int size : sizes) {
            Dictionary<Integer, String> dictionary = new Dictionary<>();

            // Заполняем словарь
            Random random = new Random();
            int elementsToAdd = (int)(size * loadFactor);
            for (int i = 0; i < elementsToAdd; i++) {
                int key = random.nextInt(size * 10);
                dictionary.put(key, "Value_" + key);
            }

            // Тестируем поиск
            long startTime = System.nanoTime();
            for (int i = 0; i < searchIterations; i++) {
                dictionary.get(random.nextInt(size * 10));
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;

            System.out.printf("%6d | %8d%n", size, durationMs);
        }
    }
}