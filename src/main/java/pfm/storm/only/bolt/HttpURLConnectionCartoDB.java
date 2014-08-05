package pfm.storm.only.bolt;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionCartoDB {

	private final String USER_AGENT = "Mozilla/5.0";

	private final String user = "rdorden"; /* User Login to CartoDB */
	private final String api_key = "d96046a567aa45a4d2f9d26b5ed58399a5f41885"; /*API KEY of CartoDB */

	// HTTP GET request
	public void CleanDb() throws Exception {

		String request = "https://"
				+ user
				+ ".cartodb.com/api/v2/sql?q=DELETE%20FROM%20twitter_pfm&api_key="
				+ api_key;

		sendPost(request);

	}

	// HTTP POST request
	public void sendToDb(String cartodb_Id, String longitude, String latitude,
			String hashtag) throws Exception {

		String request = "https://"
				+ getUser()
				+ ".cartodb.com/api/v2/sql?q=INSERT%20INTO%20twitter_pfm%20(cartodb_id,%20the_geom,%20hashtag)%20VALUES("
				+ cartodb_Id + ",ST_SetSRID(ST_Point(" + longitude + ","
				+ latitude + "),4326),'" + hashtag + "')&api_key="
				+ getApi_key();

		sendPost(request);
	}

	private void sendPost(String request) throws IOException {

		URL obj = new URL(request);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

	}

	public String getUser() {
		return user;
	}

	public String getApi_key() {
		return api_key;
	}

}