package Zabgu;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Класс для тестирования производительности AVL-дерева.
 * Измеряет время поиска элементов и строит график зависимости времени от размера дерева.
 */
public class AVLTreePerformanceTest {
    /**
     * Тестирует производительность поиска в AVL-дереве.
     * Заполняет дерево случайными уникальными значениями и измеряет время поиска.
     * Выводит результаты в консоль и строит график.
     */
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в AVL-дереве:");
        System.out.println("Размер  | Время (мс)");
        System.out.println("--------------------");

        int[] sizes = {100, 1_000, 10_000, 100_000, 500_000,1_000_000};
        int searchIterations = 100_000;
        int[] times = new int[sizes.length];
        Random rand = new Random();

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];

            // Генерация уникальных значений
            Set<Integer> values = new HashSet<>();
            while (values.size() < size) {
                values.add(rand.nextInt(size * 10));
            }

            // Заполнение дерева
            AVLTree<Integer> avl = new AVLTree<>();
            for (int value : values) {
                avl.insert(value);
            }

            // Подготовка значений для поиска (50% существующих, 50% случайных)
            int[] searchValues = new int[searchIterations];
            Integer[] existingValues = values.toArray(new Integer[0]);
            for (int j = 0; j < searchIterations; j++) {
                searchValues[j] = rand.nextBoolean() ?
                        existingValues[rand.nextInt(existingValues.length)] :
                        rand.nextInt(size * 10);
            }

            // Измерение времени поиска
            long startTime = System.nanoTime();
            for (int value : searchValues) {
                avl.contains(value);
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;
            times[i] = (int) durationMs;

            System.out.printf("%6d  | %8d%n", size, durationMs);
        }

        createChart(sizes, times);
    }

    /**
     * Создает график зависимости времени поиска от размера дерева.
     * @param sizes массив размеров дерева
     * @param times массив времени выполнения для каждого размера
     */
    private static void createChart(int[] sizes, int[] times) {
        XYSeries series = new XYSeries("AVL-дерево поиск");

        for (int i = 0; i < sizes.length; i++) {
            series.add(sizes[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Производительность AVL-дерева",
                "Размер дерева",
                "Время поиска (мс)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartFrame frame = new ChartFrame("График производительности AVL-дерева", chart);
        frame.pack();
        frame.setVisible(true);
    }

}