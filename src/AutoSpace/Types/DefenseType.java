package AutoSpace.Types;

public enum DefenseType {

	ROCKET_LAUNCHER("401"), LIGHT_LASER("402"), HEAVY_LASER("403"), ION_CANNON("405"), GAUSS_CANNON("404"), PLASMA_TURRET(
			"406"), SMALL_SHIELD_DOME("406"), LARGE_SHIELD_DOME("408"), ANTI_BALLISTIC_MISSILE("502"), INTERPLANETARY_MISSILE("503");

	private String id;

	DefenseType(String id) {
		this.id = id;
	}

	public String id() {
		return id;
	}
}
