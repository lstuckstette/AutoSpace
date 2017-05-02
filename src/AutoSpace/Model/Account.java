package AutoSpace.Model;

import java.util.ArrayList;

import org.apache.http.impl.client.BasicCookieStore;

public class Account {

	private ArrayList<Planet> planeten;
	private String playername;
	private String universe;
	private BasicCookieStore authentification;

	public Account(String universe) {
		this.planeten = new ArrayList<Planet>();
		authentification = new BasicCookieStore();
		this.universe = universe;
	}

	public String toString() {
		return "##########\nAccountinfo\n\nUniverse: " + this.universe
				+ "\nPlanetcount: " + planeten.size() + "\n##########";
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
