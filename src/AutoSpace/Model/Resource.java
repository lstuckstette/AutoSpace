package AutoSpace.Model;

public class Resource {

	private int metal = 0;
	private int crystal = 0;
	private int deuterium = 0;

	public Resource(double metal, double crystal, double deuterium) {
		this.metal = (int) metal;
		this.crystal = (int) crystal;
		this.deuterium = (int) deuterium;
	}

	public Resource(int metal, int crystal, int deuterium) {
		this.metal = metal;
		this.crystal = crystal;
		this.deuterium = deuterium;
	}

	public Resource() {

	}

	public int getMetal() {
		return metal;
	}

	public void setMetal(int metal) {
		this.metal = metal;
	}

	public int getCrystal() {
		return crystal;
	}

	public void setCrystal(int crystal) {
		this.crystal = crystal;
	}

	public int getDeuterium() {
		return deuterium;
	}

	public void setDeuterium(int deuterium) {
		this.deuterium = deuterium;
	}
	
	public String toString(){
		return "Resource M:"+metal+" - K:"+crystal+" - D:"+deuterium+"\n";
	}

}
