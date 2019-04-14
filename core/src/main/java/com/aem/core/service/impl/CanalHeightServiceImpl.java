package com.aem.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.SiteConfiguration;
import com.aem.core.bean.CanalDataBean;
import com.aem.core.service.CanalHeightService;
import com.aem.core.utils.CommonConstants;
import com.aem.core.utils.CommonHelper;

@Component(service = CanalHeightService.class, immediate = true, enabled = true)
public class CanalHeightServiceImpl implements CanalHeightService {
	private static final String HEIGHT = "height";

	/** The logger. */
	private static final Logger log = LoggerFactory
			.getLogger(CanalHeightServiceImpl.class);

	/** The resolver factory. */
	@Reference
	private ResourceResolverFactory resolverFactory;

	private List<CanalDataBean> canalDataList;

	private String canalDataPage;

	private int maxEntries;

	public String getCanalDataPage() {
		return canalDataPage;
	}

	public int getMaxEntries() {
		return maxEntries;
	}

	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.canalDataPage = config.canalDataPage();
		this.maxEntries = config.maxEntries();
	}

	@ObjectClassDefinition(name = "Canal Height Configuration")
	public @interface Configuration {

		@AttributeDefinition(name = "canalDataPage", description = "Canal Data Page Path", type = AttributeType.STRING)
		String canalDataPage() default "/content/media-monks/www/language-masters/en_us/canal-height";

		@AttributeDefinition(name = "maxEntries", description = "Number of Entries to publish(Only when no height is passed)", type = AttributeType.INTEGER)
		int maxEntries() default 20;

	}

	public String getCanalJson(SlingHttpServletRequest request) {

		String height = null != request.getParameter(HEIGHT) ? request
				.getParameter(HEIGHT) : StringUtils.EMPTY;

		ResourceResolver resolver = null;

		try {

			resolver = CommonHelper.getServiceResolver(resolverFactory);
			Resource servletRes = resolver.getResource(request.getResource()
					.getPath());
			ConfigurationBuilder configBuilder = servletRes
					.adaptTo(ConfigurationBuilder.class);
			SiteConfiguration siteConfiguration = (null != configBuilder) ? configBuilder
					.as(SiteConfiguration.class) : null;
			log.error("the value of siteconfig canalDataPage is"
					+ siteConfiguration.canalDataPage());
			Resource canalDataPageRes = resolver.getResource(getCanalDataPage()
					+ CommonConstants.CANAL_HEIGHT);

			Node canalDataNode = canalDataPageRes.adaptTo(Node.class);
			NodeIterator nodeIterator = null;
			canalDataList = new ArrayList<CanalDataBean>();

			nodeIterator = canalDataNode.getNodes();

			while (nodeIterator.hasNext()) {
				Node child = (Node) nodeIterator.nextNode();
				NodeIterator ni2 = child.getNodes();
				setMultiFieldItems(ni2);
			}
			Collections.sort(canalDataList);
			if (StringUtils.isNotEmpty(height)) {
				Integer.parseInt(height);
				canalDataList = processCanalData(Integer.parseInt(height));
			} else {
				canalDataList = canalDataList.subList(0,
						min(canalDataList.size(), getMaxEntries() - 1));
			}
			return createJsonArrayFromList(canalDataList).toJSONString();
		}

		catch (LoginException e) {
			log.error("Login Exception in getCanalJson method");
			return StringUtils.EMPTY;
		} catch (RepositoryException e) {
			log.error("RepositoryException in getCanalJson method");
			return StringUtils.EMPTY;
		} catch (NumberFormatException e) {
			log.error("NumberFormatException in getCanalJson method, the given Height is not a Integer");
			return "Height can only be a number";
		}

	}

	public static ResourceResolver getServiceResolver(
			ResourceResolverFactory resolverFactory) throws LoginException {
		Map<String, Object> param = new HashMap<>();
		param.put(ResourceResolverFactory.SUBSERVICE,
				CommonConstants.WRITE_SERVICE);
		return resolverFactory.getServiceResourceResolver(param);
	}

	private List<CanalDataBean> setMultiFieldItems(NodeIterator ni2) {

		try {
			String waterHeight;
			String eventDate;
			while (ni2.hasNext()) {
				CanalDataBean canalDataBean = new CanalDataBean();
				Node grandChild = (Node) ni2.nextNode();
				waterHeight = grandChild.getProperty(
						CommonConstants.WATER_HEIGHT).getString();
				eventDate = grandChild
						.getProperty(CommonConstants.READING_DATE).getString();
				canalDataBean.setWaterHeight(Integer.parseInt(waterHeight));
				SimpleDateFormat sdf = new SimpleDateFormat(
						CommonConstants.DATE_FORMAT);
				Date date = sdf.parse(eventDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				canalDataBean.setReadingDate(cal);
				canalDataList.add(canalDataBean);
			}
		} catch (Exception e) {

		}
		return canalDataList;
	}

	public List<CanalDataBean> processCanalData(Integer height) {
		List<CanalDataBean> greaterHeightCanalBean = new ArrayList<>();
		for (int i = 0; i < canalDataList.size(); i++) {
			if (canalDataList.get(i).getWaterHeight() > height) {
				greaterHeightCanalBean.add(canalDataList.get(i));
			}

		}
		return greaterHeightCanalBean;
	}

	public JSONArray createJsonArrayFromList(List<CanalDataBean> list) {
		JSONArray jsonArray = new JSONArray();
		for (CanalDataBean dataBean : list) {
			JSONObject canalBean = new JSONObject();
			canalBean.put(CommonConstants.WATER_HEIGHT,
					dataBean.getWaterHeight());
			canalBean.put(CommonConstants.READING_DATE,
					dataBean.getReadingDate().getTime());
			jsonArray.add(canalBean);
		}
		return jsonArray;
	}

}