package Test;

import static org.junit.Assert.*;
import model.classes.Model;
import model.classes.User;
import model.interfaces.IModel;
import exceptions.ExistsUserException;
import exceptions.InexistentUserException;

/**
 * 
 * Test an User of the model
 * 
 * @author Federico Marinelli
 *
 */
public class UserTest {

	@org.junit.Test
	public void test() throws Exception {

		/*
		 * TESTING THE USER CREATION
		 */
		User userTest = DefaultUser.getDefaultUser();
		assertEquals(userTest.getName(), DefaultUser.getName());
		assertEquals(userTest.getSurname(), DefaultUser.getSurname());
		assertEquals(userTest.getPassword(), DefaultUser.getPwd());
		assertEquals(userTest.getUsername(), DefaultUser.getUsername());

		/*
		 * TESTING USER ON THE MODEL
		 */
		IModel model = new Model();
		model.addUser(userTest);

		// Try exception ExistsUserException
		try {
			model.addUser(DefaultUser.getDefaultUser());
		} catch (ExistsUserException e) {
		}

		// Try method getUser
		assertEquals(model.getUser(DefaultUser.getUsername()), DefaultUser.getDefaultUser());

		// Try exception InexistentUserException

		try {
			model.getUser("UsernameInesistente");
		} catch (InexistentUserException e) {
		}

		// Try method checkAccount of model
		assertTrue(model.checkAccount(DefaultUser.getUsername(), DefaultUser.getPwd()));

	}
}
