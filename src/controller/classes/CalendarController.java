package controller.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import exceptions.InexistentHabitationException;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.CalendarPanel;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.ShowEarningAndExpensesBase;
import view.classes.CalendarPanel.ICalendarObserver;

/**
 * Controller for {@link CalendarPanel}.
 * 
 * @author Marco Mancini
 *
 */
public class CalendarController implements ICalendarObserver {

	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final int indexHome;
	private final ManagePaymentPanel managePanel;
	private final CalendarPanel calendarPanel;

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
	 * @param calendarPanel
	 *            the calendar panel
	 */
	public CalendarController(final IModel model, final MainFrame mainFrame, final String userLogged, final int indexHome, final ManagePaymentPanel managePanel,
			final CalendarPanel calendarPanel) {
		super();
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = userLogged;
		this.indexHome = indexHome;
		this.managePanel = managePanel;
		this.calendarPanel = calendarPanel;
		this.calendarPanel.attachObserver(this);
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(managePanel);

	}

	@Override
	public void showDate(final Calendar date) {
		final List<IEarningAndExpense> earnAndExp = new ArrayList<>();
		try {
			for (final IEarningAndExpense earn : this.model.getEarningList(this.model.getHabitation(userLogged, indexHome))) {
				earnAndExp.add(earn);
			}
			for (final IEarningAndExpense exp : this.model.getExpenseList(this.model.getHabitation(userLogged, indexHome))) {
				earnAndExp.add(exp);
			}
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

		List<IEarningAndExpense> copyList = new ArrayList<>();
		copyList = earnAndExp
				.stream()
				.filter(e -> {
					if (e.getDate().get(Calendar.DATE) == date.get(Calendar.DATE) && date.get(Calendar.MONTH) == e.getDate().get(Calendar.MONTH)
							&& date.get(Calendar.YEAR) == e.getDate().get(Calendar.YEAR)) {
						return true;
					}
					return false;
				}).collect(Collectors.toList());
		if (copyList.isEmpty()) {
			this.mainFrame.showPaneMessage(this.mainFrame, "Nessuna spessa/rientro in data selezionata", "Attenzione! Nessuna Spesa/Rientro in data selezionata!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			final ShowEarningAndExpensesBase viewBase = new ShowEarningAndExpensesBase();

			final ShowEarningAndExpenseBaseController controller = ShowEarningAndExpenseBaseController.getController(model, mainFrame, userLogged, indexHome, managePanel,
					viewBase, calendarPanel);
			controller.attachObserver();
			controller.createTable(copyList);
			this.mainFrame.setCenterPanel(viewBase);
		}

	}

}
