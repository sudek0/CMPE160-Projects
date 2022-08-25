package elements;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Market {
	private PriorityQueue<SellingOrder> sellingOrders;
	private PriorityQueue<BuyingOrder> buyingOrders;
	private ArrayList<Transaction> transactions;
	private int fee;
	
	/**
	 * Initializes field fee and creates PriorityQueue objects sellingOrders and buyingOrders
	 * Creates an ArrayList of Transactions
	 * @param fee
	 */
	public Market(int fee) {
		this.fee = fee;
		sellingOrders = new PriorityQueue<SellingOrder>();
		buyingOrders = new PriorityQueue<BuyingOrder>();
		transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * Adds SellingOrder object to sellingOrders
	 * @param order SellingOrder object
	 */
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
	}
	
	/**
	 * Adds BuyingOrder object to buyingOrders
	 * @param order BuyingOrder object
	 */
	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
	}
	
	/**
	 * Conducts trader#0's intervention to modify market price
	 * @param price Market price aim 
	 * @param traderList Traders in the market
	 */
	public void makeOpenMarketOperation(double price, ArrayList<Trader> traderList) {
		double buyingP;
		double buyingA;
		double sellingP;
		double sellingA;
		SellingOrder sellOrder;
		BuyingOrder buyOrder;
		while((!buyingOrders.isEmpty()) && buyingOrders.peek().price >= price) {
			buyingP = buyingOrders.peek().price;
			buyingA = buyingOrders.peek().amount;
			sellOrder = new SellingOrder(0, buyingA, buyingP);
			sellingOrders.add(sellOrder);
			checkTransactions(traderList);
		}
		
		while((!sellingOrders.isEmpty()) && sellingOrders.peek().price <= price) {
			sellingP = sellingOrders.peek().price;
			sellingA = sellingOrders.peek().amount;
			buyOrder = new BuyingOrder(0, sellingA, sellingP);
			buyingOrders.add(buyOrder);
			checkTransactions(traderList);
		}
	}
	
	/**
	 * Checks and carries out transactions in the market
	 * @param traders Traders in the market
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		double sPrice;
		double bPrice;
		double sAmount;
		double bAmount;
		int sID;
		int bID;
		SellingOrder sellO;
		BuyingOrder buyO;
		Wallet bWallet;
		Wallet sWallet;
		BuyingOrder buyOrder;
		SellingOrder sellOrder;
		Transaction tr;	
		while(!sellingOrders.isEmpty() && !buyingOrders.isEmpty() && buyingOrders.peek().price >= sellingOrders.peek().price) {
			sellOrder = sellingOrders.peek();
			buyOrder = buyingOrders.peek();
			tr = new Transaction(sellOrder, buyOrder);
			transactions.add(tr);			
			sPrice = sellingOrders.peek().price;
			bPrice = buyingOrders.peek().price;
			sAmount = sellingOrders.peek().amount;
			bAmount = buyingOrders.peek().amount;
			sID = sellingOrders.peek().traderID;
 			bID = buyingOrders.peek().traderID;
			bWallet = traders.get(bID).getWallet();
			sWallet = traders.get(sID).getWallet();
						
			if(sAmount == bAmount) {
				bWallet.addCoin(bAmount);
				bWallet.decreaseBlockedDollars(bAmount*bPrice);
				bWallet.depositDollar((bPrice-sPrice)*bAmount);
				sWallet.decreaseBlockedCoins(bAmount);
				sWallet.depositDollar(bAmount*sPrice*(1.0-((double)fee/1000.0)));
			}			
			else if(bAmount > sAmount) {
				bWallet.addCoin(sAmount);
				bWallet.decreaseBlockedDollars(sAmount*bPrice);
				bWallet.depositDollar((bPrice-sPrice)*sAmount);	
				sWallet.decreaseBlockedCoins(sAmount);
				sWallet.depositDollar(sPrice*sAmount*(1.0-((double)fee/1000.0)));
				
				buyO = new BuyingOrder(bID, bAmount-sAmount, bPrice);
				buyingOrders.add(buyO);
			}			
			else { //sAmount > bAmount
				bWallet.addCoin(bAmount);
				bWallet.decreaseBlockedDollars(bAmount*bPrice);
				bWallet.depositDollar((bPrice- sPrice)*bAmount);				
				sWallet.decreaseBlockedCoins(bAmount);
				sWallet.depositDollar(bAmount*sPrice*(1.0-((double)fee/1000.0)));
				
				sellO = new SellingOrder(sID, sAmount-bAmount, sPrice);
				sellingOrders.add(sellO);
			}					
			buyingOrders.poll();
			sellingOrders.poll();
		}
	}	
	

	@Override
	/**
	 * toString method to print current prices in the market
	 */
	public String toString() {
		double sellingP;
		double buyingP;
		
		if(sellingOrders.isEmpty() && buyingOrders.isEmpty()) {
			return "Current prices: "+"0.00000"+" "+"0.00000"
					+" "+"0.00000";
		}
		else if(buyingOrders.isEmpty()) {
			sellingP = sellingOrders.peek().price;
			return "Current prices: "+"0.00000"+" "+String.format("%.5f",sellingP)
					+" "+String.format("%.5f", (sellingP));
		}
		
		else if(sellingOrders.isEmpty()) {
			buyingP = buyingOrders.peek().price;
			return "Current prices: "+String.format("%.5f",buyingP)+" "+"0.00000"
					+" "+String.format("%.5f", (buyingP));			
		}
		
		else {
			sellingP = sellingOrders.peek().price;
			buyingP = buyingOrders.peek().price;
			double averageP = (buyingP + sellingP)/2;
			return "Current prices: "+String.format("%.5f",buyingP)+" "+String.format("%.5f",sellingP)
					+" "+String.format("%.5f",averageP);
		}
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}

	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}
	
	
}
