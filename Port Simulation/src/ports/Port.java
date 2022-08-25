
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;
import containers.Container;
import interfaces.IPort;
import ships.Ship;

public class Port implements IPort{
	private int ID;
	private double X;
	private double Y;
	private ArrayList<Container> containers;
	private ArrayList<Ship> history;
	private ArrayList<Ship> current;
			
	public Port(int ID, double X, double Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
		containers = new ArrayList<Container>();
		history = new ArrayList<Ship>();
		current = new ArrayList<Ship>();
	}
	
	@Override
	public void incomingShip(Ship s) {
		current.add(s);
	}
	
	@Override
	public void outgoingShip(Ship s) {
		if(!history.contains(s)) {
			history.add(s);
		}
	}
	
	public double getDistance(Port other) {
		return Math.sqrt((X-other.X)*(X-other.X)+(Y-other.Y)*(Y-other.Y)) ;
	}


	
	//GETTERS AND SETTER
	public int getID() {
		return ID;
	}

	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}

	public ArrayList<Container> getContainers() {
		return containers;
	}

	public ArrayList<Ship> getHistory() {
		return history;
	}

	public ArrayList<Ship> getCurrent() {
		return current;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setX(double x) {
		X = x;
	}

	public void setY(double y) {
		Y = y;
	}

	public void setContainers(ArrayList<Container> containers) {
		this.containers = containers;
	}

	public void setHistory(ArrayList<Ship> history) {
		this.history = history;
	}

	public void setCurrent(ArrayList<Ship> current) {
		this.current = current;
	}

	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

