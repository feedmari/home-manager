package model.interfaces;

import java.util.List;

import model.classes.Habitation;
import model.classes.User;
import model.classes.WalletsManager;

/**
 * Interface for {@link User}.
 * @author marco mancini
 * @author federico marinelli
 *
 */
public interface IUser {
	
	/**
	 * Returns the Name.
	 * @return the name
	 */
	String getName();
	
	/**
	 * Returns the Surname.
	 * @return the surname
	 */
	String getSurname();

	/**
	 * Returns the username.
	 * @return the username
	 */
	String getUsername();
	
	/**
	 * Returns the password.
	 * @return the password
	 */
	char[] getPassword();
	
	/**
	 * Returns the User's habitations.
	 * @return the User's habitations
	 */
	List<Habitation> getHabitations();

	/**
	 * Return the User's walletManager.
	 * @return the user's walletManager
	 */
	WalletsManager getWalletsManager();
}
