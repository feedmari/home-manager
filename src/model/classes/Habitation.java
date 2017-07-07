package model.classes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import model.interfaces.IHabitation;
import model.interfaces.IWallet;

/**
 * It models a generic Habitation.
 * 
 * @author marco mancini
 * @author federico marinelli
 *
 */
public class Habitation implements IHabitation, Serializable, IWallet {

	private static final long serialVersionUID = 3957389118223783042L;

	private final String owner;
	private int id;
	private final HabitationType type;
	private final String address;
	private final String city;
	private final String country;
	private final Wallet wallet;

	/**
	 * Constructor.
	 * @param userLogged the logged user
	 * @param id the id
	 * @param type the type
 	 * @param address the address
	 * @param city the city
 	 * @param country the country
	 */
	public Habitation(final String userLogged, final int id, final HabitationType type, final String address, final String city, final String country) {
		this.owner = userLogged;
		this.id = id;
		this.type = type;
		this.address = address;
		this.wallet = new Wallet();
		this.city = city;
		this.country = country;
	}

	/**
	 * Constructor.
	 * @param habitation the Habitation
	 */
	public Habitation(final Habitation habitation) {
		this.id = habitation.getId();
		this.owner = habitation.getOwner();
		this.type = habitation.getType();
		this.address = habitation.getAddress();
		this.wallet = habitation.getWallet();
		this.city = habitation.getCity();
		this.country = habitation.getCountry();

	}

	@Override
	public String getCity() {
		return this.city;
	}

	@Override
	public String getCountry() {
		return this.country;
	}

	@Override
	public String getOwner() {
		return this.owner;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public HabitationType getType() {
		return this.type;
	}

	@Override
	public String getAddress() {
		return this.address;
	}

	@Override
	public Wallet getWallet() {
		return this.wallet;
	}

	/**
	 * Enum for Habitation.
	 * @author Marco Mancini
	 * @author Federico Marinelli
	 *
	 */
	public static enum HabitationType {
		/**
		 * Di proprietà.
		 */
		PROPERTY_HOME("Di proprietà"), 
		/**
		 * In affitto.
		 */
		RENT_HOME("In affitto");

		private final String name;

		private HabitationType(final String name) {
			this.name = name;
		}

		/**
		 * Return the type's name.
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Return the type's list.
		 * @return the type's list
		 */
		public List<HabitationType> getListTypes() {
			return Arrays.asList(HabitationType.values());
		}
	}

}
