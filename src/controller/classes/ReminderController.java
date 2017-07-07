package controller.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import model.interfaces.IEarningAndExpense;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.ReminderPanel;
import view.classes.ReminderPanel.IReminderPanelObserver;

/**
 * Controller for {@link ReminderPanel}.
 * 
 * @author Marco Mancini
 *
 */
public class ReminderController implements IReminderPanelObserver {

	private final MainFrame mainFrame;
	private final ManagePaymentPanel managePanel;
	private final ReminderPanel reminderPanel;

	/**
	 * Constructor.
	 * 
	 * @param mainFrame
	 *            the mainframe
	 * @param managePanel
	 *            the manage Panel.
	 * @param reminderPanel
	 *            the reminder Panel
	 */
	public ReminderController(final MainFrame mainFrame, final ManagePaymentPanel managePanel, final ReminderPanel reminderPanel) {
		super();
		this.mainFrame = mainFrame;
		this.managePanel = managePanel;
		this.reminderPanel = reminderPanel;
		this.reminderPanel.attachObserver(this);
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(managePanel);

	}

	@Override
	public void createMonthlyReminders(final List<IEarningAndExpense> listEarnings, final List<IEarningAndExpense> listExpenses) {
		this.reminderPanel.refreshTables();
		List<IEarningAndExpense> copyListEarnings;
		List<IEarningAndExpense> copyListExpenses;
		final DateComparator comparator = new DateComparator();
		Iterator<IEarningAndExpense> iter;

		final Calendar dateCompare = Calendar.getInstance();
		final Calendar dateCompare2 = Calendar.getInstance();

		dateCompare.set(Calendar.DATE, 1);
		dateCompare2.set(Calendar.DATE, dateCompare2.getActualMaximum(Calendar.DAY_OF_MONTH));

		copyListEarnings = listEarnings.stream()
				.filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0 && !e.isPayed())
				.collect(Collectors.toList());

		copyListExpenses = listExpenses.stream()
				.filter(e -> comparator.compare(e.getDate(), dateCompare) >= 0 && comparator.compare(e.getDate(), dateCompare2) < 0 && !e.isPayed())
				.collect(Collectors.toList());

		iter = copyListEarnings.iterator();

		this.addRows(iter, true);

		iter = copyListExpenses.iterator();

		this.addRows(iter, false);

	}

	private void addRows(final Iterator<IEarningAndExpense> iter, final boolean earningOrExpense) {
		IEarningAndExpense curr;
		final DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
		while (iter.hasNext()) {
			curr = iter.next();
			final Date date = curr.getDate().getTime();
			final String strDate = df.format(date);
			final Object[] row = new Object[] { "<Tipo> " + curr.getType().getName() + "	 <Importo>" + curr.getCost() + "	 <Date>" + strDate };
			if (earningOrExpense) {
				this.reminderPanel.addEarningRow(row);
			} else {
				this.reminderPanel.addExpenseRow(row);
			}
		}

	}

}
