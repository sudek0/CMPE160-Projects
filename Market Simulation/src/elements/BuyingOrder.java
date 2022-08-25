package elements;


public class BuyingOrder extends Order implements Comparable<BuyingOrder>{

	/**
	 * Calls superclass' constructor
	 * @param traderID ID of trader who gives order
	 * @param amount Amount of Pqoins for buying order
	 * @param price Price of Pqoins for buying order
	 */
	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}

	@Override	
	/**
	 * Compares prices, amounts and IDs to sort PriorityQueue
	 */
	public int compareTo(BuyingOrder b) {
		if(price < b.price) {
			return 1;			
		}
		else if(price > b.price) {
			return -1;
		}
		else {
			if(amount < b.amount) {
				return 1;
			}
			else if(amount > b.amount) {
				return -1;
			}
			else {
				if(traderID > b.traderID) {
					return 1;
				}
				else if(traderID < b.traderID) {
					return -1;
				}
				else {
					return 0;
				}
			}
		}	
	}	
}
