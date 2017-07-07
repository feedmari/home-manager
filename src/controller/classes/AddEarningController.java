package controller.classes;

import java.util.Calendar;

import javax.swing.JOptionPane;

import exceptions.InexistentHabitationException;
import model.classes.Earning;
import model.classes.Earning.EarningType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddEarningPanel;
import view.classes.MainFrame;
import view.classes.AddEarningPanel.IAddEarningObserver;
import view.classes.ManagePaymentPanel;

/**
 * The controller for the {@link AddEarningPanel}.
 * 
 * @author Marco Mancini
 *
 */
public class AddEarningController implements IAddEarningObserver {
	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final int indexHome;
	private final AddEarningPanel addEarningPanel;
	private final ManagePaymentPanel manageHabitationsPanel;

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param userLogged
	 *            string for the logged user
	 * @param indexHome
	 *            home's index
	 * @param addEarningPanel
	 *            panel for add earning
	 * @param manageHabitationsPanel
	 *            panel for manage habitation
	 */
	public AddEarningController(final IModel model, final MainFrame mainFrame, final String userLogged, final int indexHome, final AddEarningPanel addEarningPanel,
			final ManagePaymentPanel manageHabitationsPanel) {
		super();
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = userLogged;
		this.indexHome = indexHome;
		this.addEarningPanel = addEarningPanel;
		this.manageHabitationsPanel = manageHabitationsPanel;
		this.addEarningPanel.attachObserver(this);
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(this.manageHabitationsPanel);

	}

	@Override
	public void confirm(final EarningType type, final double amount, final boolean isPayed, final Calendar date, final String description, final IEarningAndExpense oldEarning) {
		try {
			final int ind = this.model.getEarningList(this.model.getHabitation(userLogged, indexHome)).indexOf(oldEarning);
			if (amount != 0) {
				if (oldEarning == null) {
					final IEarningAndExpense transition = new Earning(type, amount, isPayed, date, this.model.getEarningList(this.model.getHabitation(userLogged, indexHome))
							.size());
					transition.setDescription(description);
					model.addTransition(this.model.getHabitation(userLogged, indexHome), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(manageHabitationsPanel);
				} else {
					final IEarningAndExpense transitionTest = new Earning(type, amount, isPayed, date, 0);
					this.model.deleteTransition(this.model.getHabitation(userLogged, indexHome), transitionTest, ind);
					final IEarningAndExpense transition = new Earning(type, amount, isPayed, date, this.model.getEarningList(this.model.getHabitation(userLogged, indexHome))
							.size());
					transition.setDescription(description);
					this.model.addTransition(this.model.getHabitation(userLogged, indexHome), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(manageHabitationsPanel);
				}
			} else {
				this.mainFrame.showPaneMessage(this.mainFrame, "Inserire un valore maggiore di 0 nell'importo!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		} catch (InexistentHabitationException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

	}
	


}
