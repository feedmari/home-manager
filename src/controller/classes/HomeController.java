package controller.classes;

import exceptions.InexistentUserException;
import model.interfaces.IModel;
import view.classes.GeneralStatisticsPanel;
import view.classes.HomePanel;
import view.classes.MainFrame;
import view.classes.ManageUserPaymentPanel;
import view.classes.ShowHabitationPanel;
import view.classes.HomePanel.IHomePanelObserver;

/**
 * The controller for {@link HomePanel}.
 * 
 * @author marco mancini
 *
 */
public class HomeController implements IHomePanelObserver {

	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final HomePanel homePanel;

	/**
	 * Constructor.
	 * 
	 * @param currentUser
	 *            the current user
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param homePanel
	 *            the home panel
	 */
	public HomeController(final String currentUser, final IModel model, final MainFrame mainFrame, final HomePanel homePanel) {
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = currentUser;
		this.homePanel = homePanel;
		this.homePanel.attachObserver(this);
	}

	@Override
	public void showHabitations() {
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
	public void manageUser() {
		final ManageUserPaymentPanel userPanel = new ManageUserPaymentPanel();
		userPanel.setWelcomeLb("Gestione dell'user: " + userLogged);
		new ManageUserController(model, mainFrame, userLogged, userPanel, homePanel);
		this.mainFrame.setCenterPanel(userPanel);
	}

	@Override
	public void showStats() {
		final GeneralStatisticsPanel statsPanel = new GeneralStatisticsPanel();
		new TotalStatisticsController(mainFrame, statsPanel, model, userLogged, homePanel);
		statsPanel.startView();
		this.mainFrame.setCenterPanel(statsPanel);

	}
}
