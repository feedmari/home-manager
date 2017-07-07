package controller.classes;

import java.util.ArrayList;
import java.util.List;

import exceptions.InexistentUserException;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddEarningUserPanel;
import view.classes.AddExpenseUserPanel;
import view.classes.GeneralStatisticsPanel;
import view.classes.HomePanel;
import view.classes.MainFrame;
import view.classes.ReminderPanel;
import view.classes.ShowEarningAndExpenseAvanced;
import view.classes.ShowEarningAndExpensesBase;
import view.classes.ManagePaymentPanel.IManagePaymentObserver;
import view.classes.ManageUserPaymentPanel;

/**
 * The manage user controller. {@link ManageUserPaymentPanel}
 * 
 * @author marinelli
 *
 */
public class ManageUserController implements IManagePaymentObserver {

	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final ManageUserPaymentPanel managePanel;
	private final HomePanel homePanel;

	/**
	 * Controller.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param userLogged
	 *            the userlogged string
	 * @param managePanel
	 *            the manage panel
	 * @param homePanel
	 *            the homepanel
	 */
	public ManageUserController(final IModel model, final MainFrame mainFrame, final String userLogged, final ManageUserPaymentPanel managePanel, final HomePanel homePanel) {
		super();
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = userLogged;
		this.managePanel = managePanel;
		this.managePanel.attachObsverver(this);
		this.homePanel = homePanel;
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(homePanel);

	}

	@Override
	public void addExpense() {
		final AddExpenseUserPanel expensePanel = new AddExpenseUserPanel(null);
		new AddExpenseUserController(model, mainFrame, userLogged, managePanel, expensePanel);
		this.mainFrame.setCenterPanel(expensePanel);
	}

	@Override
	public void addEarning() {
		final AddEarningUserPanel earningPanel = new AddEarningUserPanel(null);
		new AddEarningUserController(model, mainFrame, userLogged, managePanel, earningPanel);
		this.mainFrame.setCenterPanel(earningPanel);
	}

	@Override
	public void showExpenses() {
		final ShowEarningAndExpensesBase viewBase = new ShowEarningAndExpensesBase();
		final ShowEarningAndExpenseAvanced viewAvanced = new ShowEarningAndExpenseAvanced(viewBase);

		final ShowEarningAndExpenseAvancedController controller = CreateUserEE.getAvancedController(model, mainFrame, userLogged, viewAvanced, managePanel);
		controller.attachObserver();
		try {
			controller.setList(this.model.getExpenseList(this.model.getUser(userLogged)));
			controller.createTable(this.model.getExpenseList(this.model.getUser(userLogged)));
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		this.mainFrame.setCenterPanel(viewAvanced);

	}

	@Override
	public void showEarnings() {
		final ShowEarningAndExpensesBase viewBase = new ShowEarningAndExpensesBase();
		final ShowEarningAndExpenseAvanced viewAvanced = new ShowEarningAndExpenseAvanced(viewBase);

		final ShowEarningAndExpenseAvancedController controller = CreateUserEE.getAvancedController(model, mainFrame, userLogged, viewAvanced, managePanel);
		controller.attachObserver();
		try {
			controller.setList(this.model.getEarningList(this.model.getUser(userLogged)));
			controller.createTable(this.model.getEarningList(this.model.getUser(userLogged)));
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		this.mainFrame.setCenterPanel(viewAvanced);
	}

	@Override
	public void showCalendar() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showReminder() {
		final ReminderPanel reminderPanel = new ReminderPanel();
		try {
			final List<IEarningAndExpense> earnings = new ArrayList<>(this.model.getEarningList(this.model.getUser(userLogged)));
			final List<IEarningAndExpense> expenses = new ArrayList<>(this.model.getExpenseList(this.model.getUser(userLogged)));
			new ReminderController(mainFrame, managePanel, reminderPanel).createMonthlyReminders(earnings, expenses);
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		this.mainFrame.setCenterPanel(reminderPanel);
	}

	@Override
	public void showStatistics() {
		final GeneralStatisticsPanel statsPanel = new GeneralStatisticsPanel();
		new UserStatisticsController(mainFrame, statsPanel, model, userLogged, managePanel);
		statsPanel.startView();
		this.mainFrame.setCenterPanel(statsPanel);

	}

}
