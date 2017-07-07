package controller.classes;

import java.util.ArrayList;
import java.util.List;

import exceptions.InexistentHabitationException;
import exceptions.InexistentUserException;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddEarningPanel;
import view.classes.AddExpensesPanel;
import view.classes.CalendarPanel;
import view.classes.GeneralStatisticsPanel;
import view.classes.HomePanel;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.ReminderPanel;
import view.classes.ManagePaymentPanel.IManagePaymentObserver;
import view.classes.ShowEarningAndExpenseAvanced;
import view.classes.ShowEarningAndExpensesBase;
import view.classes.ShowHabitationPanel;

/**
 * The Controller for {@link ManagePaymentPanel}.
 * 
 * @author marco mancini
 *
 */
public class ManageHabitationsController implements IManagePaymentObserver {
	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final int indexHome;
	private final ManagePaymentPanel managePanel;
	private final HomePanel homePanel;

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
	 * @param homePanel
	 *            the home panel
	 */
	public ManageHabitationsController(final IModel model, final MainFrame mainFrame, final String userLogged, final int indexHome, final ManagePaymentPanel managePanel,
			final HomePanel homePanel) {
		super();
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = userLogged;
		this.indexHome = indexHome;
		this.managePanel = managePanel;
		this.managePanel.attachObsverver(this);
		this.homePanel = homePanel;
	}

	@Override
	public void back() {
		final ShowHabitationPanel showView = new ShowHabitationPanel();
		try {
			showView.setHabitationsList(model.getHabitations(userLogged));
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		new ShowHabitationsController(model, mainFrame, homePanel, showView, userLogged);
		this.mainFrame.setCenterPanel(showView);

	}

	@Override
	public void addExpense() {
		final AddExpensesPanel addExpensesPanel = new AddExpensesPanel(null);
		new AddExpenseController(model, mainFrame, userLogged, indexHome, managePanel, addExpensesPanel);
		this.mainFrame.setCenterPanel(addExpensesPanel);

	}

	@Override
	public void addEarning() {
		final AddEarningPanel addEarningPanel = new AddEarningPanel(null);
		new AddEarningController(model, mainFrame, userLogged, indexHome, addEarningPanel, managePanel);
		this.mainFrame.setCenterPanel(addEarningPanel);

	}

	@Override
	public void showExpenses() {
		final ShowEarningAndExpensesBase viewBase = new ShowEarningAndExpensesBase();
		final ShowEarningAndExpenseAvanced viewAvanced = new ShowEarningAndExpenseAvanced(viewBase);

		final ShowEarningAndExpenseAvancedController controller = CreateHabitationEE.getAvancedController(model, mainFrame, userLogged, indexHome, viewAvanced, managePanel);
		controller.attachObserver();
		try {
			controller.setList(this.model.getExpenseList(this.model.getHabitation(userLogged, indexHome)));
			controller.createTable(this.model.getExpenseList(this.model.getHabitation(userLogged, indexHome)));
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		controller.showTotal();
		this.mainFrame.setCenterPanel(viewAvanced);

	}

	@Override
	public void showEarnings() {
		final ShowEarningAndExpensesBase viewBase = new ShowEarningAndExpensesBase();
		final ShowEarningAndExpenseAvanced viewAvanced = new ShowEarningAndExpenseAvanced(viewBase);

		final ShowEarningAndExpenseAvancedController controller = CreateHabitationEE.getAvancedController(model, mainFrame, userLogged, indexHome, viewAvanced, managePanel);
		controller.attachObserver();
		try {
			controller.setList(this.model.getEarningList(this.model.getHabitation(userLogged, indexHome)));
			controller.createTable(this.model.getEarningList(this.model.getHabitation(userLogged, indexHome)));
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		controller.showTotal();
		this.mainFrame.setCenterPanel(viewAvanced);
	}

	@Override
	public void showCalendar() {
		final CalendarPanel calendarPanel = new CalendarPanel();
		new CalendarController(model, mainFrame, userLogged, indexHome, managePanel, calendarPanel);
		try {
			for (final IEarningAndExpense earn : this.model.getEarningList(this.model.getHabitation(userLogged, indexHome))) {
				calendarPanel.setCellOn(earn.getDate());
			}
			for (final IEarningAndExpense exp : this.model.getExpenseList(this.model.getHabitation(userLogged, indexHome))) {
				calendarPanel.setCellOn(exp.getDate());
			}
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		this.mainFrame.setCenterPanel(calendarPanel);

	}

	@Override
	public void showReminder() {
		try {
			final ReminderPanel reminderPanel = new ReminderPanel();
			final List<IEarningAndExpense> earnings = new ArrayList<>(this.model.getEarningList(this.model.getHabitation(userLogged, indexHome)));
			final List<IEarningAndExpense> expenses = new ArrayList<>(this.model.getExpenseList(this.model.getHabitation(userLogged, indexHome)));
			new ReminderController(mainFrame, managePanel, reminderPanel).createMonthlyReminders(earnings, expenses);

			this.mainFrame.setCenterPanel(reminderPanel);

		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

	}

	@Override
	public void showStatistics() {
		final GeneralStatisticsPanel statsPanel = new GeneralStatisticsPanel();
		new HabitationStatisticsController(mainFrame, statsPanel, model, userLogged, managePanel, indexHome);
		statsPanel.startView();
		this.mainFrame.setCenterPanel(statsPanel);
	}

}
