package AutoSpace.Model;

import AutoSpace.Types.ShipType;

public class Ship {

	private String name;
	private int count;
	private ShipType type;

	public Ship(String name, int count, ShipType type) {
		this.name = name;
		this.count = count;
		this.type = type;
	}

	public Ship(int count, ShipType type) {
		this.name = "none";
		this.count = count;
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public ShipType getShipType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
