package view.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.classes.Expense.ExpenseUserType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;
import view.interfaces.IAddExpenseUserPanel;
import controller.classes.AddExpenseUserController;

/**
 * Panel for add an expense to an user.
 * 
 * @author federico marinelli
 *
 */
public class AddExpenseUserPanel extends AbstractEarningAndExpensesPanel implements IAddExpenseUserPanel {

	private static final long serialVersionUID = 4702248368373333214L;
	private IAddExpenseUserObserver observer;

	/**
	 * Constructor.
	 * 
	 * @param earningOrExpense
	 *            earning/expense
	 */
	public AddExpenseUserPanel(final IEarningAndExpense earningOrExpense) {
		super(earningOrExpense);
	}

	@Override
	protected String[] chooseBoxStrings() {
		int count = 0;
		for (@SuppressWarnings("unused")
		final ExpenseUserType expenses : ExpenseUserType.values()) {
			count++;
		}

		String[] types = new String[count];
		int i = 0;
		for (final ExpenseUserType expenses : ExpenseUserType.values()) {
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
	public void attachObserver(final IAddExpenseUserObserver observer) {
		this.observer = observer;

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == confirm) {
				if (containsOnlyNumbers(amountFld.getText())) {
					if (typeBox.getSelectedItem().equals(ExpenseUserType.SPESE_VARIE.getName())) {
						observer.confirm(ExpenseUserType.SPESE_VARIE, Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(), dateChoser.getCalendar(), "", earnOrExp);

					} else if (typeBox.getSelectedItem().equals(ExpenseUserType.EXTRA.getName())) {
						observer.confirm(ExpenseUserType.EXTRA, Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(), dateChoser.getCalendar(), description.getText(),
								earnOrExp);

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
	 * Interface for {@link AddExpenseUserController}.
	 * 
	 * @author federico marinelli
	 *
	 */
	public interface IAddExpenseUserObserver extends IObserver {

		/**
		 * This command confirm the expense.
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
		 * @param oldEarning
		 *            null if it's a new expense, an expense if you have to
		 *            modify that earning
		 */
		void confirm(ExpenseUserType type, double amount, boolean isPayed, Calendar date, String description, IEarningAndExpense oldEarning);

	}

}
