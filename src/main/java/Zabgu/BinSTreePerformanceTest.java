package Zabgu;

import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BinSTreePerformanceTest {
    public static void testSearchPerformance() {
        System.out.println("\nТестирование времени поиска в 'Бинарном дереве поиска':");
        System.out.println("Размер  | Время (мс)");
        System.out.println("--------------------");

        int[] sizes = {100, 1_000, 10_000, 100_000, 1_000_000};
        int searchIterations = 100_000;
        int[] times = new int[sizes.length];
        Random rand = new Random();

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            BinSTree<Integer> tree = new BinSTree<>();

            for (int j = 0; j < size; j++) {
                int value = rand.nextInt(size * 10);
                while (tree.find(value) != null) {
                    value = rand.nextInt(size * 10);
                }
                tree.insert(value);
            }

            int[] searchValues = new int[searchIterations];
            for (int j = 0; j < searchIterations; j++) {
                searchValues[j] = rand.nextBoolean() ?
                        rand.nextInt(size * 10) :
                        size * 10 + rand.nextInt(size * 10);
            }

            long startTime = System.nanoTime();
            for (int value : searchValues) {
                tree.find(value);
            }
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;
            times[i] = (int) durationMs;

            System.out.printf("%6d  | %8d%n", size, durationMs);
        }

        createChart(times);
    }

    private static void createChart(int[] times) {
        int[] sizes = {100, 1_000, 10_000, 100_000, 1_000_000};
        XYSeries series = new XYSeries("BST Поиск");

        for (int i = 0; i < sizes.length; i++) {
            series.add(sizes[i], times[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Зависимость времени поиска от размера BST",
                "Размер дерева (элементы)",
                "Время поиска (мс)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartFrame frame = new ChartFrame("BST график", chart);
        frame.pack();
        frame.setVisible(true);
    }
}