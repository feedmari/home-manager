package controller.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import model.classes.Earning;
import model.classes.Earning.EarningType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.CalendarPanel;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.ShowEarningAndExpensesBase;
import view.classes.ShowEarningAndExpensesBase.IShowEarningAndExpensesBaseObserver;

/**
 * Controller for {@link ShowEarningAndExpensesBase}.
 * @author Marco Mancini
 *
 */
public class ShowEarningAndExpenseBaseController implements IShowEarningAndExpensesBaseObserver {
	
	private  MainFrame mainFrame;
	private  ShowEarningAndExpensesBase showEarningAndExpenses;
	private CalendarPanel calendarPanel;
	/**
	 * Return a controller for {@link ShowEarningAndExpensesBase}.
	 * @param model the model
	 * @param mainFrame the mainframe
	 * @param userLogged the logged user
	 * @param indexHome the index home
	 * @param managePanel the manage panel
	 * @param showEarningAndExpenses the panel that show earning and expenses
	 * @param calendarPanel the calendar panel
	 * @return the controller
	 */
	public static ShowEarningAndExpenseBaseController getController(final IModel model, final MainFrame mainFrame,
			final String userLogged, final int indexHome,
			final ManagePaymentPanel managePanel,
			final ShowEarningAndExpensesBase showEarningAndExpenses,
			final CalendarPanel calendarPanel) {
		final  ShowEarningAndExpenseBaseController controller = new ShowEarningAndExpenseBaseController();
		controller.mainFrame = mainFrame;
		controller.showEarningAndExpenses = showEarningAndExpenses;
		controller.calendarPanel = calendarPanel;
		
		return controller;
		
	}



	@Override
	public void back() {
		this.mainFrame.setCenterPanel(calendarPanel);
		
		
	}
	
	/**
	 * Attach the observer(this controller) to the panel.
	 */
	public void attachObserver() {
		showEarningAndExpenses.attachObserver(this);
		
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
			Object[] row = new Object[] { type, curr.getCost(),
					curr.getType().getName().equals(EarningType.EXTRA.getName()) ? "Extra: " + curr.getDescription() : curr.getType().getName(), curr.isPayed() ? "Si" : "No",
					strDate, curr.getId()};

			showEarningAndExpenses.addRow(row);
		}
	}






	
	
	

}
