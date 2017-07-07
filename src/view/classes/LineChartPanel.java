package view.classes;

import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import model.interfaces.IEarningAndExpense;

/**
 * Panel for the LineChart.
 * 
 * @author federico marinelli
 * 
 */
public class LineChartPanel extends AbstractGraphPanel {

	private static final long serialVersionUID = 5802218493129632947L;

	/**
	 * Constructor.
	 * 
	 * @param earningList the erning list for model the graph
	 * @param expenseList the expense list fot model the graph
	 */
	public LineChartPanel(final List<IEarningAndExpense> earningList, final List<IEarningAndExpense> expenseList) {

		super(earningList, expenseList);

	}

	@Override
	public ChartPanel createChart(final List<IEarningAndExpense> list, final String stato) {

		final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		final Map<String, Double> map = new TreeMap<>(new Comparator<String>() {
			public int compare(final String obj1, final String obj2) {
				if (obj1 == obj2) {
					return 0;
				}
				if (obj1 == null) {
					return -1;
				}
				if (obj2 == null) {
					return 1;
				}
				return obj1.compareTo(obj2);
			}
		});
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (final IEarningAndExpense e : list) {
			if (!map.containsKey(format1.format(e.getDate().getTime()))) {
				map.put(format1.format(e.getDate().getTime()), e.getCost());
			} else {
				double i;
				i = map.get(format1.format(e.getDate().getTime())) + e.getCost();
				map.put(format1.format(e.getDate().getTime()), i);
			}
		}

		for (final String entry : map.keySet()) {
			dataset.setValue(map.get(entry), "expense/earning", entry);
		}

		final JFreeChart chart = ChartFactory.createBarChart3D("Statistiche: [" + stato + "]", "Data", "Incasso (€)", dataset, PlotOrientation.VERTICAL, false, true, false);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(460, 390));

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		final CategoryAxis categoryAxis = plot.getDomainAxis();
		categoryAxis.setTickLabelFont(new Font("Serif", Font.PLAIN, 10));
		plot.getRangeAxis().setTickLabelFont(new Font("Serif", Font.PLAIN, 10));
		categoryAxis.setMaximumCategoryLabelWidthRatio(1.9F);
		categoryAxis.setLowerMargin(0.01D);
		categoryAxis.setUpperMargin(0.05D);

		return chartPanel;
	}

}
