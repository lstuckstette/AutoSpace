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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import AutoSpace.Model.Account;
import AutoSpace.Model.Planet;

public class GalaxyView {

	private static final Logger LOG = Logger.getLogger(GalaxyView.class.getName());
	private Account account;

	public GalaxyView(Account account) {
		this.account = account;
	}

	public ArrayList<Planet> getInactivePlanets(Planet start, int range) {
		ArrayList<Planet> result = new ArrayList<Planet>();
		int galaxy = start.getCoordinate().getGalaxy();
		int system = start.getCoordinate().getSystem();

		// lower bounds check
		if (system - range < 0) {
			for (int i = 499 + (system - range); i <= 499; i++) {
				for (Planet p : getInactivePlanetsFromSystem(galaxy - 1, i)) {
					result.add(p);
				}
			}
		} // upper bounds check
		else if (system + range > 499) {
			for(int i=0;i<(system+range)-499;i++){
				for (Planet p : getInactivePlanetsFromSystem(galaxy + 1, i)) {
					result.add(p);
				}
			}
		}
		//normal require
		
		for(int i = system - range;i<system + range;i++){
			if(i>=0 && i<499){
				for (Planet p : getInactivePlanetsFromSystem(galaxy , i)) {
					result.add(p);
				}
			}
		}

		return result;
	}

	private ArrayList<Planet> getInactivePlanetsFromSystem(int galaxy, int system) {
		ArrayList<Planet> result = new ArrayList<Planet>();
		String currentSystem = getPageHTML(
				"/game/index.php?page=galaxyContent&ajax=1&galaxy=" + galaxy + "&system=" + system);

		// Cleanup of String:
		currentSystem = currentSystem.replace("\\n", "");
		currentSystem = currentSystem.replace("\\", "");

		Document doc = Jsoup.parse(currentSystem);

		Elements planetRows = doc.select("tr.row.inactive_filter");
		for (Element e : planetRows) {
			Elements planetname = e.select("td.planetname");
			Elements coordinate = e.select("span#pos-planet");
			Planet p = new Planet();
			p.setPlanetName(planetname.text());
			p.setCoordinate(coordinate.text());
			result.add(p);
		}

		return result;
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
