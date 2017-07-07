package controller.classes;

import model.interfaces.IModel;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.ShowEarningAndExpenseAvanced;

/**
 * Create and model a ShowEarningAndExpenseAvancedController for an Habitation.
 * 
 * @author Federico Marinelli
 *
 */
public final class CreateHabitationEE {

	private CreateHabitationEE() {
	};

	/**
	 * This method return a controller for {@link ShowEarningAndExpenseAvanced}.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param userLogged
	 *            the logged user
	 * @param indexHome
	 *            the home's index
	 * @param showEarningAndExpenses
	 *            the {@link ShowEarningAndExpenseAvanced}
	 * @param managePanel
	 *            the manage panel
	 * @return the controller
	 */
	public static ShowEarningAndExpenseAvancedController getAvancedController(final IModel model, final MainFrame mainFrame, final String userLogged, final int indexHome,
			final ShowEarningAndExpenseAvanced showEarningAndExpenses, final ManagePaymentPanel managePanel) {
		final ShowEarningAndExpenseAvancedController controller = new ShowEarningAndExpenseAvancedController();
		controller.setModel(model);
		controller.setMainFrame(mainFrame);
		controller.setUserLogged(userLogged);
		controller.setIndexHome(indexHome);
		controller.setShowEarningAndExpenses(showEarningAndExpenses);
		controller.setManagePanel(managePanel);
		return controller;
	}

}
