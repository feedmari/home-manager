package view.interfaces;

import view.classes.AbstractGraphPanel.IGraphObserver;
import view.classes.PieGraphPanel;



/**
 * Interface for {@link PieGraphPanel}.
 * 
 * @author federico marinelli
 *
 */
public interface IGraphPanel {

		/**
		 * Attach the observer to this panel.
		 * 
		 * @param observer
		 *            the observer
		 */
		void attachObserver(final IGraphObserver observer);

		

		
}
