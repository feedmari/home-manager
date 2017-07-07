package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.interfaces.IShowEarningAndExpenseAvanced;

import com.toedter.calendar.JDateChooser;

import controller.classes.ShowEarningAndExpenseAvancedController;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;

/**
 * This panel is a decoration of {@link ShowEarningAndExpensesBase} it add the
 * possiblity of edit earnings/expenses and the possibility to show
 * earnings/expenses filtered in differents modality (total, for months, for
 * week).
 * 
 * @author Marco Mancini
 *
 */
public class ShowEarningAndExpenseAvanced extends ShowEarningAndExpensesBase implements IShowEarningAndExpenseAvanced {

	private static final long serialVersionUID = 91333520487410778L;
	static final String[] MONTHS = { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" };
	private JComboBox<String> showBox;
	private JComboBox<String> monthsBox;
	private JDateChooser dateChoser;
	private IShowEarningAndExpenseAvancedObserver observer;
	private JButton editBtn;
	private JPanel south;
	private JButton deleteBtn;

	/**
	 * Constructor.
	 * @param showBase the panel decorated
	 */
	public ShowEarningAndExpenseAvanced(final ShowEarningAndExpensesBase showBase) {
		super();
		this.createTable();

		this.createButtons();

		this.setLayoutAndAddComp();

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



	@Override
	public void createButtons() {
		String[] boxChooses = new String[] { "Totali", "Per mese", "Per settimana" };

		this.back = new JButton("Indietro");
		this.editBtn = new JButton("Modifica");
		this.deleteBtn = new JButton("Cancella");
		this.showBox = new JComboBox<String>(boxChooses);
		this.showBox.setSelectedIndex(0);
		this.monthsBox = new JComboBox<String>(MONTHS);
		this.monthsBox.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		this.dateChoser = new JDateChooser(new Date());
		this.panel = this;

		this.monthsBox.setVisible(false);
		this.dateChoser.setVisible(false);

		this.showBox.addItemListener(new BoxesListener());
		this.monthsBox.addItemListener(new BoxesListener());
		this.back.addActionListener(new ListenerAvanced());
		this.editBtn.addActionListener(new ListenerAvanced());
		this.deleteBtn.addActionListener(new ListenerAvanced());
		this.dateChoser.getDateEditor().addPropertyChangeListener(new DateListener());

	}

	@Override
	public void setLayoutAndAddComp() {
		south = new JPanel(new FlowLayout());

		south.add(back);
		south.add(showBox);
		south.add(monthsBox);
		south.add(dateChoser);
		south.add(editBtn);
		south.add(deleteBtn);

		setLayout(new BorderLayout());
		add(this.scrollPane, BorderLayout.CENTER);
		add(this.south, BorderLayout.SOUTH);
	}

	@Override
	public void attachObserver(final IShowEarningAndExpenseAvancedObserver observer) {
		this.observer = observer;

	}

	private class ListenerAvanced implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			Object src = e.getSource();
			if (src == back) {
				observer.back();
			} else if (src == editBtn) {
				try {

					observer.edit(Integer.parseInt(table.getValueAt(table.getSelectedRow(), ID_COLUMN).toString()));
				} catch (IndexOutOfBoundsException exc) {
					JOptionPane.showMessageDialog(panel, "Selezionare una riga della tabella!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			} else if (src == deleteBtn) {
				try {
					if (table.getValueAt(table.getSelectedRow(), ID_COLUMN) != null) {
						observer.delete(Integer.parseInt(table.getValueAt(table.getSelectedRow(), ID_COLUMN).toString()));
					}
				} catch (IndexOutOfBoundsException exc) {
					JOptionPane.showMessageDialog(panel, "Selezionare una riga della tabella!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}

	private class BoxesListener implements ItemListener {
		@Override
		public void itemStateChanged(final ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				Object item = event.getItem();
				if ((item.toString().equals("Per mese"))) {
					setComboBoxMonth(true);
					setDateChooser(false);
					observer.showForMonth(monthsBox.getSelectedIndex());
				} else if (item.toString().equals("Per settimana")) {
					setComboBoxMonth(false);
					setDateChooser(true);
					observer.showForWeek(dateChoser.getCalendar());
				} else if (findInMonths(item.toString())) {
					setComboBoxMonth(true);
					setDateChooser(false);
					observer.showForMonth(monthsBox.getSelectedIndex());
				} else if ((item.toString().equals("Totali"))) {
					setComboBoxMonth(false);
					setDateChooser(false);
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
	 * {@link ShowEarningAndExpenseAvancedController}
	 * 
	 * @author Marco Mancini
	 *
	 */
	public interface IShowEarningAndExpenseAvancedObserver extends IObserver, IShowEarningAndExpensesBaseObserver {

		/**
		 * Show total earnings/expenses list.
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
		 * Edit the selected row.
		 * 
		 * @param selected
		 *            the selected row
		 */
		void edit(int selected);

		/**
		 * Delete the selected earning/expense's.
		 * 
		 * @param selected
		 *            the selected earning/expense's
		 */
		void delete(int selected);

		/**
		 * Set the list that will be managed from view.
		 * 
		 * @param list the list used from view.
		 */
		void setList(List<IEarningAndExpense> list);

	}

}
