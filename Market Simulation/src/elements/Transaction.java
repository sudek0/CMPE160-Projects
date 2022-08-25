package elements;

public class Transaction {
	
	private SellingOrder sellingOrder;
	private BuyingOrder buyingOrder;
	
	/**
	 * Initializes fields
	 * @param sellingOrder SellingOrder of the seller in the transaction
	 * @param buyingOrder BuyingOrder of the buyer in the transaction
	 */
	public Transaction(SellingOrder sellingOrder, BuyingOrder buyingOrder) {
		this.sellingOrder = sellingOrder;
		this.buyingOrder = buyingOrder;
	}
}
