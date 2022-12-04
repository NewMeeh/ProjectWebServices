package fr.uge.exchange;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class Test {

    private static final Logger logger = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) throws IOException {
        String originCurrency = "EUR";
        String targetCurrency = "USD";
        float amount = (float) 300.30;
        System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");
        StringBuilder sb = new StringBuilder().append("https://api.exchangerate.host/convert?from=")
                .append(originCurrency)
                .append("&to=")
                .append(targetCurrency)
                .append("&amount=")
                .append(amount);

        URL url = new URL(sb.toString());
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        System.out.println("BHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");
        JsonElement root = JsonParser.parseReader((new InputStreamReader((InputStream) request.getContent())));



        System.out.println("CHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");

        JsonElement jsonElement = root.getAsJsonObject().get("result");
        System.out.println("DHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");

        if (jsonElement.isJsonNull()) return ;
        System.out.println("EHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");

        float result = jsonElement.getAsFloat();

        logger.info("Exchange "+amount+" "+originCurrency + " to " + targetCurrency +": "+result);



        return ;
    }

}
