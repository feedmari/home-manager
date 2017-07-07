package controller.classes;

import javax.swing.JPanel;

import view.classes.AbstractGraphPanel.IGraphObserver;
import view.classes.LineChartPanel;
import view.classes.MainFrame;

/**
 * The line chart panel controller. {@link LineChartPanel}
 * 
 * @author federico marinelli
 *
 */
public class LineChartController implements IGraphObserver {

	private final JPanel caller;
	private final MainFrame mainFrame;
	private final LineChartPanel graphPanel;

	/**
	 * Constructor.
	 * 
	 * @param caller
	 *            the caller panel
	 * @param mainFrame
	 *            the mainframe
	 * @param graphPanel
	 *            the linechart panel
	 *            
	 */
	public LineChartController(final JPanel caller, final MainFrame mainFrame, final LineChartPanel graphPanel) {
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
