package controller.classes;

import javax.swing.JOptionPane;

import exceptions.InexistentUserException;
import model.classes.Habitation;
import model.classes.Habitation.HabitationType;
import model.interfaces.IModel;
import view.classes.AddHabitationPanel;
import view.classes.AddHabitationPanel.IAddHabitationObsersver;
import view.classes.HomePanel;
import view.classes.MainFrame;
import view.classes.ShowHabitationPanel;

/**
 * The controller for {@link AddHabitationPanel}.
 * 
 * @author marco mancini
 *
 */
public class AddHabitationController implements IAddHabitationObsersver {

	private final IModel model;
	private final AddHabitationPanel addPanel;
	private final MainFrame mainframe;
	private final HomePanel homePanel;
	private final String userLogged;

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            the model
	 * @param habitationPanel
	 *            the panel that show habitations
	 * @param addPanel
	 *            the panel used to add habitations
	 * @param mainframe
	 *            the mainframe
	 * @param userLogged
	 *            the logged user
	 * @param homePanel
	 *            the home panel
	 */
	public AddHabitationController(final IModel model, final ShowHabitationPanel habitationPanel, final AddHabitationPanel addPanel, final MainFrame mainframe,
			final String userLogged, final HomePanel homePanel) {
		this.model = model;
		this.addPanel = addPanel;
		this.mainframe = mainframe;
		this.addPanel.attachObserver(this);
		this.homePanel = homePanel;
		this.userLogged = userLogged;
	}

	@Override
	public void addHabitation(final String username, final int id, final HabitationType type, final String address, final String city, final String country) {
		if (address.length() == 0 || city.length() == 0 || country.length() == 0) {
			mainframe.showPaneMessage(this.mainframe, "Non hai compilato tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			final Habitation habitation = new Habitation(username, id, type, address, city, country);
			model.addHabitation(username, habitation);
			mainframe.showPaneMessage(this.mainframe, "Abitazione inserita correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
			final ShowHabitationPanel showView = new ShowHabitationPanel();
			try {
				showView.setHabitationsList(model.getHabitations(userLogged));
			} catch (InexistentUserException e) {
				System.err.println("Caught Exception: " + e.getMessage());
			}
			new ShowHabitationsController(model, this.mainframe, homePanel, showView, userLogged);
			this.mainframe.setCenterPanel(showView);

		}

	}

	@Override
	public void back() {
		final ShowHabitationPanel showView = new ShowHabitationPanel();
		try {
			showView.setHabitationsList(model.getHabitations(userLogged));
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
		new ShowHabitationsController(model, this.mainframe, homePanel, showView, userLogged);
		this.mainframe.setCenterPanel(showView);

	}

}
