package AutoSpace.Engine;

import AutoSpace.Model.Account;
import AutoSpace.Model.Planet;
import AutoSpace.Model.Resource;
import AutoSpace.Types.DefenseType;
import AutoSpace.Types.FacilityBuildingType;
import AutoSpace.Types.ResearchType;
import AutoSpace.Types.ResourceBuildingType;
import AutoSpace.Types.ShipType;

public final class EntityCost {

	private EntityCost() {

	}

	public static Resource getResourceBuildingCost(ResourceBuildingType building, int level) {

		switch (building) {
		case METALMINE:
			return new Resource(40 * Math.pow(1.5, level), 10 * Math.pow(1.5, level), 0);
		case CRYSTALMINE:
			return new Resource(30 * Math.pow(1.6, level), 15 * Math.pow(1.6, level), 0);
		case DEUTERIUMSYNTHESIZER:
			return new Resource(150 * Math.pow(1.5, level), 50 * Math.pow(1.5, level), 0);
		case SOLARPLANT:
			return new Resource(50 * Math.pow(1.5, level), 20 * Math.pow(1.5, level), 0);
		case FUSIONREACTOR:
			return new Resource(500 * Math.pow(1.8, level), 200 * Math.pow(1.8, level), 100 * Math.pow(1.8, level));
		case SOLARSATELLITE:
			return new Resource(0, 2000, 500);
		case METALSTORAGE:
			return new Resource(500 * Math.pow(2, level), 0, 0);
		case CRYSTALSTORAGE:
			return new Resource(500 * Math.pow(2, level), 250 * Math.pow(2, level), 0);
		case DEUTERIUMTANK:
			return new Resource(500 * Math.pow(2, level), 500 * Math.pow(2, level), 0);
		default:
			return new Resource();
		}

	}

	public static Resource getFacilityBuildingCost(FacilityBuildingType building, int level) {
		switch (building) {
		case ROBOTICFACTORY:
			return new Resource(200 * Math.pow(2, level), 60 * Math.pow(2, level), 100 * Math.pow(2, level));
		case SHIPYARD:
			return new Resource(200 * Math.pow(2, level), 100 * Math.pow(2, level), 50 * Math.pow(2, level));
		case RESEARCHLAB:
			return new Resource(100 * Math.pow(2, level), 200 * Math.pow(2, level), 100 * Math.pow(2, level));
		case ALLIANCEDEPOT:
			return new Resource(10000 * Math.pow(2, level), 20000 * Math.pow(2, level), 0);
		case MISSILESILO:
			return new Resource(10000 * Math.pow(2, level), 10000 * Math.pow(2, level), 500 * Math.pow(2, level));
		case NANITEFACTORY:
			return new Resource(500000 * Math.pow(2, level), 250000 * Math.pow(2, level), 50000 * Math.pow(2, level));
		case TERRAFORMER:
			return new Resource(0, 25000 * Math.pow(2, level), 50000 * Math.pow(2, level));
		case SPACEDOCK:
			return new Resource(40 * Math.pow(5, level), 0, 10 * Math.pow(5, level));
		default:
			return new Resource();
		}

	}

	public static Resource getResearchCost(ResearchType research, int level) {
		switch (research) {
		case ESPIONAGE_TECHNOLOGY:
			return new Resource(100 * Math.pow(2, level), 500 * Math.pow(2, level), 100 * Math.pow(2, level));
		case COMPUTER_TECHNOLOGY:
			return new Resource(0, 200 * Math.pow(2, level), 300 * Math.pow(2, level));
		case WEAPON_TECHNOLOGY:
			return new Resource(400 * Math.pow(2, level), 100 * Math.pow(2, level), 0);
		case SHIELDING_TECHNOLOGY:
			return new Resource(100 * Math.pow(2, level), 300 * Math.pow(2, level), 0);
		case ARMOUR_TECHNOLOGY:
			return new Resource(500 * Math.pow(2, level), 0, 0);
		case ENERGY_TECHNOLOGY:
			return new Resource(0, 400 * Math.pow(2, level), 200 * Math.pow(2, level));
		case HYPERSPACE_TECHNOLOGY:
			return new Resource(0, 2000 * Math.pow(2, level), 1000 * Math.pow(2, level));
		case COMBUSTION_DRIVE:
			return new Resource(200 * Math.pow(2, level), 0, 300 * Math.pow(2, level));
		case IMPULSE_DRIVE:
			return new Resource(1000 * Math.pow(2, level), 2000 * Math.pow(2, level), 300 * Math.pow(2, level));
		case HYPERSPACE_DRIVE:
			return new Resource(5000 * Math.pow(2, level), 10000 * Math.pow(2, level), 3000 * Math.pow(2, level));
		case LASER_TECHNOLOGY:
			return new Resource(100 * Math.pow(2, level), 50 * Math.pow(2, level), 0);
		case ION_TECHNOLOGY:
			return new Resource(500 * Math.pow(2, level), 150 * Math.pow(2, level), 50 * Math.pow(2, level));
		case PLASMA_TECHNOLOGY:
			return new Resource(1000 * Math.pow(2, level), 2000 * Math.pow(2, level), 500 * Math.pow(2, level));
		case INTERGALACTIC_RESEARCH_NETWORK:
			return new Resource(120000 * Math.pow(2, level), 200000 * Math.pow(2, level), 80000 * Math.pow(2, level));
		case ASTROPHYSICS:
			double metal = 0.5 + 40 * Math.pow(1.75, level - 1);
			double crystal = 0.5 + 80 * Math.pow(1.75, level - 1);
			int metalInt = (int) metal;
			int crystalInt = (int) crystal;
			return new Resource(metalInt * 100, crystalInt * 100, metalInt * 100);
		case GRAVITON_TECHNOLOGY:
			return new Resource();
		default:
			return new Resource();
		}
	}

