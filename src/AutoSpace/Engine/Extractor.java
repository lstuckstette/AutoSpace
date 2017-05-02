package AutoSpace.Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import AutoSpace.Model.Account;
import AutoSpace.Model.Planet;
import AutoSpace.Types.ShipType;

public class Extractor {

	private static final Logger LOG = Logger.getLogger(Extractor.class.getName());
	private BasicCookieStore authentification;
	private Account account;

	public Extractor(Account account) {
		this.authentification = account.getAuthentification();
		this.account = account;
	}

	public void gatherInformation() {
		// Get Planet IDs
		String generalOverviewHTML = getPageHTML("/game/index.php?page=overview");
		ArrayList<String> planetids = getPlanetIds(generalOverviewHTML);
		// Loop through IDs and gather information

		for (String planetid : planetids) {
			Planet p = new Planet();
			String planetOverviewHTML = getPageHTML("/game/index.php?page=overview&cp=" + planetid);
			String fleetHTML = getPageHTML("/game/index.php?page=fleet1&cp=" + planetid);
			parsePlanetOverview(planetOverviewHTML, p);
			parseFleet(fleetHTML, p);
			account.addPlanet(p);
			LOG.fine(p.toString());
		}
	}

	private String getPageHTML(String page) {
		return getPageHTML(page, "none");
	}

	@SuppressWarnings("deprecation")
	private String getPageHTML(String page, String filename) {
		StringBuffer result = null;
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(authentification).build();
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

	private ArrayList<String> getPlanetIds(String html) {
		ArrayList<String> planetids = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Elements planets = doc.select("div.smallplanet");

		for (Element e : planets) {
			if (e.hasAttr("id")) {
				String s = e.attr("id");
				s = s.replaceAll("planet-", "");
				planetids.add(s);
			}
		}
		return planetids;
	}

	private void parsePlanetOverview(String html, Planet planet) {
		Document doc = Jsoup.parse(html);

		String metal = doc.select("span#resources_metal").first().text().replace(".", "");
		String crystal = doc.select("span#resources_crystal").first().text().replace(".", "");
		String deuterium = doc.select("span#resources_deuterium").first().text().replace(".", "");
		String energy = doc.select("span#resources_energy").first().text();
		String planetName = doc.select("[name=ogame-planet-name]").first().attr("content");
		String coordinate = doc.select("[name=ogame-planet-coordinates]").first().attr("content");
		String planetId = doc.select("[name=ogame-planet-id]").first().attr("content");

		planet.setPlanetId(planetId);
		planet.setMetal(Integer.parseInt(metal));
		planet.setCrystal(Integer.parseInt(crystal));
		planet.setDeuterium(Integer.parseInt(deuterium));
		planet.setEnergy(Integer.parseInt(energy));
		planet.setPlanetName(planetName);
		planet.setCoordinate(coordinate);

		// https://jsoup.org/cookbook/extracting-data/dom-navigation

		// https://jsoup.org/cookbook/extracting-data/selector-syntax
	}

	private void parseFleet(String html, Planet planet) {
		// UTF8 - conversion
		/*
		 * Leichter J‰ger -> Leichter J√§ger Schwerer J‰ger -> Schwerer J√§ger
		 * Kreuzer Schlachtschiff Schlachtkreuzer Bomber Zerstˆrer -> Zerst√∂rer
		 * Todesstern
		 * 
		 * Kleiner Transporter Groﬂer Transporter -> Gro√üer Transporter
		 * Kolonieschiff Recycler Spionagesonde Solarsatellit
		 */
		planet.addShip("Leichter Jaeger", parseShipCountHelper(html, "Leichter J√§ger"), ShipType.LIGHT_FIGHTER);
		planet.addShip("Schwerer Jaeger", parseShipCountHelper(html, "Schwerer J√§ger"), ShipType.HEAVY_FIGHTER);
		planet.addShip("Kreuzer", parseShipCountHelper(html, "Kreuzer"), ShipType.CRUISER);
		planet.addShip("Schlachtschiff", parseShipCountHelper(html, "Schlachtschiff"), ShipType.BATTLESHIP);
		planet.addShip("Schlachtkreuzer", parseShipCountHelper(html, "Schlachtkreuzer"), ShipType.BATTLECRUISER);
		planet.addShip("Bomber", parseShipCountHelper(html, "Bomber"), ShipType.BOMBER);
		planet.addShip("Zerstoerer", parseShipCountHelper(html, "Zerst√∂rer"), ShipType.DESTROYER);
		planet.addShip("Todesstern", parseShipCountHelper(html, "Todesstern"), ShipType.DEATHSTAR);

		planet.addShip("Kleiner Transporter", parseShipCountHelper(html, "Kleiner Transporter"),
				ShipType.SMALL_CARGO_SHIP);
		planet.addShip("Grosser Transporter", parseShipCountHelper(html, "Gro√üer Transporter"),
				ShipType.LARGE_CARGO_SHIP);
		planet.addShip("Kolonieschiff", parseShipCountHelper(html, "Kolonieschiff"), ShipType.COLONY_SHIP);
		planet.addShip("Recycler", parseShipCountHelper(html, "Recycler"), ShipType.RECYCLER);
		planet.addShip("Spionagesonde", parseShipCountHelper(html, "Spionagesonde"), ShipType.ESPIONAGE_PROBE);

		// int gtcount = parseShipCountHelper(html, "asd asd");
		// LOG.info("Groﬂe Transporter: " + gtcount);
	}

	private int parseShipCountHelper(String html, String shipname) {

		Document doc = Jsoup.parse(html);
		Elements shipChild = doc.select("span.textlabel:contains(" + shipname + ")");
		if (shipChild.isEmpty()) {
			return 0;
		}
		Elements parent = shipChild.parents();
		String count = parent.first().ownText();
		return Integer.parseInt(count);
	}
}
