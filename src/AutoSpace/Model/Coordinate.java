package AutoSpace.Model;

public class Coordinate {
	private int galaxy;
	private int system;
	private int position;

	public Coordinate(String coordinate) {

		coordinate = coordinate.replace("[", "");
		coordinate = coordinate.replace("]", "");

		if (coordinate.matches("\\d+:\\d+:\\d+")) {
			String[] split = coordinate.split(":");
			galaxy = Integer.parseInt(split[0]);
			system = Integer.parseInt(split[1]);
			position = Integer.parseInt(split[2]);
		}
	}

	public Coordinate(int galaxy, int system, int position) {
		this.galaxy = galaxy;
		this.system = system;
		this.position = position;
	}

	public int getGalaxy() {
		return galaxy;
	}

	public void setGalaxy(int galaxy) {
		this.galaxy = galaxy;
	}

	public int getSystem() {
		return system;
	}

	public void setSystem(int system) {
		this.system = system;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String toString() {
		return galaxy + ":" + system + ":" + position;
	}

}
