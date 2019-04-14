package com.aem.core.mediamonks.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.mediamonks.core.service.ConnectionService;
import com.aem.core.mediamonks.core.service.WeatherService;


@Component(immediate = true, service = WeatherService.class)
@Designate(ocd = WeatherServiceImpl.Configuration.class)
public class WeatherServiceImpl implements WeatherService {

	private String weatherEndPoint;

	private String weatherEndPointAPIKey;
	
	private String companyHeadQuarter;

	/** The logger. */
	private static final Logger log = LoggerFactory
			.getLogger(WeatherServiceImpl.class);
	
	@Reference
	ConnectionService connectionService;

	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.weatherEndPoint = config.weatherEndPoint();
		this.weatherEndPointAPIKey = config.weatherEndPointAPIKey();
		this.companyHeadQuarter = config.companyHeadQuarter();
	}

	public String getWeatherEndPoint() {
		return weatherEndPoint;
	}

	public String getWeatherEndPointAPIKey() {
		return weatherEndPointAPIKey;
	}
	
	public String getCompanyHeadQuarter() {
		return companyHeadQuarter;
	}

	@ObjectClassDefinition(name = "Weather Service API Configuration")
	public @interface Configuration {

		@AttributeDefinition(name = "weatherEndPoint", description = "Weather End Point", type = AttributeType.STRING)
		String weatherEndPoint() default "https://openweathermap.org/data/2.5/find?q={}&type=like&sort=population&cnt=30&appid={id}&_=1554949741307";

		@AttributeDefinition(name = "weatherEndPointAPIKey", description = "API Key for Weather End Point", type = AttributeType.STRING)
		String weatherEndPointAPIKey() default "b6907d289e10d714a6e88b30761fae22";
		
		@AttributeDefinition(name = "companyHeadQuarter", description = "HeadQuarter of Media Monks", type = AttributeType.STRING)
		String companyHeadQuarter() default "Amsterdam";

	}

	public JSONObject getWeatherDetails(String city) {
		log.info("Entering getWeatherDetails Method");
		String weatherEndPoint = getWeatherEndPoint().replace("{}",StringUtils.isNotBlank(city)?city:getCompanyHeadQuarter());
		log.info("Exiting getWeatherDetails Method");
		return connectionService.sendGet(weatherEndPoint.replace("{id}",getWeatherEndPointAPIKey()));

	}

}
