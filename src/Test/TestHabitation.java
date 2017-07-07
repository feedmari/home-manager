package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import exceptions.InexistentHabitationException;
import model.classes.Habitation;
import model.classes.Model;

/**
 * Testing the model.
 * @author marinelli federico
 *
 */
public class TestHabitation {

	@org.junit.Test
	public void testHabitationCreation() throws Exception {
		
		/*
		 * TESTING THE HABITATION CREATION
		 */
		Habitation habtitationTest = DefaultHabitation.getDefaultHabitation();
		assertEquals(habtitationTest.getId(), DefaultHabitation.getId());
		assertEquals(habtitationTest.getOwner(), DefaultHabitation.getUsername());
		assertEquals(habtitationTest.getType(), DefaultHabitation.getType());
		
		/*
		 * TESTING THE HABITATION'S METHOD ON THE MODEL
		 */
		final Model model = new Model();
		model.addUser(DefaultUser.getDefaultUser());
		
		final List<Habitation> listHabitation = new ArrayList<>();
		listHabitation.add(DefaultHabitation.getDefaultHabitation());
		listHabitation.add(DefaultHabitation.getDefaultHabitation());
		
		
		//Try method addHabitation
		model.addHabitation(DefaultUser.getUsername(), listHabitation.get(0));
		model.addHabitation(DefaultUser.getUsername(), listHabitation.get(1));
		
		
		//Try method getHabitations
		assertEquals(model.getHabitations(DefaultUser.getUsername()), listHabitation);
		
		//Try method getHabiatiton
		assertEquals(model.getHabitation(DefaultUser.getUsername(), 0), listHabitation.get(0));
		
		//Try method deleteHabitation
		model.deleteHabitation(DefaultUser.getUsername(), 1);
		try {
			model.getHabitation(DefaultUser.getUsername(), 1);
		}catch (InexistentHabitationException e){}
		
		//Try exception InexistentHabitationException
		try {
			model.deleteHabitation(DefaultUser.getUsername(), 54);
		}catch (InexistentHabitationException e){}
		
	}
	
}
