package elements;

public class SellingOrder extends Order implements Comparable<SellingOrder> {

	/**
	 * Calls superclass' constructor
	 * @param traderID ID of trader who gives order
	 * @param amount Amount of Pqoins for buying order
	 * @param price Price of Pqoins for buying order
	 */
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}
	
	@Override
	/**
	 * Compares prices, amounts and IDs to sort PriorityQueue
	 */
	
	public int compareTo(SellingOrder s) {
		if(price > s.price) {
			return 1;			
		}
		else if(price < s.price) {
			return -1;
		}
		else {
			if(amount < s.amount) {
				return 1;
			}
			else if(amount > s.amount) {
				return -1;
			}
			else {
				if(traderID > s.traderID) {
					return 1;
				}
				else if(traderID < s.traderID) {
					return -1;
				}
				else {
					return 0;
				}
			}
		}
	}	
}
