package view.interfaces;

import java.util.Calendar;

import view.classes.CalendarPanel;
import view.classes.CalendarPanel.ICalendarObserver;

/**
 * Interface for {@link CalendarPanel}.
 * @author Marco Mancini
 *
 */
public interface ICalendarPanel {
	
	/**
	 * Attach the observer to this panel.
	 * @param observer the observer
	 */
	void attachObserver(ICalendarObserver observer);
	
	/**
	 * Set a warning if on the date is there an expense or an earning.
	 * @param date the date of expense/earning;
	 */
	void setCellOn(Calendar date);

}
