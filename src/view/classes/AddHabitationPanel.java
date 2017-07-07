package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.classes.AddHabitationController;
import view.interfaces.IAddHabitationPanel;
import model.classes.Habitation.HabitationType;
import model.interfaces.IObserver;

/**
 * Panel for add an habitation.
 * 
 * @author marco mancini
 *
 */
public class AddHabitationPanel extends AbstractMainPanel implements IAddHabitationPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3237336338497743614L;
	private static final double WEIGHTX = 20;
	private static final int WIDTH_LBL = 12;
	private static final double WEIGHTY = 50;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private final JLabel addLb;
	private final JLabel ownerLb;
	private final JLabel idLb;
	private final JLabel typeLb;
	private final JLabel addressLb;
	private final JLabel cityLb;
	private final JLabel countryLb;
	private final JTextField ownerFld;
	private final JTextField idFld;
	private final JComboBox<String> typeBox;
	private final JTextField addressFld;
	private final JTextField cityFld;
	private final JTextField countryFld;
	private final JButton confirm;
	private final JButton back;
	private IAddHabitationObsersver observer;

	/**
	 * Constructor.
	 */
	public AddHabitationPanel() {
		super();

		String[] scelte = this.earningTypesString();

		this.addLb = new JLabel("Inserisci i campi");
		this.ownerLb = new JLabel("Proprietario");
		this.idLb = new JLabel("Id");
		this.typeLb = new JLabel("Tipo");
		this.addressLb = new JLabel("Indirizzo");
		this.cityLb = new JLabel("Citta");
		this.countryLb = new JLabel("Paese");
		this.ownerFld = new JTextField(WIDTH_LBL);
		this.idFld = new JTextField(WIDTH_LBL);
		this.typeBox = new JComboBox<String>(scelte);
		this.addressFld = new JTextField(WIDTH_LBL);
		this.cityFld = new JTextField(WIDTH_LBL);
		this.countryFld = new JTextField(WIDTH_LBL);
		this.confirm = new JButton("Conferma");
		this.back = new JButton("Indietro");

		this.ownerFld.setEditable(false);
		this.ownerFld.setBorder(BorderFactory.createEmptyBorder());
		this.idFld.setEditable(false);
		this.idFld.setBorder(BorderFactory.createEmptyBorder());

		this.back.addActionListener(new Listener());
		this.confirm.addActionListener(new Listener());

		this.addLb.setFont(FONT);
		this.ownerLb.setFont(FONT);
		this.idLb.setFont(FONT);
		this.typeLb.setFont(FONT);
		this.addressLb.setFont(FONT);
		this.cityLb.setFont(FONT);
		this.countryLb.setFont(FONT);

		GridBagLayout centerLayout = new GridBagLayout();
		GridBagConstraints centerConstraints = new GridBagConstraints();
		centerConstraints.weighty = WEIGHTY;
		centerConstraints.weightx = WEIGHTX;

		final JPanel north = new JPanel(new FlowLayout());
		final JPanel center = new JPanel(centerLayout);
		final JPanel south = new JPanel(new FlowLayout());

		north.add(addLb);

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(ownerLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 0;
		center.add(ownerFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(idLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 1;
		center.add(idFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 2;
		center.add(typeLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 2;
		center.add(typeBox, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 3;
		center.add(addressLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 3;
		center.add(addressFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 4;
		center.add(cityLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 4;
		center.add(cityFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 5;
		center.add(countryLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 5;
		center.add(countryFld, centerConstraints);

		south.add(back);
		south.add(confirm);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

	}

	@Override
	public void attachObserver(final IAddHabitationObsersver observer) {
		this.observer = observer;

	}

	@Override
	public void setOwner(final String owner) {
		this.ownerFld.setText(owner);

	}

	@Override
	public void setId(final int id) {
		this.idFld.setText(Integer.toString(id));

	}

	private String[] earningTypesString() {
		int count = 0;
		for (@SuppressWarnings("unused")
		final HabitationType habit : HabitationType.values()) {
			count++;
		}

		String[] types = new String[count];
		int i = 0;
		for (final HabitationType habit : HabitationType.values()) {
			types[i] = habit.getName();
			i++;
		}
		return types;
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			Object src = e.getSource();
			if (src == confirm) {
				if (typeBox.getSelectedItem().equals(HabitationType.PROPERTY_HOME.getName())) {
					observer.addHabitation(ownerFld.getText(), Integer.parseInt(idFld.getText()), HabitationType.PROPERTY_HOME, addressFld.getText(), cityFld.getText(),
							countryFld.getText());
				} else {
					observer.addHabitation(ownerFld.getText(), Integer.parseInt(idFld.getText()), HabitationType.RENT_HOME, addressFld.getText(), cityFld.getText(),
							countryFld.getText());
				}
			} else if (src == back) {
				observer.back();
			}
		}

	}

	/**
	 * Interface for {@link AddHabitationController}.
	 * 
	 * @author marco mancini
	 *
	 */
	public interface IAddHabitationObsersver extends IObserver {

		/**
		 * Add an habitation to the user's habitation list.
		 * 
		 * @param username
		 *            the username
		 * @param id
		 *            the habitation's id
		 * @param type
		 *            the type (property or rent)
		 * @param address
		 *            the address
		 * @param city
		 *            the city
		 * @param country
		 *            the country
		 */
		void addHabitation(final String username, final int id, final HabitationType type, final String address, final String city, final String country);

	}

}
