package controller.classes;

import javax.swing.JOptionPane;

import exceptions.InexistentHabitationException;
import exceptions.InexistentUserException;
import model.interfaces.IModel;
import view.classes.AddHabitationPanel;
import view.classes.HomePanel;
import view.classes.MainFrame;
import view.classes.ManagePaymentPanel;
import view.classes.ShowHabitationPanel;
import view.classes.ShowHabitationPanel.IShowHabitationsObserver;

/**
 * The controller for {@link ShowHabitationPanel}.
 * 
 * @author marco mancini
 *
 */
public class ShowHabitationsController implements IShowHabitationsObserver {
	private final IModel model;
	private final MainFrame mainFrame;
	private final HomePanel homePanel;
	private final ShowHabitationPanel showPanel;
	private final String userLogged;

	/**
	 * The constructor.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param homePanel
	 *            the home panel
	 * @param showPanel
	 *            the panel that show habitations
	 * @param userLogged
	 *            the logged user
	 */
	public ShowHabitationsController(final IModel model, final MainFrame mainFrame, final HomePanel homePanel, final ShowHabitationPanel showPanel, final String userLogged) {
		this.model = model;
		this.mainFrame = mainFrame;
		this.homePanel = homePanel;
		this.showPanel = showPanel;
		this.showPanel.attachObserver(this);
		this.userLogged = userLogged;
	}

	@Override
	public void confirm(final int index) {
		final ManagePaymentPanel habitationsPanel = new ManagePaymentPanel();
		try {
			habitationsPanel.setWelcomeLb("Via:" + model.getHabitation(userLogged, index).getAddress() + " Tipo:" + model.getHabitation(userLogged, index).getType().getName());
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		new ManageHabitationsController(model, mainFrame, userLogged, index, habitationsPanel, homePanel);
		this.mainFrame.setCenterPanel(habitationsPanel);

	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(homePanel);
	}

	@Override
	public void delete(final int index) {
		final int n = this.mainFrame.showYesNoOptMessage("Sei sicuro di voler cancellare l'abitazione?", "Cancellazione");
		try {
		if (n == JOptionPane.YES_OPTION) {
				model.deleteHabitation(userLogged, index);
			final ShowHabitationPanel showView = new ShowHabitationPanel();
				showView.setHabitationsList(model.getHabitations(userLogged));
			new ShowHabitationsController(model, mainFrame, homePanel, showView, userLogged);
			this.mainFrame.showPaneMessage(this.mainFrame, "Cancellazione avvenuta correttamente", "Cancellazione Avvenuta", JOptionPane.INFORMATION_MESSAGE);
			this.mainFrame.setCenterPanel(showView);
		}
		} catch (InexistentHabitationException  | InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

	}

	@Override
	public void addHabitation() {
		final AddHabitationPanel addView = new AddHabitationPanel();
		addView.setOwner(userLogged);
		try {
			addView.setId(model.getHabitations(userLogged).size());
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		new AddHabitationController(model, showPanel, addView, this.mainFrame, this.userLogged, this.homePanel);
		this.mainFrame.setCenterPanel(addView);
	}

}
