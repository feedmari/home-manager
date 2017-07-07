package controller.classes;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.InexistentUserException;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import model.interfaces.ITransitionsWallet;
import view.classes.GeneralStatisticsPanel;
import view.classes.GeneralStatisticsPanel.IGeneralStatisticsObserver;
import view.classes.HomePanel;
import view.classes.LineChartPanel;
import view.classes.MainFrame;
import view.classes.PieGraphPanel;

/**
 * The controller for {@link GeneralStatisticsPanel}.
 * 
 * @author Federico Marinelli
 *
 */
public class TotalStatisticsController implements IGeneralStatisticsObserver {

	private final MainFrame mainFrame;
	private final GeneralStatisticsPanel statsPanel;
	private final IModel model;
	private final String userLogged;
	private final HomePanel homePanel;

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
	 * @param homePanel
	 *            the home panel
	 * 
	 */
	public TotalStatisticsController(final MainFrame mainFrame, final GeneralStatisticsPanel statsPanel, final IModel model, final String userLogged, final HomePanel homePanel) {
		super();
		this.mainFrame = mainFrame;
		this.model = model;
		this.homePanel = homePanel;
		this.userLogged = userLogged;
		this.statsPanel = statsPanel;
		this.statsPanel.attachObserver(this);
		this.initLists();
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(homePanel);

	}

	@Override
	public void showTotal() {
		this.initLists();
		try {
			this.statsPanel.setEarning(this.model.getWalletsManager(userLogged).getEarningBalance());
			this.statsPanel.setExpense(this.model.getWalletsManager(userLogged).getExpenseBalance());
			this.statsPanel.setTotal(this.model.getWalletsManager(userLogged).getTotalBalance());
		} catch (InexistentUserException e) {
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

		this.initLists();

		final int DAYS_IN_A_WEEK = 7;

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
			for (final ITransitionsWallet e : this.model.getWalletsManager(userLogged).getWallets()) {
				earningList.addAll(e.getEarningList());
				expenseList.addAll(e.getExpenseList());
			}
		} catch (InexistentUserException e1) {
			System.err.println("Caught Exception: " + e1.getMessage());
		}
	}

}
