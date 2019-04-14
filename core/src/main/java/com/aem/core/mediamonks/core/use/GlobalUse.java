package com.aem.core.mediamonks.core.use;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

import com.adobe.cq.sightly.WCMUsePojo;
import com.aem.core.mediamonks.core.utils.CommonConstants;


public class GlobalUse extends WCMUsePojo {
	private String headerResPath;

	@Override
	public void activate() throws Exception {
		String globalPath = getInheritedPageProperties().get("headerDataPage",
				String.class);
		headerResPath = getResourcePathFromPage(globalPath,
				CommonConstants.HEADER_RES_TYPE);
	}

	/**
	 * This method will check for the provided resourceType and check against
	 * the components that are present under the Data page
	 * 
	 * @param inpPage
	 * @param inpResType
	 * @return
	 */
	private String getResourcePathFromPage(String inpPage, String inpResType) {
		if (StringUtils.isNotBlank(inpPage)) {
			String jcrPath = inpPage + CommonConstants.JCR_ROOT;
			Resource res = getResourceResolver().getResource(jcrPath);
			if (null != res) {
				Iterator<Resource> resIt = res.listChildren();
				while (resIt.hasNext()) {
					Resource child = resIt.next();
					String resType = child.getValueMap().get(
							CommonConstants.SLING_RESOURCE_TYPE, String.class);
					if (StringUtils.isNotBlank(resType)
							&& StringUtils
									.equalsIgnoreCase(resType, inpResType)) {
						return child.getPath();
					}
				}
			}
		}
		return null;
	}

	/**
	 * @return the headerResPath
	 */
	public String getHeaderResPath() {
		return headerResPath;
	}

}