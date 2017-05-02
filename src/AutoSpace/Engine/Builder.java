package AutoSpace.Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
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
import AutoSpace.Types.DefenseType;
import AutoSpace.Types.FacilityBuildingType;
import AutoSpace.Types.ResearchType;
import AutoSpace.Types.ResourceBuildingType;
import AutoSpace.Types.ShipType;

public class Builder {

	private static final Logger LOG = Logger.getLogger(Builder.class.getName());
	private Account account;

	public Builder(Account account) {
		this.account = account;
	}

	public void buildResourceBuilding(Planet target, ResourceBuildingType building) {

		// switch to desired Planet
		String resources = "/game/index.php?page=resources&cp=" + target.getPlanetId();
		String resourcesHTML = getPageHTML(resources);

		// extract token
		String token = extractToken(resourcesHTML);

		// send build request:
		try {
			HttpUriRequest request = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=resources&deprecated=1"))
					.addParameter("token", token).addParameter("modus", "1").addParameter("type", building.id())
					.build();
			postData(request);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void buildFacilityBuilding(Planet target, FacilityBuildingType building) {

		// switch to desired Planet
		String resources = "/game/index.php?page=station&cp=" + target.getPlanetId();
		String resourcesHTML = getPageHTML(resources);

		// extract token
		String token = extractToken(resourcesHTML);

		// send build request:
		try {
			HttpUriRequest request = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=station&deprecated=1"))
					.addParameter("token", token).addParameter("modus", "1").addParameter("type", building.id())
					.build();
			postData(request);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startResearch(Planet target, ResearchType research) {
		// switch to desired Planet
		String resources = "/game/index.php?page=research&cp=" + target.getPlanetId();
		String resourcesHTML = getPageHTML(resources);

		// extract token
		String token = extractToken(resourcesHTML);

		// send research request:
		try {
			HttpUriRequest request = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=research&deprecated=1"))
					.addParameter("token", token).addParameter("modus", "1").addParameter("type", research.id())
					.build();
			postData(request);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buildDefense(Planet target, DefenseType defense, int count) {
		// switch to desired Planet
		String resources = "/game/index.php?page=defense&cp=" + target.getPlanetId();
		String resourcesHTML = getPageHTML(resources);

		// extract token
		String token = extractToken(resourcesHTML);

		// send build request:
		try {
			HttpUriRequest request = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=defense&deprecated=1"))
					.addParameter("token", token).addParameter("modus", "1").addParameter("type", defense.id())
					.addParameter("menge", String.valueOf(count)).build();
			postData(request);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buildShip(Planet target, ShipType ship, int count) {
		// switch to desired Planet
		String resources = "/game/index.php?page=shipyard&cp=" + target.getPlanetId();
		String resourcesHTML = getPageHTML(resources);

		// extract token
		String token = extractToken(resourcesHTML);

		// send build request:
		try {
			HttpUriRequest request = RequestBuilder.post()
					.setUri(new URI("https://" + account.getUniverse() + "/game/index.php?page=shipyard&deprecated=1"))
					.addParameter("token", token).addParameter("modus", "1").addParameter("type", ship.id())
					.addParameter("menge", String.valueOf(count)).build();
			postData(request);
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
