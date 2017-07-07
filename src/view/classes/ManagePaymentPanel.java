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

import controller.classes.ManageHabitationsController;
import model.interfaces.IObserver;
import view.interfaces.IManagePaymentPanel;

/**
 * The panel for manage habitations.
 * 
 * @author marco mancini
 *
 */

public class ManagePaymentPanel extends AbstractMainPanel implements IManagePaymentPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6517139476651775709L;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private static final double WEIGHTX = 20;
	private static final double WEIGHTY = 50;
	private final JLabel welcomeLb;
	private final JLabel addExpenseLb;
	private final JLabel addEarningLb;
	private final JLabel showExpenseLb;
	private final JLabel showEarningLb;
	private final JLabel showStats;
	private final JLabel showCalendar;
	private final JLabel showReminder;
	private final JButton addExpenseBt;
	private final JButton addEarningBt;
	private final JButton showExpenseBt;
	private final JButton showEarningBt;
	private final JButton showStatsBt;
	private final JButton backBt;
	private final JButton showCalendarBtn;
	private final JButton reminderBtn;
	private IManagePaymentObserver observer;

	/**
	 * Constructor.
	 */
	public ManagePaymentPanel() {
		super();

		this.welcomeLb = new JLabel("");
		this.addExpenseLb = new JLabel("Aggiungi Spesa");
		this.addEarningLb = new JLabel("Aggiungi Rientro");
		this.showExpenseLb = new JLabel("Mostra Spese");
		this.showEarningLb = new JLabel("Mostra Rientri");
		this.showStats = new JLabel("Mostra Statistiche");
		this.showCalendar = new JLabel("Mostra Calendario");
		this.showReminder = new JLabel("Mostra Promemoria Mensile" + "");
		this.addExpenseBt = new JButton("Aggiungi");
		this.addEarningBt = new JButton("Aggiungi");
		this.showExpenseBt = new JButton("Mostra");
		this.showEarningBt = new JButton("Mostra");
		this.showStatsBt = new JButton("Mostra");
		this.reminderBtn = new JButton("Mostra");
		this.backBt = new JButton("Indietro");
		this.showCalendarBtn = new JButton("Mostra");

		this.welcomeLb.setFont(FONT);
		this.addEarningLb.setFont(FONT);
		this.addExpenseLb.setFont(FONT);
		this.showEarningLb.setFont(FONT);
		this.showExpenseLb.setFont(FONT);
		this.showStats.setFont(FONT);
		this.showCalendar.setFont(FONT);
		this.showReminder.setFont(FONT);

		this.addEarningBt.addActionListener(new Listener());
		this.addExpenseBt.addActionListener(new Listener());
		this.showEarningBt.addActionListener(new Listener());
		this.showExpenseBt.addActionListener(new Listener());
		this.showStatsBt.addActionListener(new Listener());
		this.backBt.addActionListener(new Listener());
		this.showCalendarBtn.addActionListener(new Listener());
		this.reminderBtn.addActionListener(new Listener());

		final GridBagLayout centerLayout = new GridBagLayout();
		final GridBagConstraints centerConstraints = new GridBagConstraints();
		centerConstraints.weighty = WEIGHTY;
		centerConstraints.weightx = WEIGHTX;

		final JPanel north = new JPanel(new FlowLayout());
		final JPanel center = new JPanel(centerLayout);
		final JPanel south = new JPanel(new FlowLayout());

		north.add(welcomeLb);

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(addExpenseLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 0;
		center.add(addExpenseBt, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(addEarningLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 1;
		center.add(addEarningBt, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 2;
		center.add(showExpenseLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 2;
		center.add(showExpenseBt, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 3;
		center.add(showEarningLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 3;
		center.add(showEarningBt, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 4;
		center.add(showStats, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 4;
		center.add(showStatsBt, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 5;
		center.add(showCalendar, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 5;
		center.add(showCalendarBtn, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 6;
		center.add(showReminder, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 6;
		center.add(reminderBtn, centerConstraints);

		south.add(backBt);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

	}
	
	
	/**
	 * Return the showCalendar lbl.
	 * @return the showCalendar Lbl
	 */
	public JLabel getShowCalendar() {
		return showCalendar;
	}

	/**
	 * Return the showCalendar button.
	 * @return the showCalendar Button
	 */
	public JButton getShowCalendarBtn() {
		return showCalendarBtn;
	}



	@Override
	public void attachObsverver(final IManagePaymentObserver observer) {
		this.observer = observer;
	}

	@Override
	public void setWelcomeLb(final String setWelcome) {
		this.welcomeLb.setText(setWelcome);

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			Object src = e.getSource();
			if (src == addExpenseBt) {
				observer.addExpense();
			} else if (src == addEarningBt) {
				observer.addEarning();
			} else if (src == backBt) {
				observer.back();
			} else if (src == showExpenseBt) {
				observer.showExpenses();
			} else if (src == showEarningBt) {
				observer.showEarnings();
			} else if (src == showCalendarBtn) {
				observer.showCalendar();
			} else if (src == reminderBtn) {
				observer.showReminder();
			} else if (src == showStatsBt) {
				observer.showStatistics();
			}

		}

	}

	/**
	 * Interface for {@link ManageHabitationsController}.
	 * 
	 * @author marco mancini
	 *
	 */
	public interface IManagePaymentObserver extends IObserver {

		/**
		 * This command show the panel that will add an expense to the home.
		 */
		void addExpense();

		/**
		 * This command show the panel that will add an earning to the home.
		 */
		void addEarning();

		/**
		 * This command show the panel that shows a table with all expenses.
		 */
		void showExpenses();

		/**
		 * This command show the panel that shows a table with all earnings.
		 */
		void showEarnings();

		/**
		 * This command will show the panel that contain the calendar.
		 */
		void showCalendar();

		/**
		 * This command will show the panel that contain the reminders.
		 */
		void showReminder();
		
		/**
		 * This command will show the panel that contain the statistics.
		 */
		void showStatistics();

	}

}
