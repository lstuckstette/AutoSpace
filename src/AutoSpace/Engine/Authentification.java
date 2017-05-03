package AutoSpace.Engine;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import AutoSpace.Model.Account;

public class Authentification {

	private static final Logger LOG = Logger.getLogger(Authentification.class.getName());
	private Account account;
	private BasicCookieStore cookieStore;

	public Authentification(Account account) {
		// System.out.println("HW!");

		this.account = account;
		this.cookieStore = account.getAuthentification();

	}

	public void login() {
		cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		try {
			HttpGet httpget = new HttpGet("https://de.ogame.gameforge.com/");
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();
				EntityUtils.consume(entity);

			} finally {
				response1.close();
			}

			HttpUriRequest login = RequestBuilder.post().setUri(new URI("https://de.ogame.gameforge.com/main/login"))
					.addParameter("kid", "").addParameter("login", account.getUsername())
					.addParameter("pass", account.getPassword()).addParameter("uni", account.getUniverse()).build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			try {
				HttpEntity entity = response2.getEntity();

				LOG.fine("Login form get: " + response2.getStatusLine());
				EntityUtils.consume(entity);

				LOG.fine("Post logon cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					LOG.info("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						LOG.fine("- " + cookies.get(i).toString());
					}
				}
			} finally {
				response2.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
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
		account.setAuthentification(cookieStore);
	}

	public boolean wasSuccessful() {
		if (cookieStore.getCookies().size() < 4)
			return false;
		return true;
	}

}
