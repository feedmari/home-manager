package view.classes;
 
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.classes.MainFrameController;
import view.interfaces.IMainFrame;

/**
 * The mainframe.
 * @author marco mancini
 *
 */

public class MainFrame extends JFrame implements IMainFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5133591477759458116L;
	private static final String TITLE = "Home Manager";
	private static final int HEIGHT = 600;
	private static final int WIDTH = 480;
	private static final String ICON = "/home.jpg";
	private final JPanel north;
	private JPanel center;
	private final JButton logout;
	private final JMenuBar menuBar;
	private final JMenu optionMenu;
	private final JMenuItem[] optionMenuItems;
	private final JFileChooser fileChooser;
	private IMainFrameObserver observer;
	private final MainFrame mainFrame;
	
	/**
	 * Constructor.
	 */
	public MainFrame() {
		super(TITLE);
		
		this.mainFrame = this;
		
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setIconImage(new ImageIcon(this.getClass().getResource(ICON)).getImage());
		this.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(final WindowEvent e) {
                observer.exit();
            }
		});
		
		
		this.logout = new JButton("Log out");
		this.optionMenu = new JMenu("Opzioni");
		this.optionMenuItems = new JMenuItem[2];
		this.optionMenuItems[0] = new JMenuItem("Carica dati");
		this.optionMenuItems[1] = new JMenuItem("Salva dati");
		this.menuBar = new JMenuBar();
		this.fileChooser = new JFileChooser();
		final FileNameExtensionFilter filter = new FileNameExtensionFilter(".dat", "dat");
		this.fileChooser.setFileFilter(filter);
		this.setLogoutEnabled(false);
		
		
		
		
		
		/*this.north = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(final Graphics g) {
				final Image image = new ImageIcon(this.getClass().getResource("/background.jpg")).getImage();
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
			}
		};*/
		
		this.logout.addActionListener(new Listener());
		this.optionMenuItems[0].addActionListener(new Listener());
		this.optionMenuItems[1].addActionListener(new Listener());
		
		this.optionMenu.add(this.optionMenuItems[0]);
		this.optionMenu.add(this.optionMenuItems[1]);
		
		this.menuBar.add(this.optionMenu);
		this.setJMenuBar(this.menuBar);
		
		this.north = new JPanel();
		this.north.setLayout(new FlowLayout());
		this.north.add(logout);
		
		this.setLayout(new BorderLayout());
		
		
	}

	@Override
	public void attachObserver(final IMainFrameObserver observer) {
		this.observer = observer;
		
	}

	@Override
	public void setCenterPanel(final JPanel panel) {
		this.center = panel;
		this.getContentPane().removeAll();
		this.getContentPane().add(north, BorderLayout.NORTH);
		this.getContentPane().add(center, BorderLayout.CENTER);
		this.repaint();
		panel.updateUI();
	
		
	}

	@Override
	public JPanel getCenterPanel() {
		return this.center;
	}
	
	@Override
	public void setLogoutEnabled(final boolean set) {
		this.logout.setEnabled(set);
		
	}
	
	@Override
	public void showPaneMessage(final Component parentComponent, final Object message,
			final String title, final int messageType) {
		JOptionPane.showMessageDialog(this,
				message, title,
				messageType);
		
	}
	
	@Override
	public int showYesNoOptMessage(final Object message, final String title) {
		return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
	}
	

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			Object src = e.getSource();
			if (src == logout) {
				observer.logout();
			} else if (src  == optionMenuItems[0]) {
				if (fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
					observer.loadData(fileChooser.getSelectedFile().getPath());
				}
			} else if (src == optionMenuItems[1]) {
				if (fileChooser.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
					observer.saveData(fileChooser.getSelectedFile().getPath());
				}
			}
			
		}
		
	}

	/**
	 * Interface for {@link MainFrameController}.
	 * @author marco mancini
	 *
	 */
	public interface IMainFrameObserver {
		
		/**
		 * Control the user's logout.
		 */
		void logout();
		
		/**
		 * Creates the home panel and controller.
		 */
		void createHome();
		
		/**
		 * Set the current user to the username.
		 * @param username the username
		 * 
		 */
		void setUserLoggedIn(final String username);
		
		
		/**
		 * Creates login panel and controller.
		 */
		void createLogin();
		
		/**
		 * Save all data in a file.
		 * @param path the path
		 * 
		 */

		void saveData(final String path);

		/**
		 * load all data.
		 * @param path the path
		 */
		void loadData(final String path);
		
		
		/**
		 * Exit from the application.
		 */
		void exit();
		
	}

	
}
