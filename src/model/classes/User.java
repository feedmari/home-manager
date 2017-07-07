package model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.interfaces.ITransitionsWallet;
import model.interfaces.IUser;
import model.interfaces.IWallet;

/**
 * It models a generic User.
 * @author marco mancini
 * @author federico marinelli
 *
 */
public class User implements IUser, Serializable, IWallet {
	
	private static final long serialVersionUID = 1301858514675361524L;

	
	private final String name;
	private final String surname;
	private final String username;
	private final char[] password;
	private final List<Habitation> habitations;
	private final Wallet wallet;
	private final WalletsManager walletManager;
	
	/**
	 * The constructor.
	 * @param name the user's name
	 * @param surname the user's surname
	 * @param username the user's username
	 * @param password the user's password
	 */
	public User(final String name, final String surname, final String username, final char[] password) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.habitations = new ArrayList<>();
		this.wallet = new Wallet();		
		this.walletManager = new WalletsManager();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSurname() {
		return this.surname;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public char[] getPassword() {
		return this.password;
	}

	@Override
	public List<Habitation> getHabitations() {
		return this.habitations;
	}

	@Override
	public ITransitionsWallet getWallet() {
		return this.wallet;
	}
	
	@Override 
	public WalletsManager getWalletsManager() {
		return this.walletManager;
	}
}
