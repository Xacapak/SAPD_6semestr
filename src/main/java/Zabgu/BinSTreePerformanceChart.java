package Zabgu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BinSTreePerformanceChart {
    private static final int[] DEFAULT_SIZES = {100, 1_000, 10_000, 100_000, 1_000_000};

    public static void createChart(int[] times) {
        if (times.length != DEFAULT_SIZES.length) {
            throw new IllegalArgumentException(
                    "Количество временных значений должно быть " + DEFAULT_SIZES.length
            );
        }

        XYSeries series = new XYSeries("BST Поиск");
        for (int i = 0; i < DEFAULT_SIZES.length; i++) {
            series.add(DEFAULT_SIZES[i], times[i]);
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