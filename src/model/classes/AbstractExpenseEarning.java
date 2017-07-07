package model.classes;

import java.io.Serializable;
import java.util.Calendar;

import model.interfaces.IEarningAndExpense;

/**
 * Models an idea of Expense and Earning, because they are very similar.
 * @author federico marinelli
 *
 */
public abstract class AbstractExpenseEarning implements IEarningAndExpense, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2438682019702659260L;
	private boolean isPayedVar;
	private Calendar date;
	private String description; //the description for the extra earning/expense
	
	/**
	 * Constructor.
	 * @param isPayed indicates if the earning/expense is payed or not
	 * @param date the date of earning/expense
	 */
	public AbstractExpenseEarning(final boolean isPayed, final Calendar date) {
		this.isPayedVar = isPayed;
		this.date = date;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override 
	public void setDescription(final String description) {
		this.description = description;
	}
	
	@Override
	public Calendar getDate() {
		return date;
	}
	
	@Override
	public void setDate(final Calendar date) {
		this.date = date;
	}

	@Override
	public boolean isPayed() {
		return this.isPayedVar;
	}

	@Override
	public void setPayed(final boolean payed) {
		this.isPayedVar = true;
		
	}
	
	/**
	 * Return the cost.
	 * @return the cost
	 */
	public abstract Double getCost();
	
	/**
	 * Return the type.
	 * @return the type
	 */
	public abstract ExpenseEarningType getType();
	
	/**
	 * Interface for expense/earning type.
	 * @author Federico Marinelli
	 * @author Marco Mancini
	 *
	 */
	public interface ExpenseEarningType {
		/**
		 * Return the type's name.
		 * @return the name
		 */
		String getName();
		
		/**
		 * Return the type's description.
		 * @return the description
		 */
		String getDescription();
	}
	
	
	
	
}
