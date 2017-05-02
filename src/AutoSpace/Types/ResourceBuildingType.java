package AutoSpace.Types;

public enum ResourceBuildingType {

	METALMINE("1"), CRYSTALMINE("2"), DEUTERIUMSYNTHESIZER("3"), SOLARPLANT("4"), FUSIONREACTOR("12"), SOLARSATELLITE(
			"212"), METALSTORAGE("22"), CRYSTALSTORAGE("23"), DEUTERIUMTANK("24");

	private String id;

	ResourceBuildingType(String id) {
		this.id = id;
	}

	public String id() {
		return id;
	}
}
