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

import controller.classes.LoginController;
import model.interfaces.IObserver;
import view.interfaces.ILoginPanel;

/**
 * The login view.
 * 
 * @author marco mancini
 *
 */
public class LoginPanel extends AbstractMainPanel implements ILoginPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 4192831275809954759L;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private static final int WIDTH_LBL = 12;
	private final JButton loginBt;
	private final JButton registerBt;
	private final JLabel welcomeLb;
	private final JLabel usernameLb;
	private final JLabel pwdLb;
	private final JTextField usernameFld;
	private final JPasswordField pwdFld;
	private ILoginPanelObserver observer;

	/**
	 * Constructor.
	 */
	public LoginPanel() {
		super();

		
		
		this.loginBt = new JButton("login");
		this.registerBt = new JButton("registrazione");
		this.welcomeLb = new JLabel("Benvenuto! Registrati o effettua il login!");
		this.usernameLb = new JLabel("Username");
		this.pwdLb = new JLabel("Password");
		this.usernameFld = new JTextField(WIDTH_LBL);
		this.pwdFld = new JPasswordField(WIDTH_LBL);

		this.welcomeLb.setFont(FONT);
		this.usernameLb.setFont(FONT);
		this.pwdLb.setFont(FONT);

		final GridBagLayout centerLayout = new GridBagLayout();
		final GridBagConstraints centerConstraints = new GridBagConstraints();

		final JPanel north = new JPanel(new FlowLayout());
		final JPanel center = new JPanel(centerLayout);
		final JPanel south = new JPanel(new FlowLayout());

		north.add(welcomeLb);
		

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(usernameLb, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(usernameFld, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 0;
		center.add(pwdLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 1;
		center.add(pwdFld, centerConstraints);

		south.add(loginBt);
		south.add(registerBt);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		this.loginBt.addActionListener(new Listener());
		this.registerBt.addActionListener(new Listener());

	}

	@Override
	public void attachObserver(final ILoginPanelObserver observer) {
		this.observer = observer;

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == registerBt) {
				observer.register();
			} else if (src == loginBt) {
				observer.login(usernameFld.getText(), pwdFld.getPassword());
			}

		}
	}

	/**
	 * Interface for {@link LoginController}.
	 * 
	 * @author marco mancini
	 *
	 */
	public interface ILoginPanelObserver extends IObserver {

		/**
		 * Manage the login.
		 * 
		 * @param username
		 *            the username
		 * @param pwd
		 *            the password
		 */
		void login(final String username, final char[] pwd);

		/**
		 * Manage the signup procedure.
		 */
		void register();

	}

}
