package elements;

public class Wallet {
	private double dollars;   
	private double coins;
	private double blockedDollars;
	private double blockedCoins;

	/**
	 * Initializes fields
	 * @param dollars Number of dollars ready to use
	 * @param coins Number of coins ready to use
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
	}
	
	/**
	 * Adds dollars to wallet
	 * @param amount Added amount
	 */
	public void depositDollar(double amount) {
		dollars += amount;
	}
	
	/**
	 * Checks the amount of dollars in the wallet and if possible, withdraws dollars from wallet 
	 * @param amount Withdrawn amount
	 * @return 1 if withdrawing that amount is possible, 0 if not
	 */
	public int withdrawDollar(double amount) {
		if(dollars >= amount) {
			dollars -= amount;
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public void addCoin(double amount) {
		coins += amount;
	}
	public void withdrawCoin(double amount) {
		coins -= amount;
	}
	
	public void increaseBlockedDollars(double amount) {
		blockedDollars += amount;
	}
	
	public void decreaseBlockedDollars(double amount) {
		blockedDollars -= amount;
	}

	public void increaseBlockedCoins(double amount) {
		blockedCoins += amount;
	}
	
	public void decreaseBlockedCoins(double amount) {
		blockedCoins -= amount;
	}

	public double getDollars() {
		return dollars;
	}

	public double getCoins() {
		return coins;
	}

	public double getBlockedDollars() {
		return blockedDollars;
	}

	public double getBlockedCoins() {
		return blockedCoins;
	}
}
