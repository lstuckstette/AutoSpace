package AutoSpace.Engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import AutoSpace.Model.Account;

public class Controller {

	public Controller() {

		// Read Config File:
		Properties config = readProperties();

		// create account
		Account account = new Account(config.getProperty("universe"));
		// login account
		Authentification auth = new Authentification(account, config.getProperty("username"),
				config.getProperty("password"));
		auth.login();

		if (!auth.wasSuccessful()) {
			System.err.println("UNABLE TO LOGIN. CHECK ENTERED DATA. ABORTING.");
			System.exit(0);
		}
		// gather information
		Extractor extractor = new Extractor(account);
		extractor.gatherInformation();

		System.out.println(account.toString());
		
		

		// Example: Send attack
		// FleetManager fleet = new FleetManager(account);
		// Planet target = new Planet();
		// target.setCoordinate("4:194:6");
		// ArrayList<Ship> attackFleet = new ArrayList<Ship>();
		// attackFleet.add(new Ship(1, ShipType.LARGE_CARGO_SHIP));
		//
		// fleet.sendAttack(account.getPlanet("Pentos"), target, attackFleet);

		// fleet.sendExpedition(account.getPlanet("Volantis"),1);

		// fleet.sendTransport(account.getPlanet("Volantis"),
		// account.getPlanet("Pentos"), 15000, 4000, 2500);

		//fleet.gatherResources(account.getPlanet("Volantis"));

		// GalaxyView gv = new GalaxyView(account);
		// ArrayList<Planet> inactivePlanets =
		// gv.getInactivePlanets(account.getPlanet("Volantis"), 1);
		// System.out.println("inactive Planets:");
		// for(Planet p : inactivePlanets){
		// System.out.println(p.getCoordinate().toString()+" -
		// "+p.getPlanetName());
		// }

		// Builder builder = new Builder(account);
		// builder.buildResourceBuilding(account.getPlanet("Bravos"),
		// ResourceBuildingType.SOLARPLANT);

		// builder.buildFacilityBuilding(account.getPlanet("Bravos"),
		// FacilityBuildingType.ROBOTICFACTORY);

		// builder.startResearch(account.getPlanet("Volantis"),
		// ResearchType.WEAPON_TECHNOLOGY);

		// builder.buildShip(account.getPlanet("Volantis"),
		// ShipType.ESPIONAGE_PROBE, 1);
		
		//BuildScheduler bs = new BuildScheduler(account);
		//bs.scheduleBuildResourceBuilding(account.getPlanet("Pentos"), ResourceBuildingType.FUSIONREACTOR, 10);
		//bs.shutdownBuildScheduler();

		System.out.println("Done.");
	}

	private Properties readProperties() {
		Properties config = new Properties();
		try {
			config.load(new FileInputStream("config.cfg"));
		} catch (FileNotFoundException e) {
			System.err.println("CONFIG FILE NOT FOUND! (did you rename config.cfg.example ?)");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return config;
	}

	public static void main(String[] args) {
		new Controller();
	}

}
