package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import elements.BuyingOrder;
import elements.Market;
import elements.SellingOrder;
import elements.Trader;

public class Main {
	
	public static Random myRandom; 
	public static void main(String[] args) throws FileNotFoundException {
			
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
				
		int seed = in.nextInt();
		int transactionFee = in.nextInt();
		int numberOfUsers = in.nextInt();
		int numberOfQueries = in.nextInt();		
		int numOfInvalidQueries = 0;
		
		myRandom = new Random(seed);
		Market market = new Market(transactionFee);
		
		ArrayList<Trader> traderArray = new ArrayList<Trader>();		
		String dataTraderArray[] = new String[numberOfUsers];
		String dataTraderLineArray[] = new String[2];			
		String dataQueryArray[] = new String[numberOfQueries];
		String dataQueryLineArray[] = new String[4];		
		in.nextLine(); //weirdspace
		
		for(int i=0; i<numberOfUsers; i++) {
			dataTraderArray[i]=in.nextLine().trim();		
		}		
		for(int k=0; k<numberOfQueries; k++) {
			dataQueryArray[k]=in.nextLine().trim();		
		}
			
		for(int x=0; x<dataTraderArray.length; x++) {
			dataTraderLineArray = dataTraderArray[x].split(" ",2);
			double dollarAmount = Double.parseDouble(dataTraderLineArray[0]);
			double pqoinAmount = Double.parseDouble(dataTraderLineArray[1]);
			
			Trader trader = new Trader(dollarAmount, pqoinAmount);
			traderArray.add(trader);
		}
		
		for(int c=0; c<dataQueryArray.length; c++) {
			dataQueryLineArray = dataQueryArray[c].split(" ", 4);
			int operationNum = Integer.parseInt(dataQueryLineArray[0]);
			
			int traderID;
			double price;
			double amount;
		
			int query=-1;
			
			//give buying order of specific price
			if(operationNum ==10) {
				traderID = Integer.parseInt(dataQueryLineArray[1]);
				price = Double.parseDouble(dataQueryLineArray[2]);
				amount = Double.parseDouble(dataQueryLineArray[3]);						
				query = traderArray.get(traderID).buy(amount, price, market);				
				if(query == 0) {
					numOfInvalidQueries++;	
				}
				market.checkTransactions(traderArray);
			}
			
			//give buying order of market price
			else if(operationNum == 11) {
				traderID = Integer.parseInt(dataQueryLineArray[1]);
				amount = Double.parseDouble(dataQueryLineArray[2]);
				
				if(!market.getBuyingOrders().isEmpty()) {
					query = traderArray.get(traderID).buy(amount, market.getSellingOrders().peek().getPrice(), market);	
				}					
				if(query == 0 || query == -1) {
					numOfInvalidQueries++;
				}
				market.checkTransactions(traderArray);
			}
			
			//give selling order of specific price
			else if(operationNum == 20) {
				traderID = Integer.parseInt(dataQueryLineArray[1]);
				price = Double.parseDouble(dataQueryLineArray[2]);
				amount = Double.parseDouble(dataQueryLineArray[3]);
				query = traderArray.get(traderID).sell(amount, price, market);
				if(query == 0) {
					numOfInvalidQueries++;
				}
				market.checkTransactions(traderArray);
			}
		
			//give selling order of market price
			else if(operationNum == 21) {
				traderID = Integer.parseInt(dataQueryLineArray[1]);
				amount = Double.parseDouble(dataQueryLineArray[2]);
				
				if(!market.getBuyingOrders().isEmpty()) {
					query = traderArray.get(traderID).sell(amount, market.getBuyingOrders().peek().getPrice(), market);
				}				
				if(query == 0 || query==-1) {
					numOfInvalidQueries++;
				}
				market.checkTransactions(traderArray);
			}
			
			//deposit a certain amount of dollars to wallet
			else if(operationNum == 3) {
				traderID = Integer.parseInt(dataQueryLineArray[1]);
				amount = Double.parseDouble(dataQueryLineArray[2]);	
				traderArray.get(traderID).getWallet().depositDollar(amount);			
			}
			
			//withdraw a certain amount of dollars from wallet
			else if(operationNum == 4) {
				traderID = Integer.parseInt(dataQueryLineArray[1]);
				amount = Double.parseDouble(dataQueryLineArray[2]);	
				query = traderArray.get(traderID).getWallet().withdrawDollar(amount);
				if(query == 0) {
					numOfInvalidQueries++;
				}
			}
			
			//print wallet status
			else if(operationNum == 5) {
				traderID = Integer.parseInt(dataQueryLineArray[1]);
				out.println(traderArray.get(traderID));
			}
					
			//SYSTEM QUERIES
			//give rewards to all traders
			else if(operationNum == 777) {
				for(Trader trader: traderArray) {
					trader.getWallet().addCoin(myRandom.nextDouble()*10);
				}		
			}
			
			//make open market operation
			else if(operationNum == 666) {
				price = Double.parseDouble(dataQueryLineArray[1]);
				market.makeOpenMarketOperation(price, traderArray);
			}
			
			//print the current market size
			else if(operationNum == 500) {				
				double totalDollarInBuyingPQ = 0;
				double totalPQoinInSelling = 0;
		        for (BuyingOrder buyOrder: market.getBuyingOrders()) {
		            totalDollarInBuyingPQ += buyOrder.getPrice()*buyOrder.getAmount();
		        }     
		        for (SellingOrder sellOrder: market.getSellingOrders()) {
		        	totalPQoinInSelling += sellOrder.getAmount();
		        }
				out.println("Current market size: "+ String.format("%.5f",totalDollarInBuyingPQ) + " "
							+String.format("%.5f",totalPQoinInSelling));
			}
			
			//print number of successful transactions
			else if(operationNum == 501) {
				out.println("Number of successful transactions: "+market.getTransactions().size());
			}
			
			//print the number of invalid queries
			else if(operationNum == 502) {
				out.println("Number of invalid queries: "+numOfInvalidQueries);			
			}
			
			//print the current prices
			else if(operationNum == 505) {
				out.println(market);
			}
			
			//print all traders’ wallet status
			else if(operationNum == 555) {
				for(Trader trader: traderArray) {
					double dollars = trader.getWallet().getBlockedDollars()+trader.getWallet().getDollars();
					double coins = trader.getWallet().getBlockedCoins()+trader.getWallet().getCoins();
					out.println("Trader "+trader.getId()+": "+String.format("%.5f",dollars)+"$ "+String.format("%.5f",coins)+"PQ");
				}				
			}			
		}			
	}
}
