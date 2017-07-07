package view.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.classes.AddEarningController;
import view.interfaces.IAddEarningPanel;
import model.classes.Earning.EarningType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;

/**
 * Panel for add an earning.
 * 
 * @author marco mancini
 *
 */
public class AddEarningPanel extends AbstractEarningAndExpensesPanel implements IAddEarningPanel {

	private static final long serialVersionUID = -1109800807786443681L;
	private IAddEarningObserver observer;

	/**
	 * Constructor.
	 * 
	 * @param earningOrExpense
	 *            the earning
	 */
	public AddEarningPanel(final IEarningAndExpense earningOrExpense) {
		super(earningOrExpense);

	}

	@Override
	protected String[] chooseBoxStrings() {
		int count = 0;
		for (@SuppressWarnings("unused")
		final EarningType earning : EarningType.values()) {
			count++;
		}

		String[] types = new String[count];
		int i = 0;
		for (final EarningType earning : EarningType.values()) {
			types[i] = earning.getName();
			i++;
		}
		return types;
	}

	@Override
	protected void attachListenerToConfirm() {
		super.confirm.addActionListener(new Listener());
		super.back.addActionListener(new Listener());
		// super.typeBox.addActionListener(new Listener());

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == confirm) {
				if (containsOnlyNumbers(amountFld.getText())) {
					if (typeBox.getSelectedItem().equals(EarningType.EXTRA.getName())) {
						observer.confirm(EarningType.EXTRA, Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(), dateChoser.getCalendar(), description.getText(),
								earnOrExp);
					} else {
						observer.confirm(EarningType.fromString(typeBox.getSelectedItem().toString()), Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(),
								dateChoser.getCalendar(), "", earnOrExp);
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Il valore dell'importo inserito non è corretto!", "Errore!", JOptionPane.ERROR_MESSAGE);
				}
			} else if (src == back) {
				observer.back();
			}

		}

	}

	@Override
	public void attachObserver(final IAddEarningObserver observer) {
		this.observer = observer;

	}

	/**
	 * Interface for {@link AddEarningController}.
	 * 
	 * @author marco mancini
	 *
	 */
	public interface IAddEarningObserver extends IObserver {

		/**
		 * This command confirm the Earning.
		 * 
		 * @param extra
		 *            the extra
		 * @param amount
		 *            the amount
		 * @param isPayed
		 *            if is payed or not
		 * @param date
		 *            the date
		 * @param description
		 *            the descriptio
		 * @param oldEarning
		 *            null if it's a new Earning, an Earning if you have to
		 *            modify that earning
		 */
		void confirm(EarningType extra, double amount, boolean isPayed, Calendar date, String description, IEarningAndExpense oldEarning);

	}

}
