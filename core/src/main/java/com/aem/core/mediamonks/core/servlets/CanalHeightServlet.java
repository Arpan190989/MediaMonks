package com.aem.core.mediamonks.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.simple.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.mediamonks.core.service.CanalHeightService;
import com.aem.core.mediamonks.core.utils.CommonConstants;



/**
 * The Class WeatherServlet.
 */
@Component(name = "Canal Servlet", immediate = true, service = Servlet.class, property = {
		"sling.servlet.resourceTypes=media-monks/canalheight",
		"sling.servlet.methods=GET", "sling.servlet.selectors=height",
		"sling.servlet.extensions=json" })
public class CanalHeightServlet extends SlingAllMethodsServlet {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CanalHeightServlet.class);

	@Reference
	private transient CanalHeightService canalHeightService;

	@Override
	protected void doGet(final SlingHttpServletRequest request,
			final SlingHttpServletResponse response) throws IOException {
		String data = canalHeightService.getCanalJson(request);

		response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		out.println(data);

		LOGGER.info(
				"Response from getMountingGroupDetailService Service is {}",
				data);
		LOGGER.info("Ending Method ByoMountingServlet Do GET Method");
	}

}