	public static Resource getShipCost(ShipType ship, int count) {
		switch (ship) {
		case SMALL_CARGO_SHIP:
			return new Resource(2000 * count, 2000 * count, 0);
		case LARGE_CARGO_SHIP:
			return new Resource(6000 * count, 6000 * count, 0);
		case LIGHT_FIGHTER:
			return new Resource(3000 * count, 1000 * count, 0);
		case HEAVY_FIGHTER:
			return new Resource(6000 * count, 4000 * count, 0);
		case CRUISER:
			return new Resource(20000 * count, 7000 * count, 2000 * count);
		case BATTLESHIP:
			return new Resource(45000 * count, 15000 * count, 0);
		case BATTLECRUISER:
			return new Resource(30000 * count, 40000 * count, 15000 * count);
		case DESTROYER:
			return new Resource(60000 * count, 50000 * count, 15000 * count);
		case DEATHSTAR:
			return new Resource(5000000 * count, 4000000 * count, 1000000 * count);
		case BOMBER:
			return new Resource(50000 * count, 25000 * count, 15000 * count);
		case RECYCLER:
			return new Resource(10000 * count, 6000 * count, 2000 * count);
		case ESPIONAGE_PROBE:
			return new Resource(0, 1000 * count, 0);
		case COLONY_SHIP:
			return new Resource(10000 * count, 20000 * count, 10000 * count);
		default:
			return new Resource();
		}
	}

	public static Resource getDefenseCost(DefenseType defense, int count) {
		switch (defense) {
		case ROCKET_LAUNCHER:
			return new Resource(2000 * count, 0, 0);
		case LIGHT_LASER:
			return new Resource(1500 * count, 500 * count, 0);
		case HEAVY_LASER:
			return new Resource(6000 * count, 2000 * count, 0);
		case ION_CANNON:
			return new Resource(2000 * count, 6000 * count, 0);
		case GAUSS_CANNON:
			return new Resource(20000 * count, 15000 * count, 2000 * count);
		case PLASMA_TURRET:
			return new Resource(50000 * count, 50000 * count, 30000 * count);
		case SMALL_SHIELD_DOME:
			return new Resource(10000, 10000, 0);
		case LARGE_SHIELD_DOME:
			return new Resource(50000, 50000, 0);
		case ANTI_BALLISTIC_MISSILE:
			return new Resource(8000 * count, 0, 2000 * count);
		case INTERPLANETARY_MISSILE:
			return new Resource(12500 * count, 2500 * count, 10000 * count);
		default:
			return new Resource();
		}
	}

	public static int getBuildingBuildDuration(Resource cost, int levelRoboticFactory, int levelNaniteFactory,
			int levelBuilding, int unispeed) {

		double seconds = (cost.getMetal() + cost.getCrystal()) * 1.44 / Math.max(4 - (levelBuilding / 2), 1)
				/ (1 + levelRoboticFactory) / Math.pow(2, levelNaniteFactory) / unispeed;

		return (int) Math.round(seconds);
	}

	public static int getResearchDuration(Resource cost, int levelResearchLab) {

		float timeDecimal = (cost.getMetal() + cost.getCrystal()) / (1000 * (1 + levelResearchLab));

		int hours = (int) timeDecimal;
		int minutes = (int) (timeDecimal * 60) % 60;
		int seconds = (int) (timeDecimal * (60 * 60)) % 60;
		int totalseconds = hours * 60 * 60 + minutes * 60 + seconds;
		return totalseconds;
	}

