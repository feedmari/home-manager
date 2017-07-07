package model.interfaces;

import java.util.List;

import exceptions.ExistsUserException;
import exceptions.InexistentHabitationException;
import exceptions.InexistentUserException;
import model.classes.Habitation;
import model.classes.Model;
import model.classes.User;
import model.classes.WalletsManager;

/**
 * Interface for {@link Model}.
 * 
 * @author marco mancini
 * @author federico marinelli
 *
 */
public interface IModel {

	/**
	 * Check if username and password entered are correct.
	 * 
	 * @param username
	 *            the account's username
	 * @param password
	 *            the account's password
	 * @throws InexistentUserException
	 *             the inexistent user exception
	 * 
	 * @return true if the combination is correct, false in other cases (we'll
	 *         implements an exception for the false case)
	 * 
	 */
	boolean checkAccount(final String username, final char[] password) throws InexistentUserException;

	/**
	 * Add an user.
	 * 
	 * @param user
	 *            the user
	 * @throws ExistsUserException
	 *             the exists user exception.
	 */
	void addUser(final User user) throws ExistsUserException;

	/**
	 * Add an habitation.
	 * 
	 * @param username
	 *            the account's username
	 * @param habitation
	 *            the habitation
	 */
	void addHabitation(final String username, final Habitation habitation);

	/**
	 * Delete an habitation.
	 * 
	 * @param username
	 *            the account's username
	 * @param index
	 *            the habitation's index
	 * @throws InexistentHabitationException
	 *             throw InexistentHabitationException
	 */
	void deleteHabitation(final String username, final int index) throws InexistentHabitationException;

	/**
	 * Return all Habitations of the users.
	 * 
	 * @param username
	 *            the username
	 * @return all user's habitations
	 * 
	 * @throws InexistentUserException
	 *             throw InexistentHabitationException
	 */
	List<Habitation> getHabitations(final String username) throws InexistentUserException;

	/**
	 * Return the habitation with that index.
	 * 
	 * @param index
	 *            the index of Habitation
	 * @param username
	 *            the username
	 * @return the Habitation (we'll implements an Exception if the index isn't
	 *         correct)
	 * @throws InexistentHabitationException
	 *             throw InexistentHabitationException
	 */
	Habitation getHabitation(final String username, final int index) throws InexistentHabitationException;

	/**
	 * Add a transition(Expense or earning) to the wallet's owner.
	 * 
	 * @param walletOwner
	 *            the wallet's owner
	 * @param transition
	 *            the transition to add
	 */
	void addTransition(final IWallet walletOwner, final IEarningAndExpense transition);

	/**
	 * Delete a transition.
	 * 
	 * @param walletOwner
	 *            the owner
	 * @param transition
	 *            the transition
	 * @param index
	 *            the transition's index
	 */
	void deleteTransition(final IWallet walletOwner, final IEarningAndExpense transition, final int index);

	/**
	 * Return the earning list of a wallet's owner.
	 * 
	 * @param walletOwner
	 *            the wallet's owner
	 * @return the earningList
	 */
	List<IEarningAndExpense> getEarningList(final IWallet walletOwner);

	/**
	 * Return the expense list of a wallet's owner.
	 * 
	 * @param walletOwner
	 *            the wallet's owner
	 * @return the expense list
	 */
	List<IEarningAndExpense> getExpenseList(final IWallet walletOwner);

	/**
	 * Return the user logged.
	 * 
	 * @param username
	 *            the username
	 * @return the user logged
	 * @throws InexistentUserException
	 *             the inexistent user exception.
	 */
	User getUser(final String username) throws InexistentUserException;

	/**
	 * Return the class utility for manage all wallets.
	 * 
	 * @param username
	 *            the username
	 * @return the wallet manager of the user
	 * @throws InexistentUserException
	 *             the the inexistent user exception.
	 */
	WalletsManager getWalletsManager(final String username) throws InexistentUserException;

}
