
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;
import java.util.Collections;

import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import interfaces.IShip;
import ports.Port;

public class Ship implements IShip ,Comparable<Ship>{
	private int ID;
	private double fuel;
	private Port currentPort;
	
	
	private ArrayList<Container> shipCurrentContainerList;
	

	private int totalWeightCapacity;
	private int maxNumberOfAllContainers;
	private int maxNumberOfHeavyContainers;
	private int maxNumberOfRefrigeratedContainers;
	private int maxNumberOfLiquidContainers;
	private double fuelConsumptionPerKM;
	
	private int currentContainerNum;
	private int currentHeavyContainerNum;
	private int currentRefContainerNum;
	private int currentLiqContainerNum;

	
	
	public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, 
			    int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers,
			    int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {	
		this.ID = ID;
		currentPort = p;
		this.totalWeightCapacity = totalWeightCapacity;
		this.maxNumberOfAllContainers = maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
		this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
		this.fuelConsumptionPerKM = fuelConsumptionPerKM;	
		fuel = 0;
		shipCurrentContainerList = new ArrayList<Container>();
	}
	
	public ArrayList<Container> getCurrentContainers(){
		Collections.sort(shipCurrentContainerList);
		return shipCurrentContainerList;
	}
	
	
	@Override
	public boolean sailTo(Port p) {
		double totalFuelPerKM = fuelConsumptionPerKM;
		double distance = currentPort.getDistance(p);
		
		for(int i=0; i<shipCurrentContainerList.size(); i++) {	
			totalFuelPerKM += shipCurrentContainerList.get(i).consumption();					
		}
		
		if(totalFuelPerKM*distance > fuel) {			
			return false;
		}
		
		else {
			fuel -= totalFuelPerKM*distance;
			return true;
		}
	}
	
	@Override
	public void reFuel(double newFuel) {
		fuel += newFuel;		
	}
	
	@Override
	public boolean load(Container cont) {
		int currentWeight = 0;
		for(int w=1; w<shipCurrentContainerList.size(); w++) {
			currentWeight += shipCurrentContainerList.get(w).getWeight();
		}
		
		if(currentPort.getContainers().contains(cont) && 
		  (currentContainerNum+1<=maxNumberOfAllContainers
		   && (currentWeight+cont.getWeight()<=totalWeightCapacity))) {
			if(cont instanceof HeavyContainer) {
				if(currentHeavyContainerNum+1<=maxNumberOfHeavyContainers) {
					if(cont instanceof LiquidContainer) {
						if(currentLiqContainerNum+1<=maxNumberOfLiquidContainers) {
							currentLiqContainerNum++;
							currentHeavyContainerNum++;
							currentContainerNum++;
							shipCurrentContainerList.add(cont);
							currentPort.getContainers().remove(cont);
							return true;							
						}
						else {
							return false;
						}
					}
					else if(cont instanceof RefrigeratedContainer) {
						if(currentRefContainerNum+1<=maxNumberOfRefrigeratedContainers) {
							currentRefContainerNum++;
							currentHeavyContainerNum++;
							currentContainerNum++;
							shipCurrentContainerList.add(cont);
							currentPort.getContainers().remove(cont);
							return true;							
						}
						else {
							return false;
						}
					}
					else {
						currentHeavyContainerNum++;
						currentContainerNum++;
						shipCurrentContainerList.add(cont);
						currentPort.getContainers().remove(cont);
						return true;
					}
				}
				else {
					return false;
				}
			}
			currentContainerNum++;
			shipCurrentContainerList.add(cont);
			currentPort.getContainers().remove(cont);
			return true;
		}
		else {
			return false;	
		}
	}
	
	
	@Override
	public boolean unLoad(Container cont) {
		if(shipCurrentContainerList.contains(cont)) {			
			if(cont instanceof HeavyContainer) {
				if(cont instanceof LiquidContainer) {
					currentLiqContainerNum--;
					shipCurrentContainerList.remove(cont);
					currentPort.getContainers().add(cont);
				}
				else if(cont instanceof RefrigeratedContainer) {
					currentRefContainerNum--;
					shipCurrentContainerList.remove(cont);
					currentPort.getContainers().add(cont);
				}
				else {
					currentHeavyContainerNum--;
					shipCurrentContainerList.remove(cont);
					currentPort.getContainers().add(cont);
				}
			}
			else {				
				currentContainerNum--;
				shipCurrentContainerList.remove(cont);
				currentPort.getContainers().add(cont);
			}
			return true;
		}
		else {
			return false;
		}
	}

	
	@Override
	public int compareTo(Ship o) {
		if(this.ID < o.ID) {
			return -1;
		}
		if(this.ID > o.ID) {
			return 1;
		}		
		return 0;
	}

	
	
	//GETTERS AND SETTERS
	public int getID() {
		return ID;
	}

	public double getFuel() {
		return fuel;
	}

	public Port getCurrentPort() {
		return currentPort;
	}

	public ArrayList<Container> getShipContainerList() {
		return shipCurrentContainerList;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	public void setCurrentPort(Port currentPort) {
		this.currentPort = currentPort;
	}

	public void setShipContainerList(ArrayList<Container> shipContainerList) {
		this.shipCurrentContainerList = shipContainerList;
	}


}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

