package view.classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.classes.AddEarningUserController;
import view.interfaces.IAddEarningUserPanel;
import model.classes.Earning.EarningUserType;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IObserver;

/**
 * Panel for add an earning.
 * 
 * @author federico marinelli
 *
 */
public class AddEarningUserPanel extends AbstractEarningAndExpensesPanel implements IAddEarningUserPanel {

	private static final long serialVersionUID = -1109800807786443681L;
	private IAddEarningUserObserver observer;

	/**
	 * Constructor.
	 * 
	 * @param earningOrExpense
	 *            the earning
	 */
	public AddEarningUserPanel(final IEarningAndExpense earningOrExpense) {
		super(earningOrExpense);
	}

	@Override
	protected String[] chooseBoxStrings() {
		int count = 0;
		for (@SuppressWarnings("unused")
		final EarningUserType earning : EarningUserType.values()) {
			count++;
		}

		String[] types = new String[count];
		int i = 0;
		for (final EarningUserType earning : EarningUserType.values()) {
			types[i] = earning.getName();
			i++;
		}
		return types;
	}

	@Override
	protected void attachListenerToConfirm() {
		super.confirm.addActionListener(new Listener());
		super.back.addActionListener(new Listener());
		super.typeBox.addActionListener(new Listener());

	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			final Object src = e.getSource();
			if (src == confirm) {
				if (containsOnlyNumbers(amountFld.getText())) {
					if (typeBox.getSelectedItem().equals(EarningUserType.STIPENDIO.getName())) {
						observer.confirm(EarningUserType.STIPENDIO, Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(), dateChoser.getCalendar(), "", earnOrExp);
					} else {
						observer.confirm(EarningUserType.EXTRA, Double.parseDouble(amountFld.getText()), isPayedBox.isSelected(), dateChoser.getCalendar(), description.getText(),
								earnOrExp);
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
	public void attachObserver(final IAddEarningUserObserver observer) {
		this.observer = observer;

	}

	/**
	 * Interface for {@link AddEarningUserController}.
	 * 
	 * @author federico marinelli
	 *
	 */
	public interface IAddEarningUserObserver extends IObserver {

		/**
		 * This command confirm the Earning.
		 * 
		 * @param type
		 *            the type
		 * @param amount
		 *            the amount
		 * @param isPayed
		 *            if is payed or not
		 * @param date
		 *            the date
		 * @param description
		 *            the description
		 * @param oldEarning
		 *            null if it's a new Earning, an Earning if you have to
		 *            modify that earning
		 */
		void confirm(EarningUserType type, double amount, boolean isPayed, Calendar date, String description, IEarningAndExpense oldEarning);

	}
}
