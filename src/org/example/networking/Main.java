package org.example.networking;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static final String URL_LOCATION_FILE = "https://raw.githubusercontent.com/productstar-team/javaTwo/master/resources/monthly_IBM.csv";
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        NetUtils netUtils = new NetUtils();
//        System.out.println(netUtils.getTextFromUrlHttps(URL_LOCATION_FILE));
//        System.out.println(netUtils.getStocks());

        List<Stock> stocks = netUtils.getTextFromUrlHttps(URL_LOCATION_FILE);
//        List<Stock> stocks = netUtils.getStocks();

        stocks.sort(new Comparator<Stock>() {
            @Override
            public int compare(Stock o1, Stock o2) {
                Float val1 = o1.getClose();
                Float val2 = o2.getClose();
                return val1.compareTo(val2);
            }
        });

        System.out.println(" Min: date: "  + stocks.get(0).getDate() + " Price close: " + stocks.get(0).getClose());
        System.out.println(" Max: date: "  + stocks.get(stocks.size() - 1).getDate() + " Price close: " + stocks.get(stocks.size() - 1).getClose());
    }
}