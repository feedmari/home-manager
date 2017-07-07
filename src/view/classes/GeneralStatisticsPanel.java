package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.IObserver;
import view.interfaces.IGeneralStatistics;

import com.toedter.calendar.JDateChooser;

/**
 * Panel for show general statistics.
 * 
 * @author federico marinelli
 *
 */
public class GeneralStatisticsPanel extends AbstractMainPanel implements IGeneralStatistics {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4816408226160803815L;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private static final double WEIGHTX = 20;
	private static final double WEIGHTY = 50;
	static final String[] MONTHS = { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" };
	private final JComboBox<String> showBox;
	private final JComboBox<String> monthsBox;
	private final JDateChooser dateChoser;
	private final JButton back;
	private final JLabel welcomeLbl;
	private final JLabel saldoTotale;
	private final JLabel saldoEntrate;
	private final JLabel saldoUscite;
	private final JLabel graphLb;
	private final JButton pieGraph;
	private final JButton xyGraph;
	private IGeneralStatisticsObserver observer;

	/**
	 * Constructor.
	 */
	public GeneralStatisticsPanel() {
		super();

		final String[] boxChooses = new String[] { "Totali", "Per mese", "Per settimana" };

		this.welcomeLbl = new JLabel(" Statistiche Totali");
		this.welcomeLbl.setOpaque(true);
		this.welcomeLbl.setFont(FONT);
		this.back = new JButton("Indietro");
		this.showBox = new JComboBox<String>(boxChooses);
		this.showBox.setSelectedIndex(0);
		this.monthsBox = new JComboBox<String>(MONTHS);
		this.monthsBox.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		this.dateChoser = new JDateChooser(new Date());

		this.monthsBox.setVisible(false);
		this.dateChoser.setVisible(false);

		this.showBox.addItemListener(new BoxesListener());
		this.monthsBox.addItemListener(new BoxesListener());
		this.back.addActionListener(new ListenerAvanced());

		final GridBagLayout centerLayout = new GridBagLayout();
		final GridBagConstraints centerConstraints = new GridBagConstraints();
		centerConstraints.weighty = WEIGHTY;
		centerConstraints.weightx = WEIGHTX;
		final JPanel center = new JPanel(centerLayout);
		this.saldoTotale = new JLabel("Saldo totale:");
		this.saldoEntrate = new JLabel("Saldo entrate:");
		this.saldoUscite = new JLabel("Saldo uscite:");
		this.saldoEntrate.setFont(FONT);
		this.saldoUscite.setFont(FONT);
		this.saldoTotale.setFont(FONT);

		this.dateChoser.getDateEditor().addPropertyChangeListener(new DateListener());

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(saldoEntrate, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(saldoUscite, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 2;
		center.add(saldoTotale, centerConstraints);

		final JPanel southBordered = new JPanel(new BorderLayout());
		final JPanel southNorth = new JPanel(new FlowLayout());
		final JPanel southCenter = new JPanel(new FlowLayout());
		southNorth.add(back);
		southNorth.add(showBox);
		southNorth.add(monthsBox);
		southNorth.add(dateChoser);

		graphLb = new JLabel("Visualizza grafo [TOTALE]: ");
		xyGraph = new JButton("XYGraph");
		pieGraph = new JButton("PieGraph");
		pieGraph.addActionListener(new ListenerAvanced());
		xyGraph.addActionListener(new ListenerAvanced());

		southCenter.add(graphLb);
		southCenter.add(xyGraph);
		southCenter.add(pieGraph);

		southBordered.add(southNorth, BorderLayout.CENTER);
		southBordered.add(southCenter, BorderLayout.NORTH);

		this.setLayout(new BorderLayout());
		this.add(this.welcomeLbl, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(southBordered, BorderLayout.SOUTH);

	}

	private void setComboBoxMonth(final boolean set) {
		this.monthsBox.setVisible(set);
	}

	private void setDateChooser(final boolean set) {
		this.dateChoser.setVisible(set);
	}

	private boolean findInMonths(final String text) {
		for (String s : MONTHS) {
			if (text == s) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method for start the view.
	 */
	public void startView() {
		this.observer.showTotal();
	}

	/**
	 * Method for set the total label.
	 * 
	 * @param total
	 *            the total balance
	 */
	public void setTotal(final double total) {
		this.saldoTotale.setText("Saldo totale: " + Double.toString(total));
	}

	/**
	 * Method for set the earning label.
	 * 
	 * @param earning
	 *            the earning balance
	 */
	public void setEarning(final double earning) {
		this.saldoEntrate.setText("Saldo entrate: " + Double.toString(earning));
	}

	/**
	 * Method for set the expense label.
	 * 
	 * @param expense
	 *            the expense balance
	 */
	public void setExpense(final double expense) {
		this.saldoUscite.setText("Saldo uscite: -" + Double.toString(expense));
	}

	@Override
	public void attachObserver(final IGeneralStatisticsObserver observer) {
		this.observer = observer;

	}

	private class ListenerAvanced implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == back) {
				observer.back();
			} else if (src == pieGraph) {
				observer.showPieGraph();
			} else if (src == xyGraph) {
				observer.showXYGraph();
			}

		}

	}

	private class BoxesListener implements ItemListener {
		@Override
		public void itemStateChanged(final ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				final Object item = event.getItem();
				if ((item.toString().equals("Per mese"))) {
					setComboBoxMonth(true);
					setDateChooser(false);
					welcomeLbl.setText(" Statistiche per il mese di: " + monthsBox.getItemAt(monthsBox.getSelectedIndex()).toString());
					graphLb.setText("Visualizza grafo per MESE [" + monthsBox.getItemAt(monthsBox.getSelectedIndex()).toString() + "] : ");
					observer.showForMonth(monthsBox.getSelectedIndex());
				} else if (item.toString().equals("Per settimana")) {
					setComboBoxMonth(false);
					setDateChooser(true);
					welcomeLbl.setText(" Statistiche per settimana");
					graphLb.setText("Visualizza grafo [SETTIMANA]: ");
					observer.showForWeek(dateChoser.getCalendar());
				} else if (findInMonths(item.toString())) {
					setComboBoxMonth(true);
					setDateChooser(false);
					welcomeLbl.setText(" Statistiche per il mese di: " + monthsBox.getItemAt(monthsBox.getSelectedIndex()).toString());
					graphLb.setText("Visualizza grafo per MESE [" + monthsBox.getItemAt(monthsBox.getSelectedIndex()).toString() + "] : ");
					observer.showForMonth(monthsBox.getSelectedIndex());
				} else if ((item.toString().equals("Totali"))) {
					setComboBoxMonth(false);
					setDateChooser(false);
					welcomeLbl.setText(" Statistiche totali");
					graphLb.setText("Visualizza grafo[TOTALE] : ");
					observer.showTotal();
				}
			}
		}
	}

	private class DateListener implements PropertyChangeListener {

		@Override
		public void propertyChange(final PropertyChangeEvent evt) {
			if (showBox.getSelectedItem().toString().equals("Per settimana")) {
				observer.showForWeek(dateChoser.getCalendar());
			}

		}

	}

	/**
	 * Interface for the controller.
	 * 
	 * 
	 * @author Federico Marinelli
	 *
	 */
	public interface IGeneralStatisticsObserver extends IObserver {

		/**
		 * Show total earnings/expenses.
		 */
		void showTotal();

		/**
		 * Show earning/expenses in month selected.
		 * 
		 * @param month
		 *            the month selected
		 */
		void showForMonth(int month);

		/**
		 * Show earnings/expenses in week selected.
		 * 
		 * @param date
		 *            the date
		 */
		void showForWeek(Calendar date);

		/**
		 * Show the pie graph for the time selected.
		 */
		void showPieGraph();

		/**
		 * Show the XY graph for the time selected.
		 */
		void showXYGraph();

	}
}
