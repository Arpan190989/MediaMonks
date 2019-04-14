package com.aem.core.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.osgi.service.component.annotations.Component;


import com.aem.core.service.ConnectionService;



@Component(service = ConnectionService.class, immediate = true)
public class ConnectionServiceImpl implements ConnectionService {
	
	public JSONObject sendGet(String endPoint) {

		StringBuilder outputString = new StringBuilder();
		try {
			URL url = new URL(endPoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				outputString.append(output);
			}
			
			conn.disconnect();
			return getJsonObject(outputString.toString());
		}

		 catch (MalformedURLException e) {
			
		} catch (ProtocolException e) {
			
		} catch (IOException e) {
			
		} catch (ParseException e) {
			
		}
		return new JSONObject();

	}
	
	private JSONObject getJsonObject(String outputString) throws ParseException
	{
		JSONParser parse = new JSONParser(); 
		JSONObject jobj = (JSONObject)parse.parse(outputString.toString());
		return jobj;
		
	}
	
	

}
