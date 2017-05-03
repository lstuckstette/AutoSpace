package AutoSpace.Model;

import AutoSpace.Types.ResourceBuildingType;

public class ResourceBuilding {

	private ResourceBuildingType type;
	private int level;

	public ResourceBuilding(ResourceBuildingType type, int level) {
		this.type = type;
		this.level = level;
	}

	public ResourceBuildingType getType() {
		return type;
	}

	public void setType(ResourceBuildingType type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
