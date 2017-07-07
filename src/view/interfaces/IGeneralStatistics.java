package view.interfaces;


import view.classes.GeneralStatisticsPanel;
import view.classes.GeneralStatisticsPanel.IGeneralStatisticsObserver;


/**
 * Interface for {@link GeneralStatisticsPanel}.
 * @author federico marinelli
 *
 */
public interface IGeneralStatistics {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(IGeneralStatisticsObserver observer);

}