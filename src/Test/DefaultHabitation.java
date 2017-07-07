package Test;

import model.classes.Habitation;
import model.classes.Habitation.HabitationType;
import model.classes.User;
import Test.DefaultUser;

/**
 * Class the return a default habitation as singleton.
 * @author marinelli federico
 *
 */
public final class DefaultHabitation {

	private static User user = DefaultUser.getDefaultUser();
	private static int id = (int) (Math.random() * 1000 + 1);
	private static HabitationType type = HabitationType.PROPERTY_HOME;
	private static final Habitation SINGLETON = new Habitation(user.getUsername(), id, type, "Via", "a", "a");
	
	/**
	 * Constructor.
	 */
	private DefaultHabitation() { }
	
	/**
	 * Return a new Habitation as singleton.
	 * @return singleton
	 */
	public static Habitation getDefaultHabitation() {
		return SINGLETON;
	}

	/**
	 * Return the username.
	 * @return username
	 */
	public static String getUsername() {
		return user.getUsername();
	}

	/**
	 * return the habitation id.
	 * @return id habitation id
	 */
	public static int getId() {
		return id;
	}

	/**
	 * return the habitation type.
	 * @return type the habitation type
	 */
	public static HabitationType getType() {
		return type;
	}
	
	
}
