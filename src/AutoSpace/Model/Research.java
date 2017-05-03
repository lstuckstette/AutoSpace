package AutoSpace.Model;

import AutoSpace.Types.ResearchType;

public class Research {

	private ResearchType type;
	private int level;

	public Research(ResearchType type, int level) {
		this.type = type;
		this.level = level;
	}

	public ResearchType getType() {
		return type;
	}

	public void setType(ResearchType type) {
		this.type = type;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
