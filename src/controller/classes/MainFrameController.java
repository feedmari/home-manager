package controller.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import model.interfaces.IModel;
import view.classes.HomePanel;
import view.classes.LoginPanel;
import view.classes.MainFrame;
import view.classes.MainFrame.IMainFrameObserver;

/**
 * The controller for the {@link MainFrame}.
 * 
 * @author marco mancini
 *
 */
public class MainFrameController implements IMainFrameObserver {

	private static final String FILE_PATH = System.getProperty("user.home") + "/data.dat";
	private static final String EXIT = "Vuoi uscire dall'applicazione? I dati verranno salvati automaticamente";

	private IModel model;
	private String user;
	private final MainFrame mainFrame;

	/**
	 * The constructor.
	 * 
	 * @param model
	 *            the model
	 * @param frame
	 *            the mainframe
	 */
	public MainFrameController(final IModel model, final MainFrame frame) {
		this.model = model;
		this.mainFrame = frame;
		this.mainFrame.attachObserver(this);
		this.loadData(null);
	}

	@Override
	public void logout() {
		final int n = this.mainFrame.showYesNoOptMessage("Vuoi uscire?", "logout");
		if (n == JOptionPane.YES_OPTION) {
			this.createLogin();
			this.mainFrame.setLogoutEnabled(false);
		}
	}

	@Override
	public void createHome() {
		final HomePanel homeView = new HomePanel();
		new HomeController(user, model, mainFrame, homeView);
		this.mainFrame.setCenterPanel(homeView);

	}

	@Override
	public void setUserLoggedIn(final String username) {
		this.user = username;

	}

	@Override
	public void createLogin() {
		final LoginPanel loginView = new LoginPanel();
		new LoginController(this.model, loginView, this.mainFrame);
		this.mainFrame.setCenterPanel(loginView);
	}

	@Override
	public void exit() {
		final int n = this.mainFrame.showYesNoOptMessage(EXIT, "Uscire");
		if (n == JOptionPane.YES_OPTION) {
			this.saveData(null);
			System.exit(0);
		}

	}

	@Override
	public void saveData(final String path) {
		try {
			final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path == null ? FILE_PATH : path + ".dat"));
			out.writeObject(this.model);
			out.close();
		} catch (IOException ex) {
			this.mainFrame.showPaneMessage(this.mainFrame, "Errore nel salvataggio", "Errore", JOptionPane.ERROR_MESSAGE);
		}	
		
	}


	@Override
	public void loadData(final String path) {
		try {
			if (this.isPresent(path == null ? FILE_PATH : path)) {
				final ObjectInputStream in = new ObjectInputStream(new FileInputStream(path == null ? FILE_PATH : path));
				this.model = (IModel) in.readObject();
				in.close();
				this.createLogin();
				this.mainFrame.setLogoutEnabled(false);
			}
		} catch (IOException | ClassNotFoundException ex) {
			this.mainFrame.showPaneMessage(this.mainFrame, "Errore nel caricamento", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Checks if the data file exists.
	 * @param path the path to the file
	 * @return true if the file exists, false otherwise
	 */
	private boolean isPresent(final String path) {
		final File data = new File(path);
		return data.exists();
	}

}
