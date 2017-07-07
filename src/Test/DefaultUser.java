package Test;

import model.classes.*;

/**
 * Class that return an User as Singleton.
 * 
 * @author federico marinelli
 * 
 */
public final class DefaultUser {

	private static String name = "DefaultName";
	private static String surname = "DefaultSurname";
	private static String username = "admin";
	private static char[] pwd = new char[] { '1', '2', '3', '4' };
	private static final User SINGLETON = new User(name, surname, username, pwd);

	private DefaultUser() {
	}

	/**
	 * return the username.
	 * 
	 * @return username
	 */
	public static User getDefaultUser() {
		return SINGLETON;
	}

	/**
	 * return the password.
	 * 
	 * @return password
	 */
	public static char[] getPwd() {
		return pwd;
	}

	/**
	 * return the user name.
	 * 
	 * @return name
	 */
	public static String getName() {
		return name;
	}

	/**
	 * return the user surname.
	 * 
	 * @return surname the su rname
	 */
	public static String getSurname() {
		return surname;
	}

	/**
	 * return the username.
	 * 
	 * @return username the username
	 */
	public static String getUsername() {
		return username;
	}

}
