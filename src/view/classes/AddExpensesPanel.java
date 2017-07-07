package view.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.classes.AddExpenseController;
import model.classes.Expense.ExpenseType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;
import view.interfaces.IAddExpensesPanel;

/**
 * Panel for add an expense.
 * 
 * @author marco mancini
 *
 */
public class AddExpensesPanel extends AbstractEarningAndExpensesPanel implements IAddExpensesPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4702248368373333214L;
	private IAddExpensesObserver observer;

	/**
	 * The constructor.
	 * 
	 * @param earningOrExpense
	 *            earning/expense
	 */
	public AddExpensesPanel(final IEarningAndExpense earningOrExpense) {
		super(earningOrExpense);
	}

	@Override
	protected String[] chooseBoxStrings() {
		int count = 0;
		for (@SuppressWarnings("unused")
		final ExpenseType expenses : ExpenseType.values()) {
			count++;
		}

		String[] types = new String[count];
		int i = 0;
		for (final ExpenseType expenses : ExpenseType.values()) {
			types[i] = expenses.getName();
			i++;
		}
		return types;
	}

	@Override
	protected void attachListenerToConfirm() {
		super.confirm.addActionListener(new Listener());
		super.back.addActionListener(new Listener());

	}

	@Override
	public void attachObserver(final IAddExpensesObserver observer) {
		this.observer = observer;

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == confirm) {
				if (containsOnlyNumbers(amountFld.getText())) {
					if (typeBox.getSelectedItem().equals(ExpenseType.EXTRA)) {
						observer.confirm(ExpenseType.fromString(typeBox.getSelectedItem().toString()), Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(),
								dateChoser.getCalendar(), description.getText(), earnOrExp);
					} else {
						observer.confirm(ExpenseType.fromString(typeBox.getSelectedItem().toString()), Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(),
								dateChoser.getCalendar(), "", earnOrExp);
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Il valore dell'importo inserito non è corretto!", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
			} else if (src == back) {
				observer.back();
			}

		}

	}

	/**
	 * Interface for {@link AddExpenseController}.
	 * 
	 * @author marco mancini
	 *
	 */
	public interface IAddExpensesObserver extends IObserver {

		/**
		 * This command confirm and insert the expense.
		 * 
		 * @param type
		 *            the type
		 * @param amount
		 *            the amount
		 * @param isPayed
		 *            if is payed or not
		 * @param date
		 *            the date
		 * @param description
		 *            the description
		 * @param expense
		 *            null if it's a new expense, an expense if you have to
		 *            modify that earning
		 */
		void confirm(ExpenseType type, double amount, boolean isPayed, Calendar date, String description, IEarningAndExpense expense);

	}

}