	public static int getShipBuildDuration(Resource cost, int levelShipyard, int levelNaniteFactory) {
		double timeDecimal = (cost.getMetal() + cost.getCrystal())
				/ (2500 * (1 + levelShipyard) * Math.pow(2, levelNaniteFactory));

		int hours = (int) timeDecimal;
		int minutes = (int) (timeDecimal * 60) % 60;
		int seconds = (int) (timeDecimal * (60 * 60)) % 60;
		int totalseconds = hours * 60 * 60 + minutes * 60 + seconds;
		return totalseconds;
	}

	public static int getCrystalProductionPerHour(Account acc, Planet p) {
		int levelCM = p.getResourceBuildingLevel(ResourceBuildingType.CRYSTALMINE);
		int levelPT = acc.getResearchLevel(ResearchType.PLASMA_TECHNOLOGY);
		int grundProd = 15;
		double prod = (20 * levelCM * Math.pow(1.1, levelCM)) + grundProd * ((100 + 0.66 * levelPT) / 100);
		return (int) prod;
	}

	public static int getMetalProductionPerHour(Account acc, Planet p) {
		int levelMM = p.getResourceBuildingLevel(ResourceBuildingType.METALMINE);
		int levelPT = acc.getResearchLevel(ResearchType.PLASMA_TECHNOLOGY);
		int grundProd = 30;
		double prod = (30 * levelMM * Math.pow(1.1, levelMM)) + grundProd * ((100 + levelPT) / 100);
		return (int) prod;
	}

	// worst-case;
	public static int getDeuteriumProductionPerHour(Planet p) {
		int levelDM = p.getResourceBuildingLevel(ResourceBuildingType.DEUTERIUMSYNTHESIZER);
		int levelFR = p.getResourceBuildingLevel(ResourceBuildingType.FUSIONREACTOR);
		int position = p.getCoordinate().getPosition();
		double fusionConsumption = 10 * levelFR * Math.pow(1.1, levelFR);
		int highTemp;
		switch (position) {
		case 1:
			highTemp = 260;
			break;
		case 2:
			highTemp = 210;
			break;
		case 3:
			highTemp = 160;
			break;
		case 4:
			highTemp = 110;
			break;
		case 5:
			highTemp = 100;
			break;
		case 6:
			highTemp = 90;
			break;
		case 7:
			highTemp = 80;
			break;
		case 8:
			highTemp = 70;
			break;
		case 9:
			highTemp = 60;
			break;
		case 10:
			highTemp = 50;
			break;
		case 11:
			highTemp = 40;
			break;
		case 12:
			highTemp = 30;
			break;
		case 13:
			highTemp = -10;
			break;
		case 14:
			highTemp = -50;
			break;
		case 15:
			highTemp = -90;
			break;
		default:
			highTemp = 0;
		}

		double prod = 10 * levelDM * Math.pow(1.1, levelDM) * (1.44 - 0.004 * highTemp);
		return (int) (prod - fusionConsumption);
	}

	public static int getBuildingEnergyCost(ResourceBuildingType type, int level) {
		switch (type) {
		case METALMINE:
			return (int) (10 * level * Math.pow(1.1, level));
		case CRYSTALMINE:
			return (int) (10 * level * Math.pow(1.1, level));
		case DEUTERIUMSYNTHESIZER:
			return (int) (20 * level * Math.pow(1.1, level));
		default:
			return 0;
		}
	}

	public static int getSecondsUntilBuildPossible(Account acc, Planet p, Resource cost) {

		int currentMetal = p.getMetal();
		int currentCrystal = p.getCrystal();
		int currentDeut = p.getDeuterium();

		double metalTimeHours = (cost.getMetal() - currentMetal) / getMetalProductionPerHour(acc, p);
		int metalTimeSeconds = decimalHoursToSeconds(metalTimeHours);

		double crystalTimeHours = (cost.getCrystal() - currentCrystal) / getCrystalProductionPerHour(acc, p);
		int crystalTimeSeconds = decimalHoursToSeconds(crystalTimeHours);

		double deutTimeHours = (cost.getDeuterium() - currentDeut) / getDeuteriumProductionPerHour(p);
		int deutTimeSeconds = decimalHoursToSeconds(deutTimeHours);

		return Math.max(Math.max(metalTimeSeconds, crystalTimeSeconds), deutTimeSeconds);
	}

	public static int decimalHoursToSeconds(double timeDecimal) {
		int hours = (int) timeDecimal;
		int minutes = (int) (timeDecimal * 60) % 60;
		int seconds = (int) (timeDecimal * (60 * 60)) % 60;
		int totalseconds = hours * 60 * 60 + minutes * 60 + seconds;
		return totalseconds;
	}
}
