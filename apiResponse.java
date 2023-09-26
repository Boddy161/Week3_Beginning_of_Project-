import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.io.FileInputStream;

public class apiResponse {
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			FileInputStream configFile = new FileInputStream("config.properties");
			properties.load(configFile);
			configFile.close();

			String apiKey = properties.getProperty("apikey");
			String email = properties.getProperty("email");

			// Check if apiKey and email are not empty (add additional validation as needed)
			if (apiKey == null || apiKey.isEmpty() || email == null || email.isEmpty()) {
				System.err.println("API key or email not provided in the configuration file.");
				return;
			}

			String apiUrl = "https://api.kickbox.com/v2/verify?email=" + email + "&apikey=" + apiKey;
			URL url = new URL(apiUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuilder response = new StringBuilder();

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();

				// Print the response
				System.out.println("API Response:");
				System.out.println(response);
			} else {
				// Handle the error response
				System.err.println("API request failed with response code: " + responseCode);
			}

			// Close the connection
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

