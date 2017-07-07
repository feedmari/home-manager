package view.classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import model.classes.Earning.EarningType;
import model.classes.Expense.ExpenseType;
import model.interfaces.IEarningAndExpense;

import com.toedter.calendar.JDateChooser;

/**
 * Abstract class for earning and expenses panels.
 * 
 * @author marco mancini
 * @author federico marinelli
 * 
 */
public abstract class AbstractEarningAndExpensesPanel extends AbstractMainPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 192649268086631317L;
	private static final Font FONT = new Font("Italic", Font.BOLD, 15);
	private static final int WIDTH_LBL = 12;
	private static final double WEIGHTX = 20;
	private static final double WEIGHTY = 50;
	private final JLabel descriptionLb;
	protected final JComboBox<String> typeBox;
	protected final JTextField amountFld;
	protected final JCheckBox isPayedBox;
	protected final JDateChooser dateChoser;
	protected final JButton confirm;
	protected final JButton back;
	protected final JTextField description;
	protected IEarningAndExpense earnOrExp;

	/**
	 * Constructor.
	 * 
	 * @param earningOrExpense
	 *            the earning/expense that if is not null set values on panel's
	 *            components
	 */
	public AbstractEarningAndExpensesPanel(final IEarningAndExpense earningOrExpense) {
		super();

		final String[] scelte = chooseBoxStrings();

		final JLabel welcomeLb = new JLabel("Inserisci tutti i campi");
		final JLabel typeLb = new JLabel("Tipo : ");
		final JLabel amountLb = new JLabel("Ammontare (€): ");
		final JLabel isPayedLb = new JLabel("Pagato: (pagato o meno)");
		final JLabel dateLb = new JLabel("Data del pagamento");
		this.descriptionLb = new JLabel("Descrizione spesa extra:");
		this.typeBox = new JComboBox<String>(scelte);
		this.amountFld = new JTextField(WIDTH_LBL);
		this.isPayedBox = new JCheckBox();
		this.dateChoser = new JDateChooser();
		this.confirm = new JButton("Conferma");
		this.back = new JButton("Indietro");
		this.description = new JTextField(WIDTH_LBL);
		this.description.setDocument(new JTextFieldLimit(10));
		this.earnOrExp = earningOrExpense;

		this.dateChoser.setDate(new Date());

		welcomeLb.setFont(FONT);
		typeLb.setFont(FONT);
		amountLb.setFont(FONT);
		isPayedLb.setFont(FONT);
		dateLb.setFont(FONT);
		this.descriptionLb.setFont(FONT);

		this.amountFld.setText("0");

		this.attachListenerToConfirm();
		this.typeBox.addItemListener(new ItemChangeListener());
		this.description.setVisible(false);
		this.descriptionLb.setVisible(false);

		if (earnOrExp != null) {
			typeBox.setSelectedItem(earningOrExpense.getType().getName());
			amountFld.setText(earningOrExpense.getCost().toString());
			isPayedBox.setSelected(earningOrExpense.isPayed());
			dateChoser.setDate(earningOrExpense.getDate().getTime());

		}

		final GridBagLayout centerLayout = new GridBagLayout();
		final GridBagConstraints centerConstraints = new GridBagConstraints();
		centerConstraints.weighty = WEIGHTY;
		centerConstraints.weightx = WEIGHTX;

		final JPanel north = new JPanel(new FlowLayout());
		final JPanel center = new JPanel(centerLayout);
		final JPanel south = new JPanel(new FlowLayout());

		north.add(welcomeLb);

		centerConstraints.gridx = 0;
		centerConstraints.gridy = 0;
		center.add(typeLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 0;
		center.add(typeBox, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 1;
		center.add(descriptionLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 1;
		center.add(description, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 2;
		center.add(amountLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 2;
		center.add(amountFld, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 3;
		center.add(isPayedLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 3;
		center.add(isPayedBox, centerConstraints);
		centerConstraints.gridx = 0;
		centerConstraints.gridy = 4;
		center.add(dateLb, centerConstraints);
		centerConstraints.gridx = 1;
		centerConstraints.gridy = 4;
		center.add(dateChoser, centerConstraints);

		south.add(back);
		south.add(confirm);

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

	}

	/**
	 * Set the descriptionLbl.
	 * 
	 * @param bool
	 *            if true set descriprion and descriptionlbl visible, otherwise
	 *            not
	 */
	public void setDescription(final boolean bool) {
		this.description.setVisible(bool);
		this.descriptionLb.setVisible(bool);
	}

	/**
	 * This method check if a string contains only numbers or not.
	 * @param str the string
	 * @return if a string contains only numbers or not
	 */
	protected boolean containsOnlyNumbers(final String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * This method return a string's vector that contain all the string to put.
	 * in the typebox
	 * 
	 * @return a string's vector that contain all the string to put in the
	 *         typebox
	 * 
	 */
	protected abstract String[] chooseBoxStrings();

	/**
	 * This method will attach the listener to the confirm's button.
	 */
	protected abstract void attachListenerToConfirm();

	/**
	 * This class is an utility for set the maximun input characters into a
	 * JTextField.
	 * 
	 * @author marinelli federico
	 *
	 */
	public class JTextFieldLimit extends PlainDocument {

		private static final long serialVersionUID = 1669773632714963651L;
		private final int limit;

		JTextFieldLimit(final int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(final int offset, final String str, final AttributeSet attr) throws BadLocationException {
			if (str == null) {
				return;
			}

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

	/**
	 * This class is a listener for a JComponent.
	 * 
	 * @author marinelli federico
	 *
	 */
	class ItemChangeListener implements ItemListener {
		@Override
		public void itemStateChanged(final ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				final Object item = event.getItem();
				if (item.equals(EarningType.EXTRA.getName()) || item.equals(ExpenseType.EXTRA.getName())) {
					setDescription(true);
				} else {
					setDescription(false);
				}
			}
		}
	}

}
