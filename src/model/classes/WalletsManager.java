package model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.interfaces.IEarningAndExpense;
import model.interfaces.ITransitionsWallet;

/**
 * It models a wallets manager.
 * 
 * @author federico marinelli
 *
 */
public class WalletsManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5274546345442397956L;
	private final List<ITransitionsWallet> wallets = new ArrayList<>();

	/**
	 * Method for register a new wallet.
	 * @param wallet the wallet
	 */
	public void registerWallet(final ITransitionsWallet wallet) {
		this.wallets.add(wallet);
	}

	/**
	 * Method for deregister a wallet.
	 * @param wallet the wallet
	 */
	public void deregisterWallet(final ITransitionsWallet wallet) {
		this.wallets.remove(wallet);
	}

	/**
	 * Method for the the total wallets balance.
	 * @return the total balance
	 */
	public double getTotalBalance() {
		double balance = 0;
		for (final ITransitionsWallet e : wallets) {
			balance = balance + e.getCurrentBalance();
		}
		return balance;
	}

	/**
	 * Method for get the earning balance.
	 * @return earnings the earning balance
	 */
	public double getEarningBalance() {
		double balance = 0;
		final List<IEarningAndExpense> list = new LinkedList<>();
		for (final ITransitionsWallet e : wallets) {
			list.addAll(e.getEarningList());
		}
		for (final IEarningAndExpense e : list) {
			balance = balance + e.getCost();
		}
		return balance;
	}

	/**
	 * Method for get the expense balance.
	 * @return expenses the expense balance
	 */
	public double getExpenseBalance() {
		double balance = 0;
		final List<IEarningAndExpense> list = new LinkedList<>();
		for (final ITransitionsWallet e : wallets) {
			list.addAll(e.getExpenseList());
		}
		for (final IEarningAndExpense e : list) {
			balance = balance + e.getCost();
		}
		return balance;
	}

	/**
	 * Method for get all wallets saved.
	 * @return wallets the wallets
	 */
	public List<ITransitionsWallet> getWallets() {
		return this.wallets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wallets == null) ? 0 : wallets.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WalletsManager other = (WalletsManager) obj;
		if (wallets == null) {
			if (other.wallets != null) {
				return false;
			}
		} else if (!wallets.equals(other.wallets)) {
			return false;
		}
		return true;
	}

}
