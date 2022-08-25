
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import containers.BasicContainer;
import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import ports.Port;
import ships.Ship;

public class Main {
			
	public static void containerIDPrinter(ArrayList<Container> contList, PrintStream out, int indentation) {
		boolean basicContainer = false;
		boolean justHeavyContainer = false;
		boolean liqContainer = false;
		boolean refContainer = false;		
		ArrayList<Integer> basicContIDList = new ArrayList<Integer>();
		ArrayList<Integer> justHeavyContIDList = new ArrayList<Integer>();
		ArrayList<Integer> liqContIDList = new ArrayList<Integer>();
		ArrayList<Integer> refContIDList = new ArrayList<Integer>();	
		for(int c=0; c < contList.size(); c++) {
			Container cont = contList.get(c);			
			if(cont instanceof HeavyContainer) {
				if(cont instanceof LiquidContainer) {
					liqContainer = true;
					liqContIDList.add(cont.getID());
				}					
				else if(cont instanceof RefrigeratedContainer) {
					refContainer = true;
					refContIDList.add(cont.getID());
				}
				else {
					justHeavyContainer = true;
					justHeavyContIDList.add(cont.getID());
				}					
			}
			else {
				basicContainer = true;
				basicContIDList.add(cont.getID());
			}
		}			
		if(basicContainer) {
			if(indentation == 1) {
				out.print("  BasicContainer:");
			}
			else {
				out.print("    BasicContainer:");
			}	
			Collections.sort(basicContIDList);
			for(int b=0; b < basicContIDList.size(); b++) {
				out.print(" " + basicContIDList.get(b));
			}
			out.println();
		}
			
							
		if(justHeavyContainer) {
			if(indentation == 1) {
				out.print("  HeavyContainer:");
			}
			else {
				out.print("    HeavyContainer:");
			}	
			Collections.sort(justHeavyContIDList);
				
				for(int b=0; b < justHeavyContIDList.size(); b++) {
					out.print(" " + justHeavyContIDList.get(b));
				}								
			out.println();
		}	
		
		if(refContainer) {
			if(indentation == 1) {
				out.print("  RefrigeratedContainer:");
			}
			else {
				out.print("    RefrigeratedContainer:");
				}	

			Collections.sort(refContIDList);
			for(int b=0; b < refContIDList.size(); b++) {
				out.print(" " + refContIDList.get(b));
			}
			out.println();
		}	
		
		if(liqContainer) {
			if(indentation == 1) {
				out.print("  LiquidContainer:");
			}
			else {
				out.print("    LiquidContainer:");
			}
				Collections.sort(liqContIDList);
				
			for(int b=0; b < liqContIDList.size(); b++) {
				out.print(" " + liqContIDList.get(b));
			}
			out.println();
		}	
	}
		
