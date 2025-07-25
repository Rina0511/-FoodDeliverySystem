package Interface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackendConnector {

    public static String sendGet(String urlString) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Optional timeout
            conn.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public static String sendPost(String urlString, String jsonData) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8")
            );

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }

            reader.close();
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
