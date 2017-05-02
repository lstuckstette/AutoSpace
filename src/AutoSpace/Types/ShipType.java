package AutoSpace.Types;

public enum ShipType {

	SMALL_CARGO_SHIP("202"), LARGE_CARGO_SHIP("203"), LIGHT_FIGHTER("204"), HEAVY_FIGHTER("205"), CRUISER(
			"206"), BATTLESHIP("207"), BATTLECRUISER("215"), DESTROYER(
					"213"), DEATHSTAR("214"), BOMBER("211"), RECYCLER("209"), ESPIONAGE_PROBE("210"), COLONY_SHIP("208");

	private String id;

	ShipType(String id) {
		this.id = id;
	}

	public String id() {
		return id;
	}
}
