package controller.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import exceptions.InexistentHabitationException;
import model.classes.Earning;
import model.classes.Expense;
import model.classes.Earning.EarningType;
import model.classes.Earning.EarningUserType;
import model.classes.Expense.ExpenseType;
import model.classes.Expense.ExpenseUserType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddEarningPanel;
import view.classes.AddExpensesPanel;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.ShowEarningAndExpenseAvanced;
import view.classes.ShowEarningAndExpenseAvanced.IShowEarningAndExpenseAvancedObserver;

/**
 * Controller for {@link ShowEarningAndExpenseAvanced}.
 * 
 * @author Marco Mancini
 * @author Federico Marinelli
 *
 */
public class ShowEarningAndExpenseAvancedController implements IShowEarningAndExpenseAvancedObserver {

	/**
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param userLogged
	 *            the logged user
	 * @param indexHome
	 *            the home's index
	 * @param showEarningAndExpense
	 *            the panel that show earnings/expenses
	 * @param list
	 *            the list (earnings or expenses) used from this controller
	 * @param managePanel
	 *            the manage panel
	 */
	private IModel model;
	private MainFrame mainFrame;
	private String userLogged;
	private int indexHome;
	private ShowEarningAndExpenseAvanced showEarningAndExpenses;
	private List<IEarningAndExpense> list;
	private ManagePaymentPanel managePanel;
	private List<IEarningAndExpense> lastListUsed;

	/**
	 * Return the model.
	 * 
	 * @return the model
	 */
	public IModel getModel() {
		return model;
	}

	/**
	 * Return the mainframe.
	 * 
	 * @return the mainframe.
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Return the logged user.
	 * 
	 * @return the logged user
	 */
	public String getUserLogged() {
		return userLogged;
	}

	/**
	 * Return the home's index.
	 * 
	 * @return the home's index
	 */
	public int getIndexHome() {
		return indexHome;
	}

	/**
	 * Return the panel that show earnings/expenses.
	 * 
	 * @return the panel that show earnings/expenses
	 */
	public ShowEarningAndExpenseAvanced getShowEarningAndExpenses() {
		return showEarningAndExpenses;
	}

	/**
	 * Return the list used from this controller.
	 * 
	 * @return the list
	 */
	public List<IEarningAndExpense> getList() {
		return list;
	}

	/**
	 * Return the manage panel.
	 * 
	 * @return the manage panel
	 */
	public ManagePaymentPanel getManagePanel() {
		return managePanel;
	}

	/**
	 * Set the model.
	 * 
	 * @param model
	 *            the model
	 */
	public void setModel(final IModel model) {
		this.model = model;
	}

