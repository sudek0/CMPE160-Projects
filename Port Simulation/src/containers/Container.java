
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

public abstract class Container implements Comparable<Container> {
	private int ID;
	private int weight;
	
	public Container(int ID, int weight) {
		this.ID = ID;
		this.weight = weight;
	}
	
	public double consumption() {
		if(this instanceof BasicContainer) {
		    return 2.50*weight;
		}
		else {
			if(this instanceof RefrigeratedContainer) {
				return 5.00*weight;
			}
			else if(this instanceof LiquidContainer) {
				return 4.00*weight;
			}
			else {
				return 3.00*weight;
			}
		}
	}
	
	
	// GET CLASS KULLANDIN BÝ BAK
	public boolean equals(Container other) {
		if(ID == other.ID && weight == other.weight && this.getClass() == other.getClass() ) {
			return true;
		}
		else {
			return false;
		}
	}

	public int compareTo(Container c) {
		if(this.ID < c.ID) {
			return -1;
		}
		if(this.ID > c.ID) {
			return 1;
		}		
		return 0;
	
	}
	
	
	//GETTERS AND SETTERS
	public int getID() {
		return ID;
	}

	public int getWeight() {
		return weight;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

