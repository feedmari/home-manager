package controller.classes;

import javax.swing.JOptionPane;

import exceptions.ExistsUserException;
import model.classes.User;
import model.interfaces.IModel;
import view.classes.LoginPanel;
import view.classes.MainFrame;
import view.classes.RegisterPanel;
import view.classes.RegisterPanel.IRegisterPanelObserver;

/**
 * The controller for {@link RegisterPanel}.
 * @author marco mancini
 *
 */
public class RegisterController implements IRegisterPanelObserver {
	private IModel model;
	private RegisterPanel registerPanel;
	private LoginPanel loginPanel;
	private MainFrame mainFrame;

	/**
	 * Constructor.
	 * @param model the model
	 * @param regPanel the registration panel
 	 * @param logPanel the login panel
	 * @param mainFrame the mainframe
	 */
	public RegisterController(final IModel model, final RegisterPanel regPanel,
			final LoginPanel logPanel, final MainFrame mainFrame) {
		this.model = model;
		this.registerPanel = regPanel;
		this.loginPanel = logPanel;
		this.mainFrame = mainFrame;
		this.registerPanel.attachObserver(this);
	}

	@Override
	public void registerUser(final String name, final String surname, final String username,
			final char[] pwd, final char[] confPwd) {
		if (name.length() == 0 || surname.length() == 0
				|| username.length() == 0 || pwd.length == 0
				|| confPwd.length == 0) {
			mainFrame.showPaneMessage(this.mainFrame, "Non hai compilato tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				if (checkPwd(pwd, confPwd)) {
					final User user = new User(name, surname, username, pwd);
					model.addUser(user);
					mainFrame.showPaneMessage(this.mainFrame, "Utente registrato correttamente!", 
							"Registrazione avvenuta", JOptionPane.INFORMATION_MESSAGE);
					this.mainFrame.setCenterPanel(loginPanel);
				} else {
					mainFrame.showPaneMessage(this.mainFrame, "Le password non corrispondono!", 
							"Errore", JOptionPane.ERROR_MESSAGE);
				}
			} catch (ExistsUserException e) {
				mainFrame.showPaneMessage(this.mainFrame, "Utente già presente!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	@Override
	public void back() {
		this.mainFrame.setCenterPanel(loginPanel);

	}

	private boolean checkPwd(final char[] pwd, final char[] confPwd) {
		if (pwd.length != confPwd.length) {
			return false;
		}
		for (int i = 0; i < pwd.length; i++) {
			if (pwd[i] != confPwd[i]) {
				return false;
			}
		}
		return true;
	}

}
