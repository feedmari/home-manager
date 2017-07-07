package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.classes.HomeController;
import view.interfaces.IHomePanel;

/**
 * Panel for show allowed operations on homes.
 * 
 * @author marco mancini
 *
 */
public class HomePanel extends AbstractMainPanel implements IHomePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5635835670376331383L;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private static final double WEIGHTX = 20;
	private static final double WEIGHTY = 50;
	private final JLabel userLb;
	private final JLabel personalLb;
	private final JLabel statsLb;
	private final JLabel showHabitationsLb;
	private final JButton statsBt;
	private final JButton personalBt;
	private final JButton showHabitationBt;
	private IHomePanelObserver observer;

	/**
	 * Constructor.
	 */
	public HomePanel() {
		super();

		this.userLb = new JLabel("Utente: ");
		this.personalLb = new JLabel("GESTIONE SPESE PERSONALI");
		this.showHabitationsLb = new JLabel("GESTIONI SPESE ABITATIVE");
		this.personalBt = new JButton("Gestisci");
		this.showHabitationBt = new JButton("Gestisci");
		this.statsBt = new JButton("Visualizza");
		this.statsLb = new JLabel("STATISTICHE GENERALI");

		this.personalLb.setFont(FONT);
		this.userLb.setFont(FONT);
		this.showHabitationsLb.setFont(FONT);
		this.statsLb.setFont(FONT);

		this.personalBt.addActionListener(new Listener());
		this.showHabitationBt.addActionListener(new Listener());
		this.statsBt.addActionListener(new Listener());

		GridBagLayout centerLayout = new GridBagLayout();
		GridBagConstraints centerConstraints = new GridBagConstraints();
		centerConstraints.weighty = WEIGHTY;
		centerConstraints.weightx = WEIGHTX;

		final JPanel north = new JPanel(new FlowLayout());
		final JPanel center = new JPanel(centerLayout);

		north.add(userLb);

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(personalLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 0;
		center.add(personalBt, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(showHabitationsLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 1;
		center.add(showHabitationBt, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 2;
		center.add(statsLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 2;
		center.add(statsBt, centerConstraints);
		

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);

	}

	@Override
	public void attachObserver(final IHomePanelObserver observer) {
		this.observer = observer;

	}

	@Override
	public void setUser(final String username) {
		this.userLb.setText("Utente: " + username);

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			Object src = e.getSource();
			if (src == showHabitationBt) {
				observer.showHabitations();
			} else if (src == personalBt) {
				observer.manageUser();
			} else if (src == statsBt) {
				observer.showStats();
			}

		}

	}

	/**
	 * Interface for {@link HomeController}.
	 * 
	 * @author marco mancini
	 * @author federico marinelli
	 *
	 */
	public interface IHomePanelObserver {

		/**
		 * Show all habitation of the user.
		 */
		void showHabitations();

		/**
		 * Show the panel for manage user Expense/Earning.
		 * 
		 */
		void manageUser();

		/**
		 * Show the general statistics panel.
		 */
		void showStats();

	}

}
