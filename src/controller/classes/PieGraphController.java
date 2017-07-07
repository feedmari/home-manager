package controller.classes;

import javax.swing.JPanel;

import view.classes.AbstractGraphPanel.IGraphObserver;
import view.classes.MainFrame;
import view.classes.PieGraphPanel;


/**
 * The pie graph controller. {@link PieGraphPanel}
 * @author marinelli
 *
 */
public class PieGraphController implements IGraphObserver {

	private final JPanel caller;
	private final MainFrame mainFrame;
	private final PieGraphPanel graphPanel;
	
	/**
	 * Controller.
	 * @param caller the caller panel
	 * @param mainFrame the mainframe panel
	 * @param graphPanel the piegraph panel
	 */
	public PieGraphController(final JPanel caller, final  MainFrame mainFrame, final  PieGraphPanel graphPanel) {
		this.caller = caller;
		this.mainFrame = mainFrame;
		this.graphPanel = graphPanel;
		this.graphPanel.attachObserver(this);
	}
	
	@Override
	public void back() {
		this.mainFrame.setCenterPanel(caller);
	}

}
