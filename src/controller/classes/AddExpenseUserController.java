package controller.classes;

import java.util.Calendar;

import javax.swing.JOptionPane;

import exceptions.InexistentUserException;
import model.classes.Expense;
import model.classes.Expense.ExpenseUserType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddExpenseUserPanel;
import view.classes.AddExpenseUserPanel.IAddExpenseUserObserver;
import view.classes.MainFrame;
import view.classes.ManageUserPaymentPanel;

/**
 * The controller for {@link AddExpenseUserPanel}.
 * 
 * @author federico marinelli
 *
 */
public class AddExpenseUserController implements IAddExpenseUserObserver {
	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final ManageUserPaymentPanel managePanel;
	private final AddExpenseUserPanel addExpensePanel;

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe panel
	 * @param userLogged
	 *            the userlogged string
	 * @param managePanel
	 *            the manage user payment panel
	 * @param addExpensesPanel
	 *            the addexpenseuserpanel
	 */
	public AddExpenseUserController(final IModel model, final MainFrame mainFrame, final String userLogged, final ManageUserPaymentPanel managePanel,
			final AddExpenseUserPanel addExpensesPanel) {
		super();
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = userLogged;
		this.managePanel = managePanel;
		this.addExpensePanel = addExpensesPanel;
		this.addExpensePanel.attachObserver(this);
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(managePanel);

	}

	@Override
	public void confirm(final ExpenseUserType type, final double amount, final boolean isPayed, final Calendar date, final String description, final IEarningAndExpense oldEarning) {
		try {
			final int ind = this.model.getExpenseList(this.model.getUser(userLogged)).indexOf(oldEarning);
			if (amount != 0) {
				if (oldEarning == null) {
					final IEarningAndExpense transition = new Expense(type, amount, isPayed, date, this.model.getUser(userLogged).getWallet().getEarningList().size());
					transition.setDescription(description);
					model.addTransition(this.model.getUser(userLogged), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(managePanel);
				} else {
					final IEarningAndExpense transitionTest = new Expense(type, amount, isPayed, date, ind);
					this.model.deleteTransition(this.model.getUser(userLogged), transitionTest, ind);
					final IEarningAndExpense transition = new Expense(type, amount, isPayed, date, this.model.getUser(userLogged).getWallet().getEarningList().size());
					transition.setDescription(description);
					this.model.addTransition(this.model.getUser(userLogged), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(managePanel);
				}

			} else {
				this.mainFrame.showPaneMessage(this.mainFrame, "Inserire un valore maggiore di 0 nell'importo!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}

}