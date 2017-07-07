package view.interfaces;

import java.awt.Component;

import javax.swing.JPanel;

import view.classes.MainFrame;
import view.classes.MainFrame.IMainFrameObserver;

/**
 * Interface for {@link MainFrame}.
 * @author marco
 *
 */
public interface IMainFrame {
	
	/**
	 * Attache the observer.
	 * @param observer the observer
	 */
	void attachObserver(final IMainFrameObserver observer);
	
	/**
	 * Set the center panel (it is dynamic).
	 * @param panel the panel
	 */
	void setCenterPanel(final JPanel panel);
	
	/**
	 * Return the center panel.
	 * @return the center panel
	 */
	JPanel getCenterPanel();
	
	/**
	 * Set the logout button (enabled or not).
	 * @param set true(enabled), false (otherwise)
	 */
	void setLogoutEnabled(final boolean set);
	
	/**
	 * Show a message.
	 * @param parentComponent the component 	
	 * @param message	the message to show
	 * @param title	the title of the message	
	 * @param messageType the type of messagge
	 */
	void showPaneMessage(Component parentComponent, Object message, String title, int messageType);
	
	/**
	 * Show a Yes_No options message.
	 * @param message the message
	 * @param title the title
	 * @return the chose
	 */
	int showYesNoOptMessage(Object message, String title);
	

}
