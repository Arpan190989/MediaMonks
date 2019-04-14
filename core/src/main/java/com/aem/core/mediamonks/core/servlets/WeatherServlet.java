package com.aem.core.mediamonks.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.mediamonks.core.bean.WeatherDataBean;
import com.aem.core.mediamonks.core.service.WeatherService;
import com.aem.core.mediamonks.core.utils.CommonConstants;



/**
 * The Class WeatherServlet.
 */
@Component(name = "Weather Servlet", immediate = true, service = Servlet.class, property = {
		"sling.servlet.resourceTypes=media-monks/weather",
		"sling.servlet.methods=GET", "sling.servlet.selectors=weather",
		"sling.servlet.extensions=json" })
public class WeatherServlet extends SlingAllMethodsServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WeatherServlet.class);

	@Reference
	private transient WeatherService weatherService;

	@Override
	protected void doGet(final SlingHttpServletRequest request,
			final SlingHttpServletResponse response) throws IOException {
		String serviceResponse = null;

		JSONObject weatherDetails = new JSONObject();

		String city = request.getParameter(CommonConstants.CITY);

		weatherDetails = weatherService.getWeatherDetails(city);

		JSONArray weatherArray = (JSONArray) weatherDetails
				.get(CommonConstants.LIST);

		printResponse(
				createJsonArrayFromList(processWeatherArray(weatherArray))
						.toString(), response);

		LOGGER.info(
				"Response from getMountingGroupDetailService Service is {}",
				serviceResponse);
		LOGGER.info("Ending Method ByoMountingServlet Do GET Method");
	}

	/**
	 * Prints the response.
	 * 
	 * @param serviceResponse
	 *            the service response
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void printResponse(String serviceResponse,
			SlingHttpServletResponse response) throws IOException {
		response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		out.println(serviceResponse);
	}

	private static List<WeatherDataBean> processWeatherArray(
			JSONArray weatherArray) {
		List<WeatherDataBean> weatherList = new ArrayList<WeatherDataBean>();
		for (int i = 0; i < weatherArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) weatherArray.get(i);
			WeatherDataBean weatherDataBean = new WeatherDataBean();
			weatherDataBean.setCity(null != jsonObject.get("name") ? jsonObject
					.get("name").toString() : StringUtils.EMPTY);
			weatherDataBean.setCountry(null != ((JSONObject) jsonObject
					.get("sys")).get("country") ? ((JSONObject) jsonObject
					.get("sys")).get("country").toString() : StringUtils.EMPTY);
			weatherDataBean.setRain(null != jsonObject.get("rain") ? jsonObject
					.get("rain").toString() : StringUtils.EMPTY);
			weatherDataBean.setLatitude(null != ((JSONObject) jsonObject
					.get("coord")).get("lat") ? ((JSONObject) jsonObject
					.get("coord")).get("lat").toString() : StringUtils.EMPTY);
			weatherDataBean.setLongitude(null != ((JSONObject) jsonObject
					.get("coord")).get("lon") ? ((JSONObject) jsonObject
					.get("coord")).get("lon").toString() : StringUtils.EMPTY);
			weatherDataBean.setSnow(null != jsonObject.get("snow") ? jsonObject
					.get("snow").toString() : StringUtils.EMPTY);
			weatherDataBean
					.setWeaterDescription(null != ((JSONObject) ((JSONArray) jsonObject
							.get("weather")).get(0)).get("description") ? ((JSONObject) ((JSONArray) jsonObject
							.get("weather")).get(0)).get("description").toString()
							: StringUtils.EMPTY);
			weatherDataBean.setMinTemp(null != ((JSONObject) jsonObject
					.get("main")).get("temp_min") ? ((JSONObject) jsonObject
					.get("main")).get("temp_min").toString()
					: StringUtils.EMPTY);
			weatherDataBean.setMaxTemp(null != ((JSONObject) jsonObject
					.get("main")).get("temp_max") ? ((JSONObject) jsonObject
					.get("main")).get("temp_max").toString()
					: StringUtils.EMPTY);
			weatherDataBean.setTemp(null != ((JSONObject) jsonObject
					.get("main")).get("temp") ? ((JSONObject) jsonObject
					.get("main")).get("temp").toString() : StringUtils.EMPTY);
			weatherDataBean.setHumidity(null != ((JSONObject) jsonObject
					.get("main")).get("humidity") ? ((JSONObject) jsonObject
					.get("main")).get("humidity").toString()
					: StringUtils.EMPTY);
			weatherDataBean.setPressure(null != ((JSONObject) jsonObject
					.get("main")).get("pressure") ? ((JSONObject) jsonObject
					.get("main")).get("pressure").toString()
					: StringUtils.EMPTY);
			weatherList.add(weatherDataBean);
		}

		return weatherList;
	}

	public JSONArray createJsonArrayFromList(List<WeatherDataBean> list) {
		JSONArray jsonArray = new JSONArray();
		for (WeatherDataBean dataBean : list) {
			JSONObject weatherBean = new JSONObject();
			weatherBean.put("CITY", dataBean.getCity());
			weatherBean.put("COUNTRY", dataBean.getCountry());
			weatherBean.put("TEMPERATURE", dataBean.getTemp());
			weatherBean.put("MIN_TEMP", dataBean.getMinTemp());
			weatherBean.put("MAX_TEMP", dataBean.getMaxTemp());
			weatherBean.put("WEATHER_DESCRIPTION",
					dataBean.getWeaterDescription());
			weatherBean.put("HUMIDITY", dataBean.getHumidity());
			weatherBean.put("LATITUDE", dataBean.getLatitude());
			weatherBean.put("LONGITUDE", dataBean.getLongitude());
			weatherBean.put("RAIN", dataBean.getRain());
			weatherBean.put("SNOW", dataBean.getSnow());
			jsonArray.add(weatherBean);
		}
		return jsonArray;

	}

}
