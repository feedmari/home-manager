package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import controller.classes.ShowHabitationsController;
import view.interfaces.IShowHabitationsPanel;
import model.classes.Habitation;
import model.interfaces.IObserver;

/**
 * The panel that show habitations.
 * 
 * @author marco mancini
 *
 */
public class ShowHabitationPanel extends AbstractMainPanel implements IShowHabitationsPanel {

	/**
         *
         */
	private static final long serialVersionUID = -1591778404965693138L;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private JList<String> habitationsList;
	private final JLabel welcomeLb;
	private final JButton back;
	private final JButton confirm;
	private final JButton addHabitation;
	private final JButton delete;
	private IShowHabitationsObserver observer;
	private final JPanel center;

	/**
	 * Constructor.
	 */
	public ShowHabitationPanel() {
		super();

		this.welcomeLb = new JLabel("Lista delle abitazioni");
		this.back = new JButton("Indietro");
		this.confirm = new JButton("Seleziona");
		this.delete = new JButton("Cancella");
		this.addHabitation = new JButton("Aggiungi");

		this.back.addActionListener(new Listener());
		this.confirm.addActionListener(new Listener());
		this.delete.addActionListener(new Listener());
		this.addHabitation.addActionListener(new Listener());

		final JPanel north = new JPanel(new FlowLayout());
		north.add(welcomeLb);

		center = new JPanel(new BorderLayout());

		final JPanel south = new JPanel(new FlowLayout());
		south.add(back);
		south.add(delete);
		south.add(addHabitation);
		south.add(confirm);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
	}

	@Override
	public void attachObserver(final IShowHabitationsObserver observer) {
		this.observer = observer;

	}

	@Override
	public void setHabitationsList(final List<Habitation> list) {
		String[] habitations = new String[list.size()];
		String hab = new String();
		int i = 0;
		for (final Habitation habitation : list) {
			hab = "Id: " + habitation.getId() + " - Via: " + habitation.getAddress() + " - Città: " + habitation.getCity() + " - Paese: " + habitation.getCountry() + " - Saldo: "
					+ habitation.getWallet().getCurrentBalance();
			habitations[i] = hab;
			i++;
		}
		this.habitationsList = new JList<>(habitations);
		habitationsList.setSelectedIndex(0);
		habitationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addElementsToPanel();

	}

	private void addElementsToPanel() {

		final JScrollPane scroll = new JScrollPane(habitationsList);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setFont(FONT);
		center.add(scroll);
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == confirm) {
				try {
					observer.confirm(habitationsList.getSelectedIndex());
				} catch (IndexOutOfBoundsException exc) {
					JOptionPane.showMessageDialog(center, "Selezionare una riga della tabella!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			} else if (src == back) {
				observer.back();
			} else if (src == delete) {
				try {
					observer.delete(habitationsList.getSelectedIndex());
				} catch (IndexOutOfBoundsException exc) {
					JOptionPane.showMessageDialog(center, "Selezionare una riga della tabella!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			} else if (src == addHabitation) {
				observer.addHabitation();
			}

		}

	}

	/**
	 * Interface for {@link ShowHabitationsController}.
	 * 
	 * @author marco mancini
	 *
	 */
	public interface IShowHabitationsObserver extends IObserver {

		/**
		 * This command will create the panel to manage the relative home.
		 *
		 * @param index
		 *            index of the habitation
		 */
		void confirm(int index);

		/**
		 * This command will delete the selected habitation.
		 * 
		 * @param index
		 *            index of habitation
		 */
		void delete(int index);

		/**
		 * Add an habitation to the user's habitations.
		 * 
		 * 
		 */
		void addHabitation();

	}

}