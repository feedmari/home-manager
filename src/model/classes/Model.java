package model.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.ExistsUserException;
import exceptions.InexistentHabitationException;
import exceptions.InexistentUserException;
import model.interfaces.IEarningAndExpense;
import model.interfaces.IModel;
import model.interfaces.IWallet;

/**
 * The model of the application. Contains all the {@link User}s.
 * 
 * @author Marinelli Federico
 * @author Marco Mancini
 *
 */
public class Model implements IModel, Serializable {

	private static final long serialVersionUID = -2269940531312956364L;

	/**
	 * That map associates to each user a String that represents his username
	 */
	private final Map<String, User> users;

	/**
	 * Constructor.
	 */
	public Model() {
		this.users = new HashMap<String, User>();
	}

	@Override
	public boolean checkAccount(final String username, final char[] password) throws InexistentUserException {
		if (this.users.containsKey(username)) {
			if (password.length == this.users.get(username).getPassword().length) {
				for (int i = 0; i < password.length; i++) {
					if (password[i] != this.users.get(username).getPassword()[i]) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} else {
			throw new InexistentUserException("Utente inesistente");
		}
	}

	@Override
	public void addUser(final User user) throws ExistsUserException {
		if (!users.containsKey(user.getUsername())) {
			this.users.put(user.getUsername(), user);
			user.getWalletsManager().registerWallet(user.getWallet());
		} else {
			throw new ExistsUserException("Utente già registrato!");
		}

	}

	@Override
	public User getUser(final String username) throws InexistentUserException {
		if (users.containsKey(username)) {
			return this.users.get(username);
		} else {
			throw new InexistentUserException("Utente inesistente");
		}

	}

	@Override
	public void addHabitation(final String username, final Habitation habitation) {
		this.users.get(username).getHabitations().add(habitation);
		try {
			this.getUser(username).getWalletsManager().registerWallet(habitation.getWallet());
		} catch (InexistentUserException e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

	}

	@Override
	public void deleteHabitation(final String username, final int index) throws InexistentHabitationException {
		if (checkHabitation(username, index)) {
			try {
				this.getUser(username).getWalletsManager().deregisterWallet(this.users.get(username).getHabitations().get(index).getWallet());
				this.users.get(username).getHabitations().remove(index);
			} catch (InexistentUserException e) {
				System.err.println("Caught Exception: " + e.getMessage());
			}
			int i = 0;
			for (final Habitation hab : this.users.get(username).getHabitations()) {
				hab.setId(i);
				i++;
			}
		} else {
			throw new InexistentHabitationException("Abitazione insistente");
		}
	}

	@Override
	public List<Habitation> getHabitations(final String username) throws InexistentUserException {
		
		if (users.containsKey(username)) {
			return this.users.get(username).getHabitations();
		} else {
			throw new InexistentUserException("Utente inesistente");
		}
	}

	@Override
	public Habitation getHabitation(final String username, final int index) throws InexistentHabitationException {
		if (checkHabitation(username, index)) {
			return this.users.get(username).getHabitations().get(index);
		} else {
			throw new InexistentHabitationException("Abitazione insistente");
		}
	}

	@Override
	public void addTransition(final IWallet walletOwner, final IEarningAndExpense transition) {
		walletOwner.getWallet().addTransition(transition);
	}

	@Override
	public void deleteTransition(final IWallet walletOwner, final IEarningAndExpense transition, final int index) {
		walletOwner.getWallet().deleteTransition(transition, index);
		int i = 0;
		if (transition instanceof Expense) {
			for (final IEarningAndExpense e : walletOwner.getWallet().getExpenseList()) {
				e.setId(i);
				i++;
			}
		} else {
			for (final IEarningAndExpense e : walletOwner.getWallet().getEarningList()) {
				e.setId(i);
				i++;
			}
		}
	}

	@Override
	public List<IEarningAndExpense> getEarningList(final IWallet walletOwner) {
		return walletOwner.getWallet().getEarningList();
	}

	@Override
	public List<IEarningAndExpense> getExpenseList(final IWallet walletOwner) {
		return walletOwner.getWallet().getExpenseList();
	}

	@Override
	public WalletsManager getWalletsManager(final String username) throws InexistentUserException {
		return this.getUser(username).getWalletsManager();
	}

	private boolean checkHabitation(final String username, final int index) {
		return this.users.get(username).getHabitations().size() > index;

	}

}
