package controller.classes;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.InexistentHabitationException;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.GeneralStatisticsPanel;
import view.classes.LineChartPanel;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.PieGraphPanel;
import view.classes.GeneralStatisticsPanel.IGeneralStatisticsObserver;

/**
 * The controller for the Habitation statistics{@link GeneralStatisticsPanel}.
 * 
 * @author Federico Marinelli
 *
 */
public class HabitationStatisticsController implements IGeneralStatisticsObserver {

	private final MainFrame mainFrame;
	private final GeneralStatisticsPanel statsPanel;
	private final IModel model;
	private final String userLogged;
	private final ManagePaymentPanel managePanel;
	private final int indexHome;

	private List<IEarningAndExpense> earningList = new LinkedList<>();
	private List<IEarningAndExpense> expenseList = new LinkedList<>();

	/**
	 * Constructor.
	 * 
	 * @param mainFrame
	 *            the mainframe
	 * @param statsPanel
	 *            the statistics Panel.
	 * @param model
	 *            the model
	 * @param userLogged
	 *            the logged user
	 * @param managePanel
	 *            the manage habitation panel
	 * @param indexHome
	 *            the home's index
	 * 
	 */
	public HabitationStatisticsController(final MainFrame mainFrame, final GeneralStatisticsPanel statsPanel, final IModel model, final String userLogged,
			final ManagePaymentPanel managePanel, final int indexHome) {
		super();
		this.mainFrame = mainFrame;
		this.model = model;
		this.managePanel = managePanel;
		this.userLogged = userLogged;
		this.statsPanel = statsPanel;
		this.indexHome = indexHome;
		this.statsPanel.attachObserver(this);
		this.initLists();
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(managePanel);

	}

	@Override
	public void showTotal() {
		try {
			this.initLists();
			Double earningBalance = new Double(0);
			for (final IEarningAndExpense e : this.model.getHabitation(userLogged, indexHome).getWallet().getEarningList()) {
				earningBalance += e.getCost();
			}
			this.statsPanel.setEarning(earningBalance);

			Double expenseBalance = new Double(0);
			for (final IEarningAndExpense e : this.model.getHabitation(userLogged, indexHome).getWallet().getExpenseList()) {
				expenseBalance += e.getCost();
			}

			this.statsPanel.setExpense(expenseBalance);

			final Double totalBalance = new Double(earningBalance - expenseBalance);
			this.statsPanel.setTotal(totalBalance);
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

		this.mainFrame.setCenterPanel(this.statsPanel);
	}

	@Override
	public void showForMonth(final int month) {
		final DateComparator comparator = new DateComparator();
		this.initLists();
		List<IEarningAndExpense> earningList2 = new LinkedList<>();
		List<IEarningAndExpense> expenseList2 = new LinkedList<>();

		final Calendar dateCompare = Calendar.getInstance();
		final Calendar dateCompare2 = Calendar.getInstance();

		dateCompare.set(Calendar.MONTH, month);
		dateCompare.set(Calendar.DATE, 1);

		dateCompare2.set(Calendar.MONTH, month);
		dateCompare2.set(Calendar.DATE, dateCompare2.getActualMaximum(Calendar.DAY_OF_MONTH));

		Double earningBalance = new Double(0);
		earningList2 = earningList.stream().filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0)
				.collect(Collectors.toList());
		for (final IEarningAndExpense e : earningList2) {
			earningBalance += e.getCost();
		}
		this.statsPanel.setEarning(earningBalance);

		Double expenseBalance = new Double(0);
		expenseList2 = expenseList.stream().filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0)
				.collect(Collectors.toList());
		for (final IEarningAndExpense e : expenseList2) {
			expenseBalance += e.getCost();
		}
		this.statsPanel.setExpense(expenseBalance);

		final Double totalBalance = new Double(earningBalance - expenseBalance);
		this.statsPanel.setTotal(totalBalance);

		this.setEarningList(earningList2);
		this.setExpenseList(expenseList2);

	}

	@Override
	public void showForWeek(final Calendar date) {
		final DateComparator comparator = new DateComparator();

		List<IEarningAndExpense> earningList2 = new LinkedList<>();
		List<IEarningAndExpense> expenseList2 = new LinkedList<>();

		final int DAYS_IN_A_WEEK = 7;

		this.initLists();

		final Calendar dateCompare = Calendar.getInstance();
		dateCompare.setTime(date.getTime());

		final Calendar dateCompare2 = Calendar.getInstance();
		dateCompare2.setTime(date.getTime());
		dateCompare2.add(Calendar.DATE, DAYS_IN_A_WEEK);

		Double earningBalance = new Double(0);
		earningList2 = earningList.stream().filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0)
				.collect(Collectors.toList());
		for (final IEarningAndExpense e : earningList2) {
			earningBalance += e.getCost();
		}
		this.statsPanel.setEarning(earningBalance);

		Double expenseBalance = new Double(0);
		expenseList2 = expenseList.stream().filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0)
				.collect(Collectors.toList());
		for (final IEarningAndExpense e : expenseList2) {
			expenseBalance += e.getCost();
		}
		this.statsPanel.setExpense(expenseBalance);
		final Double totalBalance = new Double(earningBalance - expenseBalance);
		this.statsPanel.setTotal(totalBalance);

		this.setEarningList(earningList2);
		this.setExpenseList(expenseList2);
	}

	@Override
	public void showPieGraph() {
		final PieGraphPanel pieGraph = new PieGraphPanel(earningList, expenseList);
		new PieGraphController(statsPanel, mainFrame, pieGraph);
		this.mainFrame.setCenterPanel(pieGraph);

	}

	@Override
	public void showXYGraph() {
		final LineChartPanel linePanel = new LineChartPanel(earningList, expenseList);
		new LineChartController(statsPanel, mainFrame, linePanel);
		this.mainFrame.setCenterPanel(linePanel);
	}

	private void setEarningList(final List<IEarningAndExpense> earningList) {
		this.earningList = earningList;
	}

	private void setExpenseList(final List<IEarningAndExpense> expenseList) {
		this.expenseList = expenseList;
	}

	private void initLists() {
		expenseList = new LinkedList<>();
		earningList = new LinkedList<>();
		try {
		earningList.addAll(this.model.getHabitation(userLogged, indexHome).getWallet().getEarningList());
		expenseList.addAll(this.model.getHabitation(userLogged, indexHome).getWallet().getExpenseList());
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}

}
