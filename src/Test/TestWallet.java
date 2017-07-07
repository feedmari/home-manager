package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.classes.Earning;
import model.classes.Earning.EarningType;
import model.classes.Expense;
import model.classes.Expense.ExpenseType;
import model.classes.Model;
import model.interfaces.IEarningAndExpense;

/**
 * Class for the managing of the wallet's into the model.
 * @author federico marinelli
 * 
 */
public class TestWallet {

	@org.junit.Test
	public void testWallet() throws Exception {
		
		final Model model = new Model();
		model.addUser(DefaultUser.getDefaultUser());
		model.addHabitation(DefaultUser.getUsername(), DefaultHabitation.getDefaultHabitation());

		
		//Testing a new Expense
		IEarningAndExpense expenseTest = new Expense(ExpenseType.AFFITTO, 150.0, true, null, 0);
		assertEquals(expenseTest.getCost(), 150, 0);
		assertEquals(expenseTest.getType(), ExpenseType.AFFITTO);
		assertTrue(expenseTest.isPayed());
		assertEquals(expenseTest.getId(),0);
		expenseTest.setDescription("descrizione");
		assertEquals(expenseTest.getDescription(), "descrizione");

		//Testing the model method for a wallet
		model.addTransition(DefaultHabitation.getDefaultHabitation(), new Expense(ExpenseType.AFFITTO, 150.0, true, null, 0));
		model.addTransition(DefaultHabitation.getDefaultHabitation(), new Expense(ExpenseType.AFFITTO, 150.0, true, null, 1));
		model.addTransition(DefaultHabitation.getDefaultHabitation(), new Earning(EarningType.AFFITTO, 150.0, true, null, 0));
		model.deleteTransition(DefaultHabitation.getDefaultHabitation(), new Expense(ExpenseType.AFFITTO, 150.0, true, null, 0), 1);
		
		//testiong on the wallet
		assertEquals(DefaultHabitation.getDefaultHabitation().getWallet().getCurrentBalance(), 0, 0);
		
		//testing on the walletmanager
		assertEquals(model.getUser(DefaultUser.getUsername()).getWalletsManager().getExpenseBalance(), 150, 0);
		assertEquals(model.getUser(DefaultUser.getUsername()).getWalletsManager().getTotalBalance(), 0, 0);
			
	}
	
}
