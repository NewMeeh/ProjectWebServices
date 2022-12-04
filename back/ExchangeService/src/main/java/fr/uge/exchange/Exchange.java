package fr.uge.exchange;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class Exchange {
	
	private final Logger logger = Logger.getLogger(Exchange.class.getName());
	private final Charset UTF8 = Charset.forName("UTF8");
	
	
	public float exchange(String originCurrency, String targetCurrency, float amount) {
		System.err.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");
		StringBuilder sb = new StringBuilder().append("https://api.exchangerate.host/convert?from=")
				.append(originCurrency)
				.append("&to=")
				.append(targetCurrency)
				.append("&amount=")
				.append(amount);
		
		JsonElement root;
		float result;
		try {
	        URL url = new URL(sb.toString());
	        HttpURLConnection request = (HttpURLConnection) url.openConnection();
	        request.connect();
			System.err.println("BHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");
			
			InputStream content = (InputStream) request.getContent();
			InputStreamReader in = new InputStreamReader(content);
			
			
			
			ByteBuffer bb = ByteBuffer.allocateDirect(4096);
			bb.put(content.readAllBytes());
			
			
			String js = UTF8.decode(bb.flip()).toString();
			
			System.err.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB    HHHHHHHHHHHHHH  " + js) ;
			
			
			

	        root = JsonParser.parseString(js);
        
	        
			System.err.println("CHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");
	
	        JsonElement jsonElement = root.getAsJsonObject().get("result");
			System.err.println("DHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");
	
	        if (jsonElement.isJsonNull()) return -1f;
			System.err.println("EHHHHHHHHHHHHHHHHHHHHHHHHHHHHH    HHHHHHHHHHHHHH  ");
	
	        result = jsonElement.getAsFloat();
	        
			logger.info("Exchange "+amount+" "+originCurrency + " to " + targetCurrency +": "+result);
		
		
		} catch (Exception e) {
			e.printStackTrace();
			return -1f;
		}
		return result;
	}

}
