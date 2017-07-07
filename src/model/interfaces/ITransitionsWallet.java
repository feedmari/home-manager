package model.interfaces;

import java.util.List;

/**
 * Interface for transitions in a wallet.
 * 
 * @author federico marinelli
 *
 */
public interface ITransitionsWallet {

	/**
	 * Add a transition.
	 * 
	 * @param transition
	 *            the transition
	 */
	void addTransition(IEarningAndExpense transition);

	/**
	 * Delete a transition.
	 * 
	 * @param transition
	 *            the transition
	 * @param index
	 *            the transition's index
	 */
	void deleteTransition(IEarningAndExpense transition, int index);

	/**
	 * Return the balance.
	 * 
	 * @return the balance
	 */
	double getCurrentBalance();

	/**
	 * Return the expense's list.
	 * 
	 * @return the expense's list
	 */
	List<IEarningAndExpense> getExpenseList();

	/**
	 * Return the earning's list.
	 * 
	 * @return the earning's list
	 */
	List<IEarningAndExpense> getEarningList();
}
