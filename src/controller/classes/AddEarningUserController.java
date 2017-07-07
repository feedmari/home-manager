package controller.classes;

import java.util.Calendar;

import javax.swing.JOptionPane;

import model.classes.Earning;
import model.classes.Earning.EarningUserType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import view.classes.AddEarningUserPanel;
import view.classes.AddEarningUserPanel.IAddEarningUserObserver;
import view.classes.MainFrame;
import view.classes.ManageUserPaymentPanel;
import exceptions.InexistentUserException;

/**
 * The controller for the {@link AddEarningUserPanel}.
 * 
 * @author Federico Marinelli
 *
 */
public class AddEarningUserController implements IAddEarningUserObserver {

	private final IModel model;
	private final MainFrame mainFrame;
	private final String userLogged;
	private final ManageUserPaymentPanel managePanel;
	private final AddEarningUserPanel addEarningPanel;

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            the model
	 * @param mainFrame
	 *            the mainframe
	 * @param userLogged
	 *            string for the logged user
	 * @param addEarningPanel
	 *            panel for add earning
	 * @param managePanel
	 *            panel for manage the user
	 */
	public AddEarningUserController(final IModel model, final MainFrame mainFrame, final String userLogged, final ManageUserPaymentPanel managePanel, final AddEarningUserPanel addEarningPanel) {
		super();
		this.model = model;
		this.mainFrame = mainFrame;
		this.userLogged = userLogged;
		this.managePanel = managePanel;
		this.addEarningPanel = addEarningPanel;
		this.addEarningPanel.attachObserver(this);
	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(managePanel);

	}

	@Override
	public void confirm(final EarningUserType type, final double amount, final boolean isPayed, final Calendar date, final String description, final IEarningAndExpense oldEarning) {
		try {
			final int ind = this.model.getEarningList(this.model.getUser(userLogged)).indexOf(oldEarning);
			if (amount != 0) {
				if (oldEarning == null) {
					final IEarningAndExpense transition = new Earning(type, amount, isPayed, date, this.model.getEarningList(this.model.getUser(userLogged)).size());
					transition.setDescription(description);
					model.addTransition(this.model.getUser(userLogged), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(managePanel);
				} else {
					final IEarningAndExpense transitionTest = new Earning(type, amount, isPayed, date, ind);
					this.model.deleteTransition(this.model.getUser(userLogged), transitionTest, ind);
					final IEarningAndExpense transition = new Earning(type, amount, isPayed, date, this.model.getUser(userLogged).getWallet().getEarningList().size());
					transition.setDescription(description);
					this.model.addTransition(this.model.getUser(userLogged), transition);
					this.mainFrame.showPaneMessage(this.mainFrame, "Importo salvato correttamente!", "Inserimento avvenuto", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(managePanel);
				}

			} else {
				this.mainFrame.showPaneMessage(this.mainFrame, "Inserire un valore maggiore di 0 nell'importo!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}
	}
}