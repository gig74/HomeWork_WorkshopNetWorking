package org.example.networking;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class NetUtils {
    public List<Stock> getTextFromUrlHttps(String urlLocationFile) throws IOException {
        List<Stock> resultFromUrl = new ArrayList<>();
        // Create a new trust manager that trust all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Activate the new trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }

//        Прокси
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.chmk.mechelgroup.ru", 8080));
        Authenticator authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication("user",
                        "password".toCharArray()));
            }
        };
        Authenticator.setDefault(authenticator);
//        conn = new URL(urlString).openConnection(proxy);

// And as before now you can use URL and URLConnection
        URL url = new URL(urlLocationFile);
        URLConnection connection = url.openConnection(proxy);
////  then download the file
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line = br.readLine();
            line = br.readLine(); // Для игнорирования первой строчки
            while (line != null) {
                resultFromUrl.add(convertLineToStock(line));
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("Unable to get data from the API");
        }
        return resultFromUrl;
    }

    public List<Stock> getStocks() throws IOException {
        String url = "https://raw.githubusercontent.com/productstar-team/javaTwo/master/resources/monthly_IBM.csv";
        try (BufferedInputStream is = new BufferedInputStream(new URL(url).openStream())) {
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String result = scanner.hasNext() ? scanner.next() : "";
            return convertCsvToStock(result);
        } catch (Exception e) {
            System.out.println("Unable to get data from the API");
        }
        return Collections.emptyList();
    }

    private List<Stock> convertCsvToStock(String inputData) {
        List<Stock> stocks = new ArrayList<>();
        String[] lines = inputData.split("\n");
        for (int i = 1; i < lines.length; i++) {
            stocks.add(convertLineToStock(lines[i]));
        }
        return stocks;
    }

    private Stock convertLineToStock(String line) {
        String[] tokens = line.split(",");
        return new Stock(tokens[0] , Float.parseFloat(tokens[4]));
    }
}
