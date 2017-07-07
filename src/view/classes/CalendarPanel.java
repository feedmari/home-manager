package view.classes;

import javax.swing.*;
import javax.swing.table.*;

import model.interfaces.IObserver;
import view.interfaces.ICalendarPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Calendar panel (Token from internet, but i added possibility of choose a date
 * and check if in that date are there expenses/earnings and watch these).
 * 
 * @author Marco Mancini
 * @author \S http://www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer
 *         -application/
 *
 */
public class CalendarPanel extends AbstractMainPanel implements ICalendarPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int DAYS_WEEK = 7;

	private static JLabel lblMonth, lblYear, lblRed, lblBlu;

	private static JButton btnPrev, btnNext, backBtn, showBtn;

	private static JTable tblCalendar;

	private static DefaultTableModel mtblCalendar;

	private static JScrollPane stblCalendar;

	private static int realYear, realMonth, realDay, currentYear, currentMonth, currentSelected;

	private static JComboBox<String> cmbYear;

	private ICalendarObserver observer;

	private static List<Calendar> datesOn = new ArrayList<Calendar>();

	/**
	 * Constructor.
	 */
	public CalendarPanel() {

		setLayout(null);

		createControls();

		setBorder();

		registerActionListeners();

		addControls();

		setBounds();

		setDate();

		addHeaders();

		setBackground();

		setTableProperties();

		populateTable();

		refreshCalendar(realMonth, realYear);

	}

	private void createControls() {

		lblMonth = new JLabel("January");

		lblYear = new JLabel("Change year:");

		lblRed = new JLabel("Rosso = Presenti spese/rientri");

		lblBlu = new JLabel("Blu = Selezionato");

		cmbYear = new JComboBox<String>();

		btnPrev = new JButton("<");

		btnNext = new JButton(">");

		backBtn = new JButton("Indietro");

		showBtn = new JButton("Mostra");

		mtblCalendar = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(final int rowIndex, final int mColIndex) {

				return false;

			}

		};

		tblCalendar = new JTable(mtblCalendar);

		stblCalendar = new JScrollPane(tblCalendar);

	}

	private void setBorder() {

		this.setBorder(BorderFactory.createTitledBorder("Calendario"));

	}

	private void registerActionListeners() {

		btnPrev.addActionListener(new btnPrev_Action());

		btnNext.addActionListener(new btnNext_Action());

		showBtn.addActionListener(new Listener());

		cmbYear.addActionListener(new cmbYear_Action());

		backBtn.addActionListener(new Listener());

	}

	private void addControls() {

		this.add(lblMonth);

		this.add(lblYear);

		this.add(lblRed);

		this.add(lblBlu);

		this.add(showBtn);

		this.add(cmbYear);

		this.add(btnPrev);

		this.add(btnNext);

		this.add(stblCalendar);

		this.add(backBtn);

	}

	private void setBounds() {

		lblMonth.setBounds(20, 25, 100, 25);

		lblYear.setBounds(10, 305, 80, 20);

		showBtn.setBounds(190, 305, 100, 25);

		cmbYear.setBounds(380, 305, 80, 20);

		lblRed.setBounds(10, 340, 200, 25);

		lblBlu.setBounds(10, 360, 200, 25);

		btnPrev.setBounds(10, 25, 50, 25);

		btnNext.setBounds(410, 25, 50, 25);

		backBtn.setBounds(200, 440, 100, 25);

		stblCalendar.setBounds(10, 50, 450, 250);

	}

	private void setDate() {

		GregorianCalendar cal = new GregorianCalendar(); // Create calendar

		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day

		realMonth = cal.get(GregorianCalendar.MONTH); // Get month

		realYear = cal.get(GregorianCalendar.YEAR); // Get year

		currentMonth = realMonth; // Match month and year

		currentYear = realYear;

	}

	private void addHeaders() {

		String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

		for (int i = 0; i < DAYS_WEEK; i++) {

			mtblCalendar.addColumn(headers[i]);

		}

	}

	private void setBackground() {

		tblCalendar.getParent().setBackground(tblCalendar.getBackground());

	}

	private void setTableProperties() {

		// No resize/reorder

		tblCalendar.getTableHeader().setResizingAllowed(false);

		tblCalendar.getTableHeader().setReorderingAllowed(false);

		// Single cell selection

		tblCalendar.setColumnSelectionAllowed(true);

		tblCalendar.setRowSelectionAllowed(true);

		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Set row/column count

		tblCalendar.setRowHeight(38);

		mtblCalendar.setColumnCount(DAYS_WEEK);

		mtblCalendar.setRowCount(6);

	}

	private void populateTable() {

		for (int i = realYear - 100; i <= realYear + 100; i++) {

			cmbYear.addItem(String.valueOf(i));

		}

	}

	/**
	 * Refresh the calendar.
	 * @param month the month
	 * @param year the year
	 */
	public static void refreshCalendar(final int month, final int year) {

		// Variables

		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

		int nod, som; // Number Of Days, Start Of Month

		// Allow/disallow buttons

		btnPrev.setEnabled(true);

		btnNext.setEnabled(true);

		if (month == 0 && year <= realYear - 10) {

			btnPrev.setEnabled(false);

		} // Too early

		if (month == 11 && year >= realYear + 100) {

			btnNext.setEnabled(false);

		} // Too late

		lblMonth.setText(months[month]); // Refresh the month label (at the top)

		lblMonth.setBounds(100, 25, 100, 25); // Re-align label with calendar
		lblMonth.setOpaque(true);

		// Select the correct year in the combo box

		cmbYear.setSelectedItem(String.valueOf(year));

		// Clear table

		for (int i = 0; i < 6; i++) {

			for (int j = 0; j < DAYS_WEEK; j++) {

				mtblCalendar.setValueAt(null, i, j);

			}

		}

		// Get first day of month and number of days

		GregorianCalendar cal = new GregorianCalendar(year, month, 1);

		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Draw calendar

		for (int i = 1; i <= nod; i++) {

			int row = new Integer((i + som - 2) / DAYS_WEEK);

			int column = (i + som - 2) % DAYS_WEEK;

			mtblCalendar.setValueAt(i, row, column);

		}

		// Apply renderers

		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());

	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean selected, final boolean focused, final int row, final int column) {

			super.getTableCellRendererComponent(table, value, selected, focused, row, column);

			if (column == 0 || column == 6) { // Week-end

				setBackground(new Color(255, 220, 220));

			}

			else { // Week

				setBackground(new Color(255, 255, 255));

			}

			if (value != null) {

				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear) { // Today

					setBackground(new Color(220, 220, 255));

				}

			}

			if (value != null) {
				Calendar date = Calendar.getInstance();

				date.set(currentYear, currentMonth, Integer.parseInt(value.toString()));

				for (Calendar dateCheck : datesOn) {
					if (date.get(Calendar.DATE) == dateCheck.get(Calendar.DATE) && date.get(Calendar.MONTH) == dateCheck.get(Calendar.MONTH)
							&& date.get(Calendar.YEAR) == dateCheck.get(Calendar.YEAR)) {
						setBackground(Color.RED);
					}
				}

				if (selected) {

					setBackground(Color.BLUE);
					currentSelected = Integer.parseInt(value.toString());

				}
			}

			setBorder(null);

			setForeground(Color.black);

			return this;

		}

	}

	static class btnPrev_Action implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (currentMonth == 0) { // Back one year

				currentMonth = 11;

				currentYear -= 1;

			}

			else { // Back one month

				currentMonth -= 1;

			}

			refreshCalendar(currentMonth, currentYear);

		}

	}

	static class btnNext_Action implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (currentMonth == 11) { // Forward one year

				currentMonth = 0;

				currentYear += 1;

			}

			else { // Forward one month

				currentMonth += 1;

			}

			refreshCalendar(currentMonth, currentYear);

		}

	}

	static class cmbYear_Action implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (cmbYear.getSelectedItem() != null) {

				String b = cmbYear.getSelectedItem().toString();

				currentYear = Integer.parseInt(b);

				refreshCalendar(currentMonth, currentYear);

			}

		}

	}

	@Override
	public void setCellOn(final Calendar date) {
		Calendar dateToIns = Calendar.getInstance();
		dateToIns.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
		CalendarPanel.datesOn.add(dateToIns);
		// refreshCalendar(currentMonth, currentYear);

	}

	@Override
	public void attachObserver(final ICalendarObserver observer) {
		this.observer = observer;

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == backBtn) {
				observer.back();
			} else if (src == showBtn) {
				Calendar date = Calendar.getInstance();
				date.set(currentYear, currentMonth, currentSelected);
				observer.showDate(date);
			}

		}

	}

	/**
	 * Interface for {@link CalendarPanel}.
	 * @author Marco Mancini
	 *
	 */
	public interface ICalendarObserver extends IObserver {

		/**
		 * Show earning/expense's in selected date.
		 * @param date the date
		 */
		void showDate(Calendar date);

	}

}
