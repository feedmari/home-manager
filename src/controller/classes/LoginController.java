package controller.classes;

import javax.swing.JOptionPane;

import exceptions.InexistentUserException;
import model.interfaces.IModel;
import view.classes.LoginPanel;
import view.classes.LoginPanel.ILoginPanelObserver;
import view.classes.HomePanel;
import view.classes.MainFrame;
import view.classes.RegisterPanel;

/**
 * The controller for {@link LoginPanel}.
 * @author marco mancini
 * @author federico marinelli
 *
 */
public class LoginController implements ILoginPanelObserver {

	private final IModel model;
	private final LoginPanel loginPanel;
	private final MainFrame mainFrame;

	/**
	 * Constructor.
	 * @param model the model
	 * @param loginPanel the login panel
 	 * @param mainFrame the mainframe
	 */
	public LoginController(final IModel model, final LoginPanel loginPanel,
			final MainFrame mainFrame) {
		this.model = model;
		this.loginPanel = loginPanel;
		this.mainFrame = mainFrame;
		this.loginPanel.attachObserver(this);
	}

	@Override
	public void login(final String username, final char[] pwd) {
		// Effettua controlli su username e pwd e crea home view
		try {
			if (model.checkAccount(username, pwd)) {
				final HomePanel homeView = new HomePanel();
				new HomeController(username, model, mainFrame, homeView);
				homeView.setUser(username);
				this.mainFrame.setCenterPanel(homeView);
				this.mainFrame.setLogoutEnabled(true);
			} else {
				mainFrame.showPaneMessage(this.mainFrame, "Password errata!",  "Errore", JOptionPane.ERROR_MESSAGE);
			}
		} catch (InexistentUserException e) {
			mainFrame.showPaneMessage(this.mainFrame, "Il nome utente da te inserito non esite!", 
					"Errore", JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void register() {
		final RegisterPanel register = new RegisterPanel();
		new RegisterController(model, register, loginPanel, mainFrame);
		this.mainFrame.setCenterPanel(register);

	}

	@Override
	public void back() {
		throw new UnsupportedOperationException();
		
	}

}
