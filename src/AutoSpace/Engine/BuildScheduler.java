package AutoSpace.Engine;

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

public class BuildScheduler {

	private ScheduledExecutorService scheduledExecutorService;
	private Builder builder;

	public BuildScheduler(Account account) {
		scheduledExecutorService = Executors.newScheduledThreadPool(1);
		builder = new Builder(account);
	}

	// TODO: implement specific methods for building buildings/start research;
	// check login(!!!)
	public void scheduleBuildResourceBuilding(Planet target, ResourceBuildingType building, int delaySeconds) {
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {

				builder.buildResourceBuilding(target, building);

			}

		}, delaySeconds, TimeUnit.SECONDS);
	}
	public void scheduleBuildFacilityBuilding(Planet target, FacilityBuildingType building, int delaySeconds){
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {

				builder.buildFacilityBuilding(target, building);

			}

		}, delaySeconds, TimeUnit.SECONDS);
	}
	
	public void scheduleStartResearch(Planet target, ResearchType research, int delaySeconds){
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {

				builder.startResearch(target, research);

			}

		}, delaySeconds, TimeUnit.SECONDS);
	}
	
	public void scheduleBuildDefense(Planet target, DefenseType defense, int count, int delaySeconds){
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {

				builder.buildDefense(target, defense, count);

			}

		}, delaySeconds, TimeUnit.SECONDS);
	}
	
	public void scheduleBuildShip(Planet target, ShipType ship, int count, int delaySeconds){
		scheduledExecutorService.schedule(new Runnable() {

			@Override
			public void run() {

				builder.buildShip(target, ship, count);

			}

		}, delaySeconds, TimeUnit.SECONDS);
	}

	public void shutdownBuildScheduler() {
		scheduledExecutorService.shutdown();
	}
}