	/**
	 * Set the mainframe.
	 * 
	 * @param mainFrame
	 *            the mainframe
	 */
	public void setMainFrame(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * Set the logged user.
	 * 
	 * @param userLogged
	 *            the logged user
	 */
	public void setUserLogged(final String userLogged) {
		this.userLogged = userLogged;
	}

	/**
	 * Set the index home.
	 * 
	 * @param indexHome
	 *            the index home
	 */
	public void setIndexHome(final int indexHome) {
		this.indexHome = indexHome;
	}

	/**
	 * Set the panel that show earnings/expenses.
	 * 
	 * @param showEarningAndExpenses
	 *            the panel that show earnings/expenses.
	 */
	public void setShowEarningAndExpenses(final ShowEarningAndExpenseAvanced showEarningAndExpenses) {
		this.showEarningAndExpenses = showEarningAndExpenses;
	}

	/**
	 * Set the manage panel.
	 * 
	 * @param managePanel
	 *            the manage panel.
	 */
	public void setManagePanel(final ManagePaymentPanel managePanel) {
		this.managePanel = managePanel;
	}

	/**
	 * Attach the observer(this controller) tho the panel.
	 */
	public void attachObserver() {
		showEarningAndExpenses.attachObserver(this);
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(managePanel);
	}

	@Override
	public void createTable(final List<IEarningAndExpense> usedList) {
		this.showEarningAndExpenses.refreshTable();
		final Iterator<IEarningAndExpense> it = usedList.iterator();
		IEarningAndExpense curr;
		final DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
		while (it.hasNext()) {
			curr = it.next();

			final Date date = curr.getDate().getTime();
			final String strDate = df.format(date);
			final String type;
			if (curr instanceof Earning) {
				type = "Ricavo";
			} else {
				type = "Spesa";
			}
			Object[] row = new Object[] {
					type,
					curr.getCost(),
					(curr.getType().getName().equals(EarningType.EXTRA.getName()) || curr.getType().getName().equals(EarningUserType.EXTRA.getName()))
							|| curr.getType().getName().equals(ExpenseUserType.EXTRA.getName()) || curr.getType().getName().equals(ExpenseType.EXTRA.getName()) ? "Extra: "
							+ curr.getDescription() : curr.getType().getName(), curr.isPayed() ? "Si" : "No", strDate, curr.getId() };

			showEarningAndExpenses.addRow(row);
		}
	}

	@Override
	public void setList(final List<IEarningAndExpense> list) {
		this.list = list;

		this.lastListUsed = new ArrayList<IEarningAndExpense>(list);
	}

	@Override
	public void showTotal() {
		/*
		 * lastChoose = "Total"; List<IEarningAndExpense> copyList; copyList =
		 * this.showForChoose("Total", 0, null);
		 */
		lastListUsed = new ArrayList<IEarningAndExpense>(list);

		this.createTable(list);

	}

	@Override
	public void showForMonth(final int month) {
		List<IEarningAndExpense> copyList;
		copyList = this.showForChoose("Month", month, null);

		lastListUsed = new ArrayList<IEarningAndExpense>(copyList);

		this.createTable(copyList);
	}

	@Override
	public void showForWeek(final Calendar date) {
		List<IEarningAndExpense> copyList;

		copyList = this.showForChoose("Week", 0, date);

		lastListUsed = new ArrayList<IEarningAndExpense>(copyList);
		this.createTable(copyList);
	}

	@Override
	public void edit(final int selected) {
		IEarningAndExpense expenseOrEarning;
		try {
			if (this.list.get(0) != null) {
				if (this.list.get(0) instanceof Expense) {
					expenseOrEarning = this.model.getExpenseList(this.model.getHabitation(userLogged, indexHome)).get(selected);
					AddExpensesPanel view = new AddExpensesPanel(expenseOrEarning);
					new AddExpenseController(model, mainFrame, userLogged, indexHome, managePanel, view);
					this.mainFrame.setCenterPanel(view);
				} else {
					expenseOrEarning = this.model.getEarningList(this.model.getHabitation(userLogged, indexHome)).get(selected);
					final AddEarningPanel view = new AddEarningPanel(expenseOrEarning);

					new AddEarningController(model, mainFrame, userLogged, indexHome, view, managePanel);
					this.mainFrame.setCenterPanel(view);
				}
			}
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}

	@Override
	public void delete(final int selected) {
		try {
			if (this.list.get(0) != null) {
				removeFromLastList(selected);
				if (this.list.get(0) instanceof Expense) {
					final Expense expDel = new Expense(ExpenseType.AFFITTO, 0, true, Calendar.getInstance(), selected);
					this.model.deleteTransition(this.model.getHabitation(userLogged, indexHome), expDel, selected);

				} else {
					final Earning earDel = new Earning(EarningType.AFFITTO, 0, true, Calendar.getInstance(), selected);
					this.model.deleteTransition(this.model.getHabitation(userLogged, indexHome), earDel, selected);
				}
				this.mainFrame.showPaneMessage(mainFrame, "Cancellazione avvenuta", "Cancellazione", JOptionPane.INFORMATION_MESSAGE);
				this.createTable(lastListUsed);
			}
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

	}

	private List<IEarningAndExpense> showForChoose(final String choose, final int month, final Calendar week) {
		List<IEarningAndExpense> returnList = null;
		final DateComparator comparator = new DateComparator();
		if (choose.equals("Total")) {
			returnList = new ArrayList<IEarningAndExpense>(list);
		} else if (choose.equals("Month")) {
			final Calendar dateCompare = Calendar.getInstance();
			final Calendar dateCompare2 = Calendar.getInstance();

			dateCompare.set(Calendar.MONTH, month);
			dateCompare.set(Calendar.DATE, 1);

			dateCompare2.set(Calendar.MONTH, month);
			dateCompare2.set(Calendar.DATE, dateCompare2.getActualMaximum(Calendar.DAY_OF_MONTH));

			returnList = list.stream().filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0)
					.collect(Collectors.toList());

		} else if (choose.equals("Week")) {
			final int DAYS_IN_A_WEEK = 7;

			final Calendar dateCompare = Calendar.getInstance();
			dateCompare.setTime(week.getTime());

			final Calendar dateCompare2 = Calendar.getInstance();
			dateCompare2.setTime(week.getTime());
			dateCompare2.add(Calendar.DATE, DAYS_IN_A_WEEK);

			returnList = list.stream().filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0)
					.collect(Collectors.toList());
		}
		return returnList;

	}

	private void removeFromLastList(final int index) {
		for (int i = 0; i < lastListUsed.size(); i++) {
			if (lastListUsed.get(i).getId() == index) {
				lastListUsed.remove(i);
			}
		}
	}

}
