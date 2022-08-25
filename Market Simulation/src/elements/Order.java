package elements;

public abstract class Order {
	int traderID;	
	double amount;
	double price;

	/**
	 * Initializes fields
	 * @param traderID ID of the trader who gives order
	 * @param amount Amount of Pqoins
	 * @param price Price of Pqoins
	 */
	public Order(int traderID, double amount, double price) {
		this.traderID = traderID;
		this.amount = amount;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public double getAmount() {
		return amount;
	}	
}