package AutoSpace.Model;

import java.util.ArrayList;

import org.apache.http.impl.client.BasicCookieStore;

import AutoSpace.Types.ResearchType;

public class Account {

	private ArrayList<Planet> planeten;
	private ArrayList<Research> research;
	private String playername;
	private String universe;
	private BasicCookieStore authentification;

	public Account(String universe) {
		this.planeten = new ArrayList<Planet>();
		this.research = new ArrayList<>();
		authentification = new BasicCookieStore();
		this.universe = universe;
	}

	public String toString() {
		int totalMetal = 0;
		int totalCrystal = 0;
		int totalDeuterium = 0;
		for (Planet p : planeten) {
			totalMetal += p.getMetal();
			totalCrystal += p.getCrystal();
			totalDeuterium += p.getDeuterium();
		}
		return "##########\nAccountinfo\nUniverse: " + this.universe + "\nPlanetcount: " + planeten.size()
				+ "\nTotal Resources\nMetal: " + totalMetal + "\nCrystal: " + totalCrystal + "\nDeuterium: "
				+ totalDeuterium + "\n##########";
	}

	public void addResearch(Research newResearch) {
		this.research.add(newResearch);
	}

	public Research getResearch(ResearchType type) {
		for (Research r : this.research) {
			if (r.getType() == type) {
				return r;
			}
		}
		return null;
	}

	public int getResearchLevel(ResearchType type) {
		Research r = getResearch(type);
		return r == null ? -1 : r.getLevel();
	}

	public void addPlanet(Planet pnew) {
		this.planeten.add(pnew);
	}

	public ArrayList<Planet> getPlanets() {
		return planeten;
	}

	public Planet getPlanet(String name) {
		for (Planet p : planeten) {
			if (p.getPlanetName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public String getUniverse() {
		return universe;
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public void setAuthentification(BasicCookieStore authentification) {
		this.authentification = authentification;
	}

	public BasicCookieStore getAuthentification() {
		return authentification;
	}
}
