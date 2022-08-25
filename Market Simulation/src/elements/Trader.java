package elements;

public class Trader {

	private int id;
	private Wallet wallet;
	public static int numberOfUsers = 0;

	/**
	 * Initializes fields and increases numberOfUsers to keep track of trader IDs
	 * @param dollars Amount of dollars trader initially has
	 * @param coins Amount of coins trader initially has
	 */
	public Trader(double dollars, double coins) {
		wallet = new Wallet(dollars, coins);
		id = numberOfUsers;
		numberOfUsers++;
	}

	/**
	 * Checks coin amount of trader and decides whether sell order request is valid
	 * If it is valid, creates order object and modifies seller's wallet
	 * @param amount Amount of Pqoins for selling order
	 * @param price Price of Pqoins for selling order
	 * @param market Market object in which ordering takes place
	 * @return 1 if selling order request is valid; 2 if invalid
	 */
	public int sell(double amount, double price, Market market) {
		if(wallet.getCoins() >= amount) {
			SellingOrder sellingOrder = new SellingOrder(id, amount, price);
			market.giveSellOrder(sellingOrder);
			wallet.increaseBlockedCoins(amount);
			wallet.withdrawCoin(amount);
			return 1;
		}
		else {
			return 0;
		}	
	}

	/**
	 * Checks dollar amount of trader and decides whether buy order request is valid
	 * If it is valid, creates order object and modifies buyer's wallet
	 * @param amount Amount of Pqoins for buying order
	 * @param price Price of Pqoins for buying order
	 * @param market Market object in which ordering takes place
	 * @return 1 if selling order request is valid; 2 if invalid
	 */
	public int buy(double amount, double price, Market market) {
		if(wallet.getDollars() >= price*amount) {
			BuyingOrder buyingOrder = new BuyingOrder(id, amount, price);
			market.giveBuyOrder(buyingOrder);
			wallet.increaseBlockedDollars(amount*price);
			wallet.withdrawDollar(amount*price);
			return 1;
		}
		else {
			return 0;
		}	
	}
	
	@Override
	/**
	 * toString method that prints current assets in the wallet
	 */
	public String toString() {
		return "Trader "+id+": "+String.format("%.5f",(wallet.getDollars()+wallet.getBlockedDollars()))+"$ "+
				String.format("%.5f",wallet.getCoins()+wallet.getBlockedCoins())+"PQ";
	}
	
	public Wallet getWallet() {
		return wallet;
	}

	public int getId() {
		return id;
	}		
}
