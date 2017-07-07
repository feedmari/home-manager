package model.interfaces;

import java.util.Calendar;

import model.classes.AbstractExpenseEarning;
import model.classes.AbstractExpenseEarning.ExpenseEarningType;

/**
 * Interface for abstract class {@link AbstractExpenseEarning}.
 * 
 * @author marco mancini
 * @author federico marinelli
 *
 */
public interface IEarningAndExpense {

	/**
	 * Return the type.
	 * 
	 * @return type the type
	 */
	ExpenseEarningType getType();

	/**
	 * Return the cost.
	 * 
	 * @return cost the cost
	 */
	Double getCost();

	/**
	 * Return the date of the payment.
	 * 
	 * @return date the date
	 */
	Calendar getDate();

	/**
	 * Set the date of payment.
	 * 
	 * @param date
	 *            the date
	 */
	void setDate(Calendar date);

	/**
	 * Return true if the expense/earning is payed/received or false if the
	 * expense isn't payed/received.
	 * 
	 * @return if is payed or not
	 */
	boolean isPayed();

	/**
	 * Set if the expense/earning is payed/received or not.
	 * 
	 * @param payed set isPayed
	 */
	void setPayed(final boolean payed);

	/**
	 * Return the expense/earning description.
	 * 
	 * @return descriprion the description
	 */
	String getDescription();

	/**
	 * Set the description of the expense/earning.
	 * 
	 * @param description
	 *            the description
	 */
	void setDescription(String description);

	/**
	 * Return the earning/expense's id.
	 * 
	 * @return the id
	 */
	int getId();

	/**
	 * Set the earning/expense's ID.
	 * @param id set the id
	 */
	void setId(int id);

}
