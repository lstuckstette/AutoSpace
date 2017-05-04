package AutoSpace.Engine;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import AutoSpace.Model.Account;
import AutoSpace.Model.Planet;
import AutoSpace.Types.DefenseType;
import AutoSpace.Types.FacilityBuildingType;
import AutoSpace.Types.ResearchType;
import AutoSpace.Types.ResourceBuildingType;
import AutoSpace.Types.ShipType;

public class TaskScheduler {

	private ScheduledExecutorService scheduledExecutorService;
	private Builder builder;
	private Account account;
	private static final int GENERAL_DELAY_SECONDS = 10;
	private long currentBuildingDelay = 0;
	private long currentResearchDelay = 0;
	private long currentShipDelay = 0;

	public TaskScheduler(Account account) {
		scheduledExecutorService = Executors.newScheduledThreadPool(1);
		builder = new Builder(account);
		this.account = account;
	}

	public void scheduleBuildResourceBuilding(Planet target, ResourceBuildingType building, int delaySeconds) {
		currentBuildingDelay += delaySeconds;
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {
				new Authentification(account).login();
				builder.buildResourceBuilding(target, building);
				currentBuildingDelay -= delaySeconds;
			}

		}, currentBuildingDelay + delaySeconds + GENERAL_DELAY_SECONDS, TimeUnit.SECONDS);
	}

	public void scheduleBuildFacilityBuilding(Planet target, FacilityBuildingType building, int delaySeconds) {
		currentBuildingDelay += delaySeconds;
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {
				new Authentification(account).login();
				builder.buildFacilityBuilding(target, building);
				currentBuildingDelay -= delaySeconds;
			}

		}, currentBuildingDelay + delaySeconds + GENERAL_DELAY_SECONDS, TimeUnit.SECONDS);
	}

	public void scheduleStartResearch(Planet target, ResearchType research, int delaySeconds) {
		currentResearchDelay += delaySeconds;
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {
				new Authentification(account).login();
				builder.startResearch(target, research);
				currentResearchDelay -= delaySeconds;
			}

		}, currentResearchDelay + delaySeconds + GENERAL_DELAY_SECONDS, TimeUnit.SECONDS);
	}

	public void scheduleBuildDefense(Planet target, DefenseType defense, int count, int delaySeconds) {
		currentShipDelay += delaySeconds;
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {
				new Authentification(account).login();
				builder.buildDefense(target, defense, count);
				currentShipDelay -= delaySeconds;
			}

		}, currentShipDelay + delaySeconds + GENERAL_DELAY_SECONDS, TimeUnit.SECONDS);
	}

	public void scheduleBuildShip(Planet target, ShipType ship, int count, int delaySeconds) {
		currentShipDelay += delaySeconds;
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {
				new Authentification(account).login();
				builder.buildShip(target, ship, count);
				currentShipDelay -= delaySeconds;
			}

		}, delaySeconds + GENERAL_DELAY_SECONDS, TimeUnit.SECONDS);
	}

	public void scheduleSavingDaemon() {
		int initialDelay = 0;
		int period = 30; // min
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				new Authentification(account).login();
				FleetManager fm = new FleetManager(account);
				if (fm.isUnderAttack()) {
					// save-style 1: send all shipable res very slowly to next
					// planet in list:
					ArrayList<Planet> planets = account.getPlanets();
					if (planets.size() > 1) {
						// TODO <->
						for (int i = 0; i < planets.size(); i++) {
							int nextPlanet = i == planets.size() - 1 ? 0 : i + 1;
							fm.sendTransport(planets.get(i), planets.get(nextPlanet), 3, planets.get(i).getMetal(),
									planets.get(i).getCrystal(), planets.get(i).getDeuterium());
						}
					} else {
						System.err.println("SavingDaemon: UNABLE TO SAVE! TOO LITTLE PLANETS.");
					}
				}
			}

		}, initialDelay, period, TimeUnit.MINUTES);
	}

	public void shutdownTaskScheduler() {
		scheduledExecutorService.shutdown();
	}
}
