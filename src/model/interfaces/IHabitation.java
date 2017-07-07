package model.interfaces;

import model.classes.Habitation;
import model.classes.Habitation.HabitationType;

/**
 * Interface for {@link Habitation}.
 * 
 * @author marco mancini
 * @author federico marinelli
 *
 */
public interface IHabitation {

	/**
	 * Returns the owner's name.
	 * 
	 * @return the owner name
	 */
	String getOwner();

	/**
	 * Return the habitation's id.
	 * 
	 * @return the habitation's id
	 */

	int getId();

	/**
	 * Returns the type of the Habitation (rent/ property). Probably replaced
	 * with an Enum class
	 * 
	 * @return the type
	 */
	HabitationType getType();

	/**
	 * Change the id of the Habitation.
	 * 
	 * @param id the id
	 */
	void setId(int id);

	/**
	 * Return the habitation's address.
	 * 
	 * @return the habitation's address
	 */
	String getAddress();

	/**
	 * Return the habitation's city.
	 * 
	 * @return the habitation's city
	 */
	String getCity();

	/**
	 * Return the habitation's country.
	 * 
	 * @return the habitation's country
	 */
	String getCountry();

}
