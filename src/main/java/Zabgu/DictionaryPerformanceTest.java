package Zabgu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Класс для тестирования производительности Dictionary.
 * Измеряет время поиска элементов при разных размерах словаря.
 */
public class DictionaryPerformanceTest {
    /**
     * Тестирует производительность поиска в Dictionary.
     * Выводит результаты в консоль и строит график зависимости времени от размера.
     */
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в Dictionary:");
        System.out.println("Размер  | Время (мс)");
        System.out.println("--------------------");

        int[] sizes = {100, 1_000, 10_000, 100_000, 1_000_000};
        int searchIterations = 100_000;
        int[] times = new int[sizes.length];
        Random rand = new Random();

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            Dictionary<Integer, String> dict = new Dictionary<>();

            // Заполнение словаря случайными уникальными ключами
            Set<Integer> uniqueKeys = new HashSet<>();
            while (uniqueKeys.size() < size) {
                uniqueKeys.add(rand.nextInt(size * 10));
            }
            for (Integer key : uniqueKeys) {
                dict.put(key, "value_" + key);
            }

            // Подготовка ключей для поиска (50% существующих, 50% случайных)
            int[] searchKeys = new int[searchIterations];
            for (int j = 0; j < searchIterations; j++) {
                searchKeys[j] = rand.nextBoolean() ?
                        new ArrayList<>(uniqueKeys).get(rand.nextInt(size)) :
                        size * 10 + rand.nextInt(size * 10);
            }

            // Измерение времени поиска
            long startTime = System.nanoTime();
            for (int key : searchKeys) {
                dict.get(key);
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;
            times[i] = (int) durationMs;

            System.out.printf("%6d  | %8d%n", size, durationMs);
        }

        createChart(sizes, times);
    }

    /**
     * Создает график зависимости времени поиска от размера словаря.
     * @param sizes массив размеров словаря
     * @param times массив времени выполнения для каждого размера
     */
    private static void createChart(int[] sizes, int[] times) {
        XYSeries series = new XYSeries("Dictionary поиск");

        for (int i = 0; i < sizes.length; i++) {
            series.add(sizes[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Производительность Dictionary",
                "Размер словаря",
                "Время поиска (мс)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartFrame frame = new ChartFrame("График производительности Dictionary", chart);
        frame.pack();
        frame.setVisible(true);
    }

}

