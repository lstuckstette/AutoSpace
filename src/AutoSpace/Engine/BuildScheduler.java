package AutoSpace.Engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import AutoSpace.Model.Account;
import AutoSpace.Model.Planet;
import AutoSpace.Types.ResourceBuildingType;

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

	public void shutdownBuildScheduler() {
		scheduledExecutorService.shutdown();
	}
}
