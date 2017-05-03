package AutoSpace.Model;

import AutoSpace.Types.FacilityBuildingType;

public class FacilityBuilding {

	private FacilityBuildingType type;
	private int level;

	public FacilityBuilding(FacilityBuildingType type, int level) {
		this.type = type;
		this.level = level;
	}

	public FacilityBuildingType getType() {
		return type;
	}

	public void setType(FacilityBuildingType type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
