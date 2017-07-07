package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import controller.classes.ReminderController;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;
import view.interfaces.IReminderPanel;

/**
 * The reminder panel.
 * 
 * @author Marco Mancini
 *
 */
public class ReminderPanel extends AbstractMainPanel implements IReminderPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7644197835997811549L;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private static final int IPAD_X = 400;
	private static final int IPAD_Y = 140;
	private static final String[] COLUMNS_EARNINGS = new String[] { "Promemoria entrate da ricevere" };
	private static final String[] COLUMNS_EXPENSES = new String[] { "Promemoria spese da pagare" };
	private static final double WEIGHTX = 10;
	private static final double WEIGHTY = 5;
	private final JLabel welcomeLbl;
	private final Object[][] tableDataEarnings = new Object[][] {};
	private final JScrollPane scrollPaneEarnings;
	private final JTable tableEarnings;
	private final Object[][] tableDataExpenses = new Object[][] {};
	private final JScrollPane scrollPaneExpenses;
	private final JTable tableExpenses;
	private final JButton back;
	private IReminderPanelObserver observer;

	/**
	 * Contructor.
	 */
	public ReminderPanel() {
		super();

		this.welcomeLbl = new JLabel("Promemoria Mensile");
		this.back = new JButton("Indietro");

		this.welcomeLbl.setFont(FONT);

		DefaultTableModel tableModelEarnings = new DefaultTableModel(this.tableDataEarnings, COLUMNS_EARNINGS);
		this.tableEarnings = new JTable(tableModelEarnings) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		};
		this.tableEarnings.getTableHeader().setResizingAllowed(false);
		this.tableEarnings.getTableHeader().setReorderingAllowed(false);
		this.tableEarnings.setFillsViewportHeight(true);
		this.scrollPaneEarnings = new JScrollPane(this.tableEarnings);
		this.scrollPaneEarnings.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		DefaultTableModel tableModelExpenses = new DefaultTableModel(this.tableDataExpenses, COLUMNS_EXPENSES);
		this.tableExpenses = new JTable(tableModelExpenses) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		};
		this.tableExpenses.getTableHeader().setResizingAllowed(false);
		this.tableExpenses.getTableHeader().setReorderingAllowed(false);
		this.tableExpenses.setFillsViewportHeight(true);
		this.scrollPaneExpenses = new JScrollPane(this.tableExpenses);
		this.scrollPaneExpenses.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.back.addActionListener(new Listener());

		final JPanel north = new JPanel(new FlowLayout());
		north.add(welcomeLbl);

		GridBagLayout centerLayout = new GridBagLayout();
		GridBagConstraints centerConstraints = new GridBagConstraints();
		centerConstraints.weighty = WEIGHTY;
		centerConstraints.weightx = WEIGHTX;
		centerConstraints.ipadx = IPAD_X;
		centerConstraints.ipady = IPAD_Y;
		final JPanel center = new JPanel(centerLayout);

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(scrollPaneEarnings, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(scrollPaneExpenses, centerConstraints);

		final JPanel south = new JPanel(new FlowLayout());
		south.add(back);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

	}

	@Override
	public void attachObserver(final IReminderPanelObserver observer) {
		this.observer = observer;

	}

	@Override
	public void refreshTables() {
		((DefaultTableModel) this.tableEarnings.getModel()).getDataVector().clear();
		((DefaultTableModel) this.tableEarnings.getModel()).fireTableDataChanged();

	}

	@Override
	public void addEarningRow(final Object[] obj) {

		((DefaultTableModel) this.tableEarnings.getModel()).addRow(obj);

	}

	@Override
	public void addExpenseRow(final Object[] obj) {
		((DefaultTableModel) this.tableExpenses.getModel()).addRow(obj);

	}

	@Override
	public void removeAll() {
		DefaultTableModel model = (DefaultTableModel) this.tableEarnings.getModel();
		int rows = model.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		DefaultTableModel model2 = (DefaultTableModel) this.tableExpenses.getModel();
		int rows2 = model2.getRowCount();
		for (int i = rows2 - 1; i >= 0; i--) {
			model2.removeRow(i);
		}

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			observer.back();

		}

	}

	/**
	 * Interface for {@link ReminderController}.
	 * @author Marco Mancini
	 *
	 */
	public interface IReminderPanelObserver extends IObserver {
		/**
		 * Populates the table with monthly reminders.
		 * @param listEarnings earning's list
		 * @param listExpenses expense's list
		 */
		void createMonthlyReminders(List<IEarningAndExpense> listEarnings, List<IEarningAndExpense> listExpenses);

	}

}
