package controller.classes;

import java.util.Calendar;

import javax.swing.JOptionPane;

import exceptions.InexistentUserException;
import model.classes.Earning;
import model.classes.Expense;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddEarningUserPanel;
import view.classes.AddExpenseUserPanel;
import view.classes.MainFrame;
import view.classes.ManageUserPaymentPanel;
import view.classes.ShowEarningAndExpenseAvanced;

/**
 * Create and model a ShowEarningAndExpenseAvancedController for an User.
 * 
 * @author Federico Marinelli
 *
 */
public final class CreateUserEE {

	private CreateUserEE() {
	};

	/**
	 * Controller.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param userLogged
	 *            the userlogged string
	 * @param showEarningAndExpenses
	 *            the ShowEarningAndExpensesPanel
	 * @param managePanel
	 *            the ManageUserPaymentPanel
	 * @return ShowEarningAndExpenseAvancedController the controller
	 */
	public static ShowEarningAndExpenseAvancedController getAvancedController(final IModel model, final MainFrame mainFrame, final String userLogged,
			final ShowEarningAndExpenseAvanced showEarningAndExpenses, final ManageUserPaymentPanel managePanel) {
		final ShowEarningAndExpenseAvancedController controller = new ShowEarningAndExpenseAvancedController() {

			@Override
			public void edit(final int selected) {
				IEarningAndExpense expenseOrEarning = null;

				if (this.getList().get(0) != null) {
					if (this.getList().get(0) instanceof Expense) {
						try {
							expenseOrEarning = this.getModel().getExpenseList(model.getUser(userLogged)).get(selected);
						} catch (InexistentUserException e) {
							System.err.println("Caught Exception: " + e.getMessage());
						}
						AddExpenseUserPanel view = new AddExpenseUserPanel(expenseOrEarning);
						new AddExpenseUserController(model, mainFrame, userLogged, (ManageUserPaymentPanel) managePanel, view);
						this.getMainFrame().setCenterPanel(view);
					} else {
						try {
							expenseOrEarning = this.getModel().getEarningList(this.getModel().getUser(userLogged)).get(selected);
						} catch (InexistentUserException e) {
							System.err.println("Caught Exception: " + e.getMessage());
						}
						AddEarningUserPanel view = new AddEarningUserPanel(expenseOrEarning);
						new AddEarningUserController(model, mainFrame, userLogged, (ManageUserPaymentPanel) managePanel, view);
						this.getMainFrame().setCenterPanel(view);
					}
				}
			}

			@Override
			public void delete(final int selected) {
				if (this.getList().get(0) != null) {
					if (this.getList().get(0) instanceof Expense) {
						final Expense expDel = new Expense(null, 0, true, Calendar.getInstance(), selected);
						try {
							this.getModel().deleteTransition(this.getModel().getUser(userLogged), expDel, selected);
						} catch (InexistentUserException e) {
							System.err.println("Caught Exception: " + e.getMessage());
						}
						this.getMainFrame().showPaneMessage(mainFrame, "Cancellazione avvenuta", "Cancellazione", JOptionPane.INFORMATION_MESSAGE);
						this.createTable(this.getList());
					} else {
						final Earning earDel = new Earning(null, 0, true, Calendar.getInstance(), selected);
						try {
							this.getModel().deleteTransition(this.getModel().getUser(userLogged), earDel, selected);
						} catch (InexistentUserException e) {
							System.err.println("Caught Exception: " + e.getMessage());
						}
						this.getMainFrame().showPaneMessage(mainFrame, "Cancellazione avvenuta", "Cancellazione", JOptionPane.INFORMATION_MESSAGE);
						this.createTable(this.getList());
					}
				}

			}

		};

		controller.setModel(model);
		controller.setMainFrame(mainFrame);
		controller.setUserLogged(userLogged);
		controller.setShowEarningAndExpenses(showEarningAndExpenses);
		controller.setManagePanel(managePanel);
		return controller;
	}
}
