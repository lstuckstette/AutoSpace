package AutoSpace.Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import AutoSpace.Model.Account;
import AutoSpace.Model.Planet;
import AutoSpace.Model.Ship;
import AutoSpace.Types.ShipType;

public class FleetManager {

	private Account account;
	private static final Logger LOG = Logger.getLogger(FleetManager.class.getName());
	private static final int CONSUMPTION = 500;

	public FleetManager(Account account) {
		this.account = account;
	}

	public void sendExpedition(Planet planet, int transporterCount) {

		int currentGalaxy = planet.getCoordinate().getGalaxy();
		int currentSystem = planet.getCoordinate().getSystem();
		int currentPosition = planet.getCoordinate().getPosition();

		// switch to desired Planet
		String query0 = "/game/index.php?page=overview&cp=" + planet.getPlanetId();
		String html1 =getPageHTML(query0);

		System.out.println("reached! - Size:" +html1.length());
		// Choose Ship
		String query1 = "/game/index.php?page=fleet2&galaxy=" + currentGalaxy + "&system=" + currentSystem
				+ "&position=" + currentPosition + "&type=1&mission=0&speed=10&am203=" + transporterCount;
		String html2 =getPageHTML(query1);

		// Fleetcheck -> really needed?
		String query2 = "/game/index.php?page=fleetcheck&ajax=1&espionage=0" + "&galaxy=" + currentGalaxy + "&system="
				+ currentSystem + "&planet=16&type=1";
		String html3 =getPageHTML(query2);

		// Briefing
		String query3 = "/game/index.php?page=fleet3&type=1&mission=15&union=0&am203=" + transporterCount + "&galaxy="
				+ currentGalaxy + "&system=" + currentSystem + "&position=16&acsValues=-&speed=10";

		String q3HTML = getPageHTML(query3);

		// Start flight
		String token = extractToken(q3HTML);

		try {
			HttpUriRequest request = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=movement"))
					.addParameter("holdingtime", "1").addParameter("expeditiontime", "1").addParameter("token", token)
					.addParameter("galaxy", String.valueOf(currentGalaxy))
					.addParameter("system", String.valueOf(currentSystem)).addParameter("position", "16")
					.addParameter("type", "1").addParameter("mission", "15").addParameter("union2", "0")
					.addParameter("holdingOrExpTime", "").addParameter("speed", "10").addParameter("acsValues", "-")
					.addParameter("prioMetal", "1").addParameter("prioCrystal", "2").addParameter("prioDeuterium", "3")
					.addParameter("am203", String.valueOf(transporterCount)).addParameter("metal", "0")
					.addParameter("crystal", "0").addParameter("deuterium", "0").build();
			postData(request);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendTransport(Planet source, Planet target, int speed, int metal, int crystal, int deuterium) {

		
		
		
		String startGalaxy = String.valueOf(source.getCoordinate().getGalaxy());
		String startSystem = String.valueOf(source.getCoordinate().getSystem());
		String startPosition = String.valueOf(source.getCoordinate().getPosition());

		String targetGalaxy = String.valueOf(target.getCoordinate().getGalaxy());
		String targetSystem = String.valueOf(target.getCoordinate().getSystem());
		String targetPosition = String.valueOf(target.getCoordinate().getPosition());

		// check speed in bounds
		speed = speed < 1 || speed > 10 ? 10 : speed;

		// calculate required GT' count ; handle too little GTs
		String transporterCount = "";
		int requiredGTs = ((metal + crystal + deuterium) / 25000) + 1;
		int presentGTs = source.getShipCount(ShipType.LARGE_CARGO_SHIP);
		if (presentGTs < requiredGTs) {
			// if to little large cargos -> reduce Resources
			transporterCount = String.valueOf(presentGTs);
			int maxSpace = presentGTs * 25000 - CONSUMPTION;
			deuterium = maxSpace < source.getDeuterium() ? maxSpace : source.getDeuterium();
			maxSpace -= deuterium;
			crystal = maxSpace < source.getCrystal() ? maxSpace : source.getCrystal();
			maxSpace -= crystal;
			metal = maxSpace;

		} else {
			// all fine
			transporterCount = String.valueOf(requiredGTs);
			deuterium -= CONSUMPTION;
		}
		// <--

		// switch to desired Planet
		String query0 = "/game/index.php?page=fleet1&cp=" + source.getPlanetId();
		getPageHTML(query0);
		// Choose Ship
		String query1 = "/game/index.php?page=fleet2&galaxy=" + startGalaxy + "&system=" + startSystem + "&position="
				+ startPosition + "&type=1&mission=0&speed=" + String.valueOf(speed) + "&am203=" + transporterCount;
		getPageHTML(query1);
		// Briefing
		String query3 = "/game/index.php?page=fleet3&type=1&mission=0&union=0&am203=" + transporterCount + "&galaxy="
				+ targetGalaxy + "&system=" + targetSystem + "&position=" + targetPosition + "&acsValues=-&speed="
				+ String.valueOf(speed);
		String q3HTML = getPageHTML(query3);
		// Start flight
		String token = extractToken(q3HTML);

		try {
			HttpUriRequest request = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=movement"))
					.addParameter("holdingtime", "1").addParameter("expeditiontime", "1").addParameter("token", token)
					.addParameter("galaxy", targetGalaxy).addParameter("system", targetSystem)
					.addParameter("position", targetPosition).addParameter("type", "1").addParameter("mission", "3")
					.addParameter("union2", "0").addParameter("holdingOrExpTime", "0")
					.addParameter("speed", String.valueOf(speed)).addParameter("acsValues", "-")
					.addParameter("prioMetal", "1").addParameter("prioCrystal", "2").addParameter("prioDeuterium", "3")
					.addParameter("am203", transporterCount).addParameter("metal", String.valueOf(metal))
					.addParameter("crystal", String.valueOf(crystal))
					.addParameter("deuterium", String.valueOf(deuterium)).build();
			postData(request);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void gatherResources(Planet target) {
		for (Planet p : account.getPlanets()) {
			if (!p.equals(target)) {
				sendTransport(p, target,10, p.getMetal(), p.getCrystal(), p.getDeuterium());
			}
		}
	}

	public void sendAttack(Planet source, Planet target, ArrayList<Ship> fleet) {

		String startGalaxy = String.valueOf(source.getCoordinate().getGalaxy());
		String startSystem = String.valueOf(source.getCoordinate().getSystem());
		String startPosition = String.valueOf(source.getCoordinate().getPosition());

		String targetGalaxy = String.valueOf(target.getCoordinate().getGalaxy());
		String targetSystem = String.valueOf(target.getCoordinate().getSystem());
		String targetPosition = String.valueOf(target.getCoordinate().getPosition());

		// switch to desired Planet
		String query0 = "/game/index.php?page=overview&cp=" + source.getPlanetId();
		getPageHTML(query0);

		// build ships query
		String ships = "";
		for (Ship p : fleet)
			ships += "am" + p.getShipType().id() + "=" + p.getCount() + "&";

		// Choose Ships
		String query1 = "/game/index.php?page=fleet2&galaxy=" + startGalaxy + "&system=" + startSystem + "&position="
				+ startPosition + "&type=1&mission=0&speed=10&" + ships;
		getPageHTML(query1);

		// Briefing
		String query3 = "/game/index.php?page=fleet3&type=1&mission=0&union=0&" + ships + "galaxy=" + targetGalaxy
				+ "&system=" + targetSystem + "&position=" + targetPosition + "&acsValues=-&speed=10";
		String q3HTML = getPageHTML(query3);

		// Start flight
		String token = extractToken(q3HTML);

		try {

			RequestBuilder rb = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=movement"))
					.addParameter("holdingtime", "1").addParameter("expeditiontime", "1").addParameter("token", token)
					.addParameter("galaxy", targetGalaxy).addParameter("system", targetSystem)
					.addParameter("position", targetPosition).addParameter("type", "1").addParameter("mission", "1")
					.addParameter("union2", "0").addParameter("holdingOrExpTime", "0").addParameter("speed", "10")
					.addParameter("acsValues", "-").addParameter("prioMetal", "1").addParameter("prioCrystal", "2")
					.addParameter("prioDeuterium", "3").addParameter("metal", "0").addParameter("crystal", "0")
					.addParameter("deuterium", "0");

			for (Ship s : fleet)
				rb.addParameter("am" + s.getShipType().id(), String.valueOf(s.getCount()));

			rb.addParameter("metal", "0");
			rb.addParameter("crystal", "0");
			rb.addParameter("deuterium", "0");

			postData(rb.build());

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String extractToken(String html) {
		Document doc = Jsoup.parse(html);
		Elements tokenNode = doc.select("input[name=token]");
		if (tokenNode.size() > 0) {
			return tokenNode.first().attr("value");
		}
		return "";
	}

	public boolean isUnderAttack() {

		String overviewHTML = getPageHTML("/game/index.php?page=overview");
		Document doc = Jsoup.parse(overviewHTML);
		Elements attackNode = doc.select("div.tooltip.eventToggle.soon");
		if (attackNode.size() > 0) {
			return true;
		} else if (doc.select("div.tooltip.eventToggle.noAttack").size() > 0) {
			return false;
		}
		System.err.println("FleetManager.isUnderAttack() - UNKNOWN ATTACK STATE!");
		return true;
	}

	private void postData(HttpUriRequest request) {
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(account.getAuthentification())
				.build();
		try {

			CloseableHttpResponse response2 = httpclient.execute(request);
			try {
				HttpEntity entity = response2.getEntity();

				LOG.fine("Send fleet Response: " + response2.getStatusLine());
				EntityUtils.consume(entity);

			} finally {
				response2.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getPageHTML(String page) {
		return getPageHTML(page, "none");
	}

	@SuppressWarnings("deprecation")
	private String getPageHTML(String page, String filename) {
		StringBuffer result = null;
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(account.getAuthentification())
				.build();
		try {
			HttpGet httpget = new HttpGet("https://" + account.getUniverse() + page);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				LOG.fine("Response Status : " + response.getStatusLine());

				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line + "\n");
				}
				// System.out.println("RECEIVED Bytes: "+result.length());
				if (!filename.equals("none"))
					FileUtils.writeStringToFile(new File(filename), result.toString());

			} finally {
				response.close();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result.toString();
	}

}
