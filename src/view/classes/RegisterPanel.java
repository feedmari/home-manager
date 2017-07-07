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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.classes.RegisterController;
import model.interfaces.IObserver;
import view.interfaces.IRegisterPanel;

/**
 * The register Panel.
 * 
 * @author marco mancini
 *
 */
public class RegisterPanel extends AbstractMainPanel implements IRegisterPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5374990625713341038L;
	private static final int WIDTH_LBL = 12;
	private static final double WEIGHTX = 20;
	private static final double WEIGHTY = 50;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private final JLabel registerLb;
	private final JLabel nameLb;
	private final JLabel surnameLb;
	private final JLabel usernameLb;
	private final JLabel pwdLb;
	private final JLabel confPwdLb;
	private final JTextField nameFld;
	private final JTextField surnameFld;
	private final JTextField usernameFld;
	private final JPasswordField pwdFld;
	private final JPasswordField confPwdFld;
	private final JButton register;
	private final JButton back;
	private IRegisterPanelObserver observer;

	/**
	 * Constructor.
	 */
	public RegisterPanel() {
		super();

		this.registerLb = new JLabel("Inserisci i campi");
		this.nameLb = new JLabel("Nome");
		this.surnameLb = new JLabel("Cognome");
		this.usernameLb = new JLabel("Username");
		this.pwdLb = new JLabel("Password");
		this.confPwdLb = new JLabel("Conferma password");
		this.nameFld = new JTextField(WIDTH_LBL);
		this.surnameFld = new JTextField(WIDTH_LBL);
		this.usernameFld = new JTextField(WIDTH_LBL);
		this.pwdFld = new JPasswordField(WIDTH_LBL);
		this.confPwdFld = new JPasswordField(WIDTH_LBL);
		this.register = new JButton("Registrati");
		this.back = new JButton("Indietro");

		this.register.addActionListener(new Listener());
		this.back.addActionListener(new Listener());

		this.registerLb.setFont(FONT);
		this.nameLb.setFont(FONT);
		this.surnameLb.setFont(FONT);
		this.usernameLb.setFont(FONT);
		this.pwdLb.setFont(FONT);
		this.confPwdLb.setFont(FONT);

		final GridBagLayout centerLayout = new GridBagLayout();
		final GridBagConstraints centerConstraints = new GridBagConstraints();
		centerConstraints.weighty = WEIGHTY;
		centerConstraints.weightx = WEIGHTX;

		final JPanel north = new JPanel(new FlowLayout());
		final JPanel center = new JPanel(centerLayout);
		final JPanel south = new JPanel(new FlowLayout());

		north.add(registerLb);

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(nameLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 0;
		center.add(nameFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(surnameLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 1;
		center.add(surnameFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 2;
		center.add(usernameLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 2;
		center.add(usernameFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 3;
		center.add(pwdLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 3;
		center.add(pwdFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 4;
		center.add(confPwdLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 4;
		center.add(confPwdFld, centerConstraints);

		south.add(back);
		south.add(register);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

	}

	@Override
	public void attachObserver(final IRegisterPanelObserver observer) {
		this.observer = observer;

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == register) {
				observer.registerUser(nameFld.getText(), surnameFld.getText(), usernameFld.getText(), pwdFld.getPassword(), confPwdFld.getPassword());
			} else if (src == back) {
				observer.back();
			}

		}

	}

	/**
	 * Interface for {@link RegisterController}.
	 * 
	 * @author marco mancini
	 *
	 */
	public interface IRegisterPanelObserver extends IObserver {

		/**
		 * Register a new User.
		 * 
		 * @param name
		 *            the name
		 * @param surname
		 *            the surname
		 * @param username
		 *            the username
		 * @param pwd
		 *            the password
		 * @param confPwd
		 *            the confirm of password
		 */
		void registerUser(final String name, final String surname, final String username, final char[] pwd, final char[] confPwd);

	}

}
