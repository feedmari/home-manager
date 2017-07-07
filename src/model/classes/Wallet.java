package model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.interfaces.IEarningAndExpense;
import model.interfaces.ITransitionsWallet;

/**
 * It models a generic wallet.
 * 
 * @author federico marinelli
 *
 */
public class Wallet implements ITransitionsWallet, Serializable {

	private static final long serialVersionUID = 2573632437023840908L;

	private final List<IEarningAndExpense> expenseList = new ArrayList<>();
	private final List<IEarningAndExpense> earningList = new ArrayList<>();

	@Override
	public void addTransition(final IEarningAndExpense transition) {
		if (transition instanceof Expense) {
			this.expenseList.add(transition);
		} else {
			this.earningList.add(transition);
		}
	}

	@Override
	public void deleteTransition(final IEarningAndExpense transition, final int index) {
		if (transition instanceof Expense) {
			this.expenseList.remove(index);
		} else {
			this.earningList.remove(index);
		}

	}

	@Override
	public double getCurrentBalance() {
		double balance = 0;
		for (final IEarningAndExpense e : expenseList) {
			balance -= e.getCost();
		}
		for (final IEarningAndExpense e : earningList) {
			balance += e.getCost();
		}
		return balance;
	}

	@Override
	public List<IEarningAndExpense> getExpenseList() {
		// return new ArrayList<IEarningAndExpense>(expenseList);
		return this.expenseList;
	}

	@Override
	public List<IEarningAndExpense> getEarningList() {
		// return new ArrayList<IEarningAndExpense>(earningList);
		return this.earningList;
	}

}
