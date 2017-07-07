package view.classes;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.interfaces.IEarningAndExpense;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


/**
 * Class for create the PieGraphPanel.
 * 
 * @author federico marinelli
 * 
 */
public class PieGraphPanel extends AbstractGraphPanel {

	private static final long serialVersionUID = -4227053589978132239L;

	/**
	 * Constructor.
	 * 
	 * @param earningList the erning list for model the graph
	 * @param expenseList the expense list fot model the graph
	 */
	public PieGraphPanel(final List<IEarningAndExpense> earningList, final  List<IEarningAndExpense> expenseList) {

		super(earningList, expenseList);
		
	}
	
	@Override
	public ChartPanel createChart(final List<IEarningAndExpense> list, final  String stato) {
		final Map<String, Integer> map = new HashMap<>();
		final DefaultPieDataset dataset = new DefaultPieDataset();
		for (final IEarningAndExpense e : list) {
			if (!map.containsKey(e.getType().getName())) {
				map.put(e.getType().getName(), 1);
			} else {
				int i;
				i = map.get(e.getType().getName()) + 1;
				map.put(e.getType().getName(), i);
			}
		}

		for (final String entry : map.keySet()) {
			dataset.setValue(entry, map.get(entry));
		}

		final JFreeChart pieChart = ChartFactory.createPieChart("Statistiche [" + stato + "]", dataset, true, true, false);
		final PiePlot plot = (PiePlot) pieChart.getPlot();

		final Random rand = new Random();
		for (final String entry : map.keySet()) {
			final float r = rand.nextFloat();
			final float g = rand.nextFloat();
			final float b = rand.nextFloat();
			final Color randomColor = new Color(r, g, b);
			plot.setSectionPaint(entry, randomColor);
		}

		final PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);

		final ChartPanel chart = new ChartPanel(pieChart) {

			private static final long serialVersionUID = -4865377524243658293L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(460, 390);
			}
		};
		
		return chart;
		
	}

}
