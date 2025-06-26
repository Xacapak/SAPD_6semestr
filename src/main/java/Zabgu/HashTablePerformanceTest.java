package Zabgu;

import java.util.Random;
import java.util.function.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Класс для тестирования производительности хеш-таблицы.
 * Измеряет время поиска элементов и строит график зависимости времени от размера таблицы.
 */
public class HashTablePerformanceTest {
    /**
     * Тестирует производительность поиска в хеш-таблице для разных размеров.
     * Выводит результаты в консоль и строит график.
     */
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в хеш-таблице:");
        System.out.println("Размер  | Время (мс)");
        System.out.println("--------------------");

        int[] sizes = {100, 1_000, 10_000, 100_000, 1_000_000};
        int searchIterations = 100_000;
        int[] times = new int[sizes.length];
        Random rand = new Random();

        // Хеш-функция с улучшенным распределением
        Function<Integer, Integer> hashFunction = key -> {
            key = ((key >> 16) ^ key) * 0x45d9f3b;
            key = ((key >> 16) ^ key) * 0x45d9f3b;
            return (key >> 16) ^ key;
        };

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            // Создаем таблицу в 2 раза больше для уменьшения коллизий
            HashTable<Integer> table = new HashTable<>(size * 2, hashFunction);

            // Заполняем таблицу уникальными значениями
            for (int j = 0; j < size; j++) {
                int value = rand.nextInt(size * 10);
                while (table.find(value) != null) {
                    value = rand.nextInt(size * 10);
                }
                table.insert(new DataItem<>(value));
            }

            // Подготовка значений для поиска (50% существующих, 50% случайных)
            int[] searchValues = new int[searchIterations];
            for (int j = 0; j < searchIterations; j++) {
                searchValues[j] = rand.nextBoolean() ?
                        rand.nextInt(size * 10) :
                        size * 10 + rand.nextInt(size * 10);
            }

            // Измерение времени поиска
            long startTime = System.nanoTime();
            for (int value : searchValues) {
                table.find(value);
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;
            times[i] = (int) durationMs;

            System.out.printf("%6d  | %8d%n", size, durationMs);
        }

        createChart(times);
    }

    /**
     * Создает график зависимости времени поиска от размера таблицы
     * @param times массив времени выполнения для каждого размера таблицы
     */
    private static void createChart(int[] times) {
        int[] sizes = {100, 1_000, 10_000, 100_000, 1_000_000};
        XYSeries series = new XYSeries("Хеш-таблица поиск");

        for (int i = 0; i < sizes.length; i++) {
            series.add(sizes[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Производительность хеш-таблицы",
                "Размер таблицы",
                "Время поиска (мс)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartFrame frame = new ChartFrame("График производительности", chart);
        frame.pack();
        frame.setVisible(true);
    }
}