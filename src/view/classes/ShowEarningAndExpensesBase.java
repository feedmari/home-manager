package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controller.classes.ShowEarningAndExpenseBaseController;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;
import view.interfaces.IShowEarningAndExpensesBase;

/**
 * Panel that show earnings/expenses.
 * @author Marco Mancini
 *
 */
public class ShowEarningAndExpensesBase extends AbstractMainPanel implements
		IShowEarningAndExpensesBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5246160730800424284L;
	
	/**
	 * Id column (hidden).
	 */
	protected static final int ID_COLUMN = 5;
	/**
	 * Table's columns.
	 */
	protected static final String[] COLUMNS = new String[] { "Spesa/Ricavo",
			"Importo", "Tipo", "Pagato", "Data", "ID" };
	/**
	 * Table's chooses.
	 */
	protected static final String[] CHOOSES = new String[] { "Importo", "Data",
			"Tipo" };
	protected final Object[][] tableData = new Object[][] {};
	protected JScrollPane scrollPane;
	protected JTable table;
	protected JButton back;
	protected ShowEarningAndExpensesBase panel;
	protected JPanel south;
	protected IShowEarningAndExpensesBaseObserver observer;

	/**
	 * Constructor.
	 */
	public ShowEarningAndExpensesBase() {
		super();

		this.createTable();

		this.createButtons();

		this.setLayoutAndAddComp();

	}

	@Override
	public void refreshTable() {
		((DefaultTableModel) this.table.getModel()).getDataVector().clear();
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();

	}

	@Override
	public void addRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();
		

	}
	

	@Override
	public void createTable() {
		final DefaultTableModel tableModel = new DefaultTableModel(this.tableData,
				COLUMNS);
		
		final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(
				tableModel);
		this.table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		};
	
		this.table.setRowSorter(sorter);	
		this.table.getTableHeader().setResizingAllowed(true);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.setFillsViewportHeight(true);
		
		//Nascondo la colonna dell'id
		table.getColumnModel().getColumn(ID_COLUMN).setMinWidth(0);
		table.getColumnModel().getColumn(ID_COLUMN).setMaxWidth(0);
		table.getColumnModel().getColumn(ID_COLUMN).setWidth(0);
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	}

	@Override
	public void createButtons() {
		this.back = new JButton("Indietro");
		this.back.addActionListener(new Listener());

	}

	@Override
	public void setLayoutAndAddComp() {
		south = new JPanel(new FlowLayout());

		south.add(back);

		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
	}

	@Override
	public void attachObserver(final IShowEarningAndExpensesBaseObserver observer) {
		this.observer = observer;

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == back) {
				observer.back();
			}

		}

	}

	/**
	 * Interface for the controller {@link ShowEarningAndExpenseBaseController}.
	 * @author Marco Mancini
	 *
	 */
	public interface IShowEarningAndExpensesBaseObserver extends IObserver {

		/**
		 * Creates the earning's/expenses table.
		 * @param usedlist the used list from table
		 * 
		 */
		void createTable(List<IEarningAndExpense> usedlist);

	}

}
