package controller.classes;

import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.InexistentHabitationException;
import model.classes.Expense;
import model.classes.Expense.ExpenseType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddExpensesPanel;
import view.classes.MainFrame;
import view.classes.AddExpensesPanel.IAddExpensesObserver;

/**
 * The controller for {@link AddExpensesPanel}.
 * 
 * @author marco mancini
 *
 */
public class AddExpenseController implements IAddExpensesObserver {
	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final int indexHome;
	private final JPanel manageHabitationsPanel;
	private final AddExpensesPanel addExpensesPanel;

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param userLogged
	 *            the logged user
	 * @param indexHome
	 *            the home's index
	 * @param managePanel
	 *            the manage panel
	 * @param addExpensesPanel
	 *            the expense's panek
	 */
	public AddExpenseController(final IModel model, final MainFrame mainFrame, final String userLogged, final int indexHome, final JPanel managePanel,
			final AddExpensesPanel addExpensesPanel) {
		super();
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = userLogged;
		this.indexHome = indexHome;
		this.manageHabitationsPanel = managePanel;
		this.addExpensesPanel = addExpensesPanel;
		this.addExpensesPanel.attachObserver(this);
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(this.manageHabitationsPanel);

	}

	@Override
	public void confirm(final ExpenseType type, final double amount, final boolean isPayed, final Calendar date, final String description, final IEarningAndExpense expense) {
		try {
			final int ind = this.model.getExpenseList(this.model.getHabitation(userLogged, indexHome)).indexOf(expense);
			if (amount != 0) {
				if (expense == null) {
					final IEarningAndExpense transition = new Expense(type, amount, isPayed, date, model.getExpenseList(this.model.getHabitation(userLogged, indexHome)).size());
					transition.setDescription(description);
					model.addTransition(this.model.getHabitation(userLogged, indexHome), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(manageHabitationsPanel);

				} else {
					final IEarningAndExpense transitionTest = new Expense(type, amount, isPayed, date, ind);
					model.deleteTransition(model.getHabitation(userLogged, indexHome), transitionTest, ind);
					final IEarningAndExpense transition = new Expense(type, amount, isPayed, date, model.getExpenseList(this.model.getHabitation(userLogged, indexHome)).size());
					transition.setDescription(description);
					model.addTransition(this.model.getHabitation(userLogged, indexHome), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(manageHabitationsPanel);
				}

			} else {
				this.mainFrame.showPaneMessage(this.mainFrame, "Inserire un valore maggiore di 0 nell'importo!", "Errore", JOptionPane.ERROR_MESSAGE);
			}

		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

	}

}
