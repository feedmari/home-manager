package main;

import controller.classes.MainFrameController;
import view.classes.MainFrame;
import model.classes.Model;

/**
 * The main, it creates model,view, controller and starts the application.
 * @author marco mancini
 * @author federico marinelli
 *
 */
public final class Main {
	
	private Main() { };
	
	/**
	 * The main.
	 * @param args arguments
	 */
	public static void main(final String[] args) {
		final Model model = new Model();
		final MainFrame view = new MainFrame();
		final MainFrameController controller = new MainFrameController(model, view);
		controller.createLogin();
		view.setVisible(true);
	}

}
