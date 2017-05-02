package AutoSpace.Model;

import java.util.ArrayList;

import AutoSpace.Types.ShipType;

public class Planet {

	private String planetId;
	private String planetName;
	private Coordinate coordinate;
	private ArrayList<Ship> fleet;
	private int resource_metal;
	private int resource_crystal;
	private int resource_deuterium;
	private int resource_energy;

	public Planet() {
		this.fleet = new ArrayList<Ship>();
	}

	public void addShip(String name, int count, ShipType type) {
		if (count > 0)
			fleet.add(new Ship(name, count,type));
	}

	public int getShipCount(ShipType type) {
		for (Ship s : fleet) {
			if (s.getShipType() == type) {
				return s.getCount();
			}
		}
		return 0;
	}
	
	public boolean equals(Planet p2){
		return this.planetId.equals(p2.getPlanetId());
	}

	public String toString() {
		// Name, Coord, Resources
		String result = "\n----------------\n";
		result += "Planet '" + planetName + "' [" + coordinate + "] - Metall:" + resource_metal + "|Kristall:"
				+ resource_crystal + "|Deuterium:" + resource_deuterium + "|Energie:" + resource_energy + "\n";
		// Fleet

		if (fleet.isEmpty()) {
			result += "No Ships.\n";
		} else {
			result += "Fleet:\n";
			for (Ship s : fleet) {
				result += s.getName() + " : " + s.getCount() + "\n";
			}
		}
		result += "----------------\n";
		return result;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = new Coordinate(coordinate);
	}

	public int getEnergy() {
		return resource_energy;
	}

	public void setEnergy(int resource_energy) {
		this.resource_energy = resource_energy;
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public int getMetal() {
		return resource_metal;
	}

	public void setMetal(int resource_metal) {
		this.resource_metal = resource_metal;
	}

	public int getCrystal() {
		return resource_crystal;
	}

	public void setCrystal(int resource_crystal) {
		this.resource_crystal = resource_crystal;
	}

	public int getDeuterium() {
		return resource_deuterium;
	}

	public void setDeuterium(int resource_deuterium) {
		this.resource_deuterium = resource_deuterium;
	}

	public String getPlanetId() {
		return planetId;
	}

	public void setPlanetId(String planetId) {
		this.planetId = planetId;
	}
	
}
