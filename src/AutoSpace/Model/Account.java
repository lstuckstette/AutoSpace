package AutoSpace.Model;

import java.util.ArrayList;

import org.apache.http.impl.client.BasicCookieStore;

import AutoSpace.Types.ResearchType;

public class Account {

	private ArrayList<Planet> planeten;
	private ArrayList<Research> research;
	private String universe;
	private String username = "";
	private String password = "";
	private BasicCookieStore authentification;

	public Account(String universe, String username, String password) {
		this.universe = universe;
		this.username = username;
		this.password = password;
		
		this.planeten = new ArrayList<Planet>();
		this.research = new ArrayList<>();
		authentification = new BasicCookieStore();
		
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

	public ArrayList<Research> getResearchList() {
		return this.research;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthentification(BasicCookieStore authentification) {
		this.authentification = authentification;
	}

	public BasicCookieStore getAuthentification() {
		return authentification;
	}
}
