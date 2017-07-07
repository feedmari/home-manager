package model.classes;

import java.io.Serializable;
import java.util.Calendar;

/**
 * It models a generic Expense.
 * 
 * @author federico marinelli
 * @author marco mancini
 */
public class Expense extends AbstractExpenseEarning implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7599057514530755345L;
	private final ExpenseEarningType type;
	private final double amount;
	private int id;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            the type
	 * @param amount
	 *            the amount
	 * @param isPayed
	 *            indicates if the expense is payed or not
	 * @param date
	 *            the date
	 * @param id
	 *            the id
	 */
	public Expense(final ExpenseEarningType type, final double amount, final boolean isPayed, final Calendar date, final int id) {
		super(isPayed, date);
		this.type = type;
		this.amount = amount;
		this.id = id;
	}

	@Override
	public Double getCost() {
		return this.amount;
	}

	@Override
	public ExpenseEarningType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "Expense [Type=" + type + ", Amount=" + amount + "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(final int id) {
		this.id = id;

	}

	/**
	 * Enum for habitation's expense type.
	 * 
	 * @author Marco Mancini
	 * @author Federico marinelli
	 *
	 */
	public enum ExpenseType implements ExpenseEarningType {

		/**
		 * Bolletta della luce type.
		 */
		BOLLETTA_LUCE("Bolletta della Luce", "Bolletta"),
		/**
		 * Bolletta del gas type.
		 */
		BOLLETTA_GAS("Bolletta del gas", "Bolleta"),
		/**
		 * Bolletta dell'acqua type.
		 */
		BOLLETTA_ACQUA("Bolletta dell'acqua", "Bolletta"),
		/**
		 * Bolletta del telefono type.
		 */
		BOLLETTA_TELEFONO("Bolletta telefono(e internet)", "Bolletta"),
		/**
		 * Mutuo type.
		 */
		MUTUO("Mutuo casa", "Mutuo"),
		/**
		 * Affitto type.
		 */
		AFFITTO("Affitto casa", "Affitto"),
		/**
		 * Assicurazione type.
		 */
		ASSICURAZIONE("Assicurazione", "Assicurazione"),
		/**
		 * Spazzatura type.
		 */
		SPAZZATURA("Tassa Spazzatura", "Spazzatura"),
		/**
		 * Extra type.
		 */
		EXTRA("Spesa extra", "Extra");

		private final String name;
		private final String description;

		private ExpenseType(final String name, final String description) {
			this.name = name;
			this.description = description;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getDescription() {
			return this.description;
		}

		/**
		 * Return the type from name.
		 * 
		 * @param name
		 *            the name
		 * @return the type
		 */
		public static ExpenseType fromString(final String name) {
			if (name != null) {
				for (ExpenseType e : ExpenseType.values()) {
					if (name.equalsIgnoreCase(e.getName())) {
						return e;
					}
				}
			}
			return null;
		}
	}

	/**
	 * Enum for User's expenses types.
	 * @author Marco Mancini
	 * @author Federico Marinelli
	 *
	 */
	public enum ExpenseUserType implements ExpenseEarningType {

		/**
		 * Spese varie type.
		 */
		SPESE_VARIE("Spese Varie", "Spese varie"), 
		/**
		 * Extra type.
		 */
		EXTRA("Spesa extra", "Extra");

		private final String name;
		private final String description;

		private ExpenseUserType(final String name, final String description) {
			this.name = name;
			this.description = description;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getDescription() {
			return this.description;
		}

		/**
		 * Return the type from the type's name.
		 * @param name the name
		 * @return the type
		 */
		public static ExpenseUserType fromString(final String name) {
			if (name != null) {
				for (final ExpenseUserType e : ExpenseUserType.values()) {
					if (name.equalsIgnoreCase(e.getName())) {
						return e;
					}
				}
			}
			return null;
		}
	}

}
