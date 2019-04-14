package com.aem.core.mediamonks.core.service.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.mediamonks.core.service.ConnectionService;



@Component(service = ConnectionService.class, immediate = true)
public class ConnectionServiceImpl implements ConnectionService {

	/** The logger. */
	private static final Logger log = LoggerFactory
			.getLogger(ConnectionServiceImpl.class);

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
			while ((output = br.readLine()) != null) {
				outputString.append(output);
			}

			conn.disconnect();
			return getJsonObject(outputString.toString());
		}

		catch (MalformedURLException e) {
			log.error("MalformedURLException" + e.getMessage());

		} catch (ProtocolException e) {
			log.error("ProtocolException" + e.getMessage());

		} catch (IOException e) {
			log.error("IOException" + e.getMessage());

		} catch (ParseException e) {
			log.error("ParseException" + e.getMessage());
		}
		return new JSONObject();

	}

	private JSONObject getJsonObject(String outputString) throws ParseException {
		log.info("Entering getJsonObject");
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(outputString.toString());
		log.info("Exiting getJsonObject");
		return jobj;

	}

}
