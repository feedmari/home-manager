package model.classes;

import java.io.Serializable;
import java.util.Calendar;

/**
 * It models a generic Earning.
 * 
 * @author federico marinelli
 * @author marco mancini
 *
 */
public class Earning extends AbstractExpenseEarning implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4857848098743749956L;
	private final ExpenseEarningType type;
	private final double amount;
	private int id;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            the type
	 * @param amount
	 *            the amount.
	 * @param isPayed
	 *            indicates if the earning/expense is payed or not
	 * @param date
	 *            the earning date
	 * @param id
	 *            the id
	 */
	public Earning(final ExpenseEarningType type, final double amount, final boolean isPayed, final Calendar date, final int id) {
		super(isPayed, date);
		this.type = type;
		this.amount = amount;
		this.id = id;
	}

	@Override
	public ExpenseEarningType getType() {
		return this.type;
	}

	@Override
	public Double getCost() {
		return this.amount;
	}

	@Override
	public String toString() {
		return "Earning [Type=" + type + ", Amount=" + amount + "]";
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
	 * Enum for habitation's earning type.
	 * 
	 * @author Marco Mancini
	 * @author Federico Marinelli
	 *
	 */
	public static enum EarningType implements ExpenseEarningType {

		/**
		 * The type Affitto.
		 */
		AFFITTO("Affitto", "Ammontare del rientro della casa che hai affittato"), 
		/**
		 * The type Extra.
		 */
		EXTRA("Rientro Extra", "Rientro extra");

		private final String name;
		private final String description;

		private EarningType(final String name, final String description) {
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
		 * Return the earningType from the Name.
		 * @param name the name
		 * @return the earning type
		 */
		public static EarningType fromString(final String name) {
			if (name != null) {
				for (final EarningType e : EarningType.values()) {
					if (name.equalsIgnoreCase(e.getName())) {
						return e;
					}
				}
			}
			return null;
		}

	}

	/**
	 * The User's earning type.
	 * @author Marco Mancini
	 * @author Federico Marinelli
	 *
	 */
	public static enum EarningUserType implements ExpenseEarningType {

		/**
		 * The type Stipendio.
		 */
		STIPENDIO("Stipendio", "Stipendio"), 
		/**
		 * The type Extra.
		 */
		EXTRA("Rientro Extra", "Rientro extra");

		private final String name;
		private final String description;

		private EarningUserType(final String name, final String description) {
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
		 * Return the earningType from the Name.
		 * @param name the name
		 * @return the earning type
		 */
		public static EarningUserType fromString(final String name) {
			if (name != null) {
				for (final EarningUserType e : EarningUserType.values()) {
					if (name.equalsIgnoreCase(e.getName())) {
						return e;
					}
				}
			}
			return null;
		}

	}

}