	public static void main(String[] args) throws FileNotFoundException {
		
		//
		// Main receives two arguments: path to input file and path to output file.
		// You can assume that they will always be provided, so no need to check them.
		// Scanner and PrintStream are already defined for you.
		// Use them to read input and write output.
		// 
		// Good Luck!
		// 
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		
		int N = in.nextInt();
		
		String dataArray[] = new String[N+1];
		String dataLineArray[] = new String[8];
		
		int i=0;
		while(i<N+1) {
			dataArray[i]=in.nextLine().trim();
			i++;			
		}
				
		int containerIDCount = 0;
		int shipIDCount = 0;
		int portIDCount = 0;
		
		ArrayList<Port> portArrayList = new ArrayList<Port>();
		ArrayList<Ship> shipArrayList = new ArrayList<Ship>();
		ArrayList<Container> containerArrayList = new ArrayList<Container>();
		
		for(int x=1; x < (dataArray.length); x++) {
			dataLineArray = dataArray[x].split(" ",8);			
			int operationNum = Integer.parseInt(dataLineArray[0]);
			
			//Creating a new Container
			if(operationNum == 1) {
				int containerPortID = Integer.parseInt(dataLineArray[1]);
				int weight = Integer.parseInt(dataLineArray[2]);				
				
				if(dataLineArray.length == 4) {
					if(dataLineArray[3].equals("R")) {
						RefrigeratedContainer refContObj = new RefrigeratedContainer(containerIDCount, weight);
						portArrayList.get(containerPortID).getContainers().add(refContObj);
						containerArrayList.add(refContObj);
					}	
					if(dataLineArray[3].equals("L")) {	
						LiquidContainer liqContObj = new LiquidContainer(containerIDCount, weight);
						portArrayList.get(containerPortID).getContainers().add(liqContObj);
						containerArrayList.add(liqContObj);
					}							
				}																					
				else {			
					if (weight <= 3000) {
						BasicContainer basicContObj = new BasicContainer(containerIDCount, weight);
						portArrayList.get(containerPortID).getContainers().add(basicContObj);
						containerArrayList.add(basicContObj);
					}					
					else {
						HeavyContainer heavyContObj = new HeavyContainer(containerIDCount, weight);
						portArrayList.get(containerPortID).getContainers().add(heavyContObj);
						containerArrayList.add(heavyContObj);
					}
				}		
				containerIDCount++;
			}	
						
			//Creating a Ship
			if(operationNum == 2) {
				int shipPortID = Integer.parseInt(dataLineArray[1]);
				int shipMaxWeight = Integer.parseInt(dataLineArray[2]);
				int shipMaxContNumber = Integer.parseInt(dataLineArray[3]);
				int shipMaxHeavyContNumber = Integer.parseInt(dataLineArray[4]);
				int shipMaxRefContNumber = Integer.parseInt(dataLineArray[5]);
				int shipMaxLiqContNumber = Integer.parseInt(dataLineArray[6]);
				double fuelConsumptionPerKM = Double.parseDouble(dataLineArray[7]);				
				
				Ship shipObj = new Ship(shipIDCount, portArrayList.get(shipPortID), shipMaxWeight,
										shipMaxContNumber, shipMaxHeavyContNumber, shipMaxRefContNumber,
										shipMaxLiqContNumber, fuelConsumptionPerKM);
				
				portArrayList.get(shipPortID).getCurrent().add(shipObj);
				shipArrayList.add(shipObj);
	
				shipIDCount++;			
			}	
			
			//Creating a Port
			if(operationNum == 3) {
				double xCoordinatePort = Double.parseDouble(dataLineArray[1]);
				double yCoordinatePort = Double.parseDouble(dataLineArray[2]);			
				Port portObj = new Port(portIDCount, xCoordinatePort, yCoordinatePort);
				portArrayList.add( portObj);			
				portIDCount++;
			}
							
			//Loading container into a ship
			if(operationNum == 4) {
				int loadingShipID = Integer.parseInt(dataLineArray[1]);
				int loadingContainerID = Integer.parseInt(dataLineArray[2]);		
				shipArrayList.get(loadingShipID).load(containerArrayList.get(loadingContainerID));			
			}
				
			//Unloading container into a ship
			if(operationNum == 5) {
				int unloadingShipID = Integer.parseInt(dataLineArray[1]);
				int unloadingContainerID = Integer.parseInt(dataLineArray[2]);		
				shipArrayList.get(unloadingShipID).unLoad(containerArrayList.get(unloadingContainerID));
			}
			
			//ships travel from one port to another
			if(operationNum == 6) {
				int travelingShipID = Integer.parseInt(dataLineArray[1]);
				int destinationPortID = Integer.parseInt(dataLineArray[2]);		
				
				boolean travelStatus = shipArrayList.get(travelingShipID).sailTo(portArrayList.get(destinationPortID));
				
				if(travelStatus) {
					shipArrayList.get(travelingShipID).getCurrentPort().outgoingShip(shipArrayList.get(travelingShipID));
					shipArrayList.get(travelingShipID).getCurrentPort().getCurrent().remove(shipArrayList.get(travelingShipID));
					shipArrayList.get(travelingShipID).setCurrentPort(portArrayList.get(destinationPortID)); 
					portArrayList.get(destinationPortID).incomingShip(shipArrayList.get(travelingShipID));				
				}							
			}
			
			//fuel is added to a ship
			if(operationNum == 7) {
				int fuelShipID = Integer.parseInt(dataLineArray[1]);
				double fuelAmount = Double.parseDouble(dataLineArray[2]);	
				shipArrayList.get(fuelShipID).reFuel(fuelAmount);
			}
																				
		} //end of the reading part
	
		
		
	//OUTPUT PART				
		for(int p=0; p < portArrayList.size(); p++) {
			Port port = portArrayList.get(p);
			out.println("Port " + port.getID() + ": " + "(" + String.format("%.2f",port.getX()) + ", " 
			            + String.format("%.2f",port.getY()) + ")");			
			containerIDPrinter(port.getContainers(), out, 1);			
			Collections.sort(port.getCurrent());			
			for(int s=0; s<port.getCurrent().size(); s++) {
				Ship ship = port.getCurrent().get(s);
				out.println("  Ship " + ship.getID() + ": " + String.format("%.2f", ship.getFuel()));				
				containerIDPrinter(ship.getCurrentContainers(), out, 2);
			}					
		}   //end out output port for loop
			
		
				
		in.close();
		out.close();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

