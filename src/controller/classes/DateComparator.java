package controller.classes;

import java.util.Calendar;
import java.util.Comparator;


/**
 * Comparator for dates, the deafult comparator (Calendar.compareTo()), didnt work as I expected, then i done it.
 * @author Marco Mancini
 *
 */
public class DateComparator implements Comparator<Calendar> {
	
	@Override
	public int compare(final Calendar o1, final Calendar o2) {
		if (o1.get(Calendar.YEAR) > o2.get(Calendar.YEAR)) {
			return 1;
		} else if (o1.get(Calendar.YEAR) == o2.get(Calendar.YEAR) && o1.get(Calendar.MONTH) > o2.get(Calendar.MONTH)) {
			return 1;
		} else if (o1.get(Calendar.YEAR) == o2.get(Calendar.YEAR) && o1.get(Calendar.MONTH) == o2.get(Calendar.MONTH)
				&& o1.get(Calendar.DATE) > o2.get(Calendar.DATE)) {
			return 1;
		} else if (o1.get(Calendar.YEAR) == o2.get(Calendar.YEAR) && o1.get(Calendar.MONTH) == o2.get(Calendar.MONTH)
				&& o1.get(Calendar.DATE) == o2.get(Calendar.DATE)) {
			return 0;
		} else {
			return -1;
		}
	}

}
