package AutoSpace.Types;

public enum FacilityBuildingType {

	ROBOTICFACTORY("14"), SHIPYARD("21"), RESEARCHLAB("31"), ALLIANCEDEPOT("34"), MISSILESILO("44"), NANITEFACTORY(
			"15"), TERRAFORMER("33"), SPACEDOCK("36");

	private String id;

	FacilityBuildingType(String id) {
		this.id = id;
	}

	public String id() {
		return id;
	}
}
