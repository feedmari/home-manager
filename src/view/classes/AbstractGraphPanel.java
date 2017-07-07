package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;

import org.jfree.chart.ChartPanel;

import controller.classes.PieGraphController;
import view.interfaces.IGraphPanel;


/**
 * Abstract panel for all application's graph.
 * @author federico marinelli
 *
 */
public abstract class AbstractGraphPanel extends AbstractMainPanel implements IGraphPanel {

	private static final long serialVersionUID = -4227053589458132239L;

	protected final JButton back;
	protected final JButton uscite;
	protected final JButton entrate;
	protected IGraphObserver observer;
	protected List<IEarningAndExpense> earningList;
	protected List<IEarningAndExpense> expenseList;
	protected JPanel center;
	protected JPanel panel; 

	/**
	 * Constructor.
	 * 
	 * @param earningList the erning list for model the graph
	 * @param expenseList the expense list fot model the graph
	 */
	public AbstractGraphPanel(final List<IEarningAndExpense> earningList, final  List<IEarningAndExpense> expenseList) {

		this.earningList = earningList;
		this.expenseList = expenseList;
		
		panel = new JPanel(new BorderLayout());
		center = new JPanel(new FlowLayout());
		final JPanel south = new JPanel(new BorderLayout());
		entrate = new JButton("Visualizza grafo entrate");
		uscite = new JButton("Visualizza grafo uscite");
		back = new JButton("Indietro");
		
		
		this.back.addActionListener(new Listener());
		this.entrate.addActionListener(new Listener());
		this.uscite.addActionListener(new Listener());
		
		final JPanel southCenter = new JPanel(new FlowLayout());
		center.add(this.createChart(earningList, "ENTRATE"));
		southCenter.add(entrate);
		southCenter.add(uscite);
		final JPanel southSouth = new JPanel(new FlowLayout());
		southSouth.add(back);
		south.add(southSouth, BorderLayout.SOUTH);
		south.add(southCenter, BorderLayout.CENTER);
		panel.add(center, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		this.add(panel);
	}
	
	/**
	 * Method to create the center graph panel.
	 * @param list the expense/earning list
	 * @param stato the state of the graph(expense or earning)
	 * @return chartPanel the graph panel
	 */
	public abstract ChartPanel createChart(List<IEarningAndExpense> list, String stato);
		

	@Override
	public void attachObserver(final IGraphObserver observer) {
		this.observer = observer;

	}

	/**
	 * In the listener a template method for create the chartPanel and put it in my view
	 * @author federico marinelli
	 *
	 */
	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == back) {
				observer.back();
			} else if (src == entrate) {
				center.removeAll();
				center.add(createChart(earningList, "ENTRATE"));
				panel.updateUI();
			} else if (src == uscite) {
				center.removeAll();
				center.add(createChart(expenseList, "USCITE"));
				panel.updateUI();
			}
		}

	}

	/**
	 * Interface for {@link PieGraphController}.
	 * 
	 * 
	 * @author federico marinelli
	 *
	 */
	public interface IGraphObserver extends IObserver {

		/**
		 * Manage the turn back procedure.
		 */
		void back();

	}

}
