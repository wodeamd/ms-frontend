package ms.demo.front;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import static spark.Spark.get;
import static spark.Spark.port;



public class FrontendController {

    public static void main(String[] args) {
        port(8070);
        get("/", (req, res) -> "This is frontend worker:" + InetAddress.getLocalHost().getHostAddress().toString());
        get("/frontend/hex", (req, res) -> {
            String str =  req.queryParams("str");
            return executePost("http://"
                    + System.getenv("WORKER")+
//                    + "localhost" +
                    ":8070" + "/backend/hex", "str=" + str);
        });
    }

    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL + "?" + urlParameters);
            connection = (HttpURLConnection) url.openConnection();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
