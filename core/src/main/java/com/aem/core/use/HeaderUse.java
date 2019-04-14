package com.aem.core.use;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.aem.core.bean.HeaderNavigationBean;

public class HeaderUse extends WCMUsePojo {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	private List<HeaderNavigationBean> submenuItems ;

	public void activate() throws Exception {
		submenuItems = new ArrayList<HeaderNavigationBean>();
		Node currentNode = getResource().adaptTo(Node.class);
		NodeIterator nodeIterator = currentNode.getNodes();
		while (nodeIterator.hasNext()) {
			Node child = (Node) nodeIterator.nextNode();
			NodeIterator ni2 = child.getNodes();
			setMultiFieldItems(ni2);
		}
	}

	private List<HeaderNavigationBean> setMultiFieldItems(NodeIterator ni2) {

		try {
			String navigationLink;
			String navigationText;
			while (ni2.hasNext()) {

				HeaderNavigationBean navigationItem = new HeaderNavigationBean();
				Node grandChild = (Node) ni2.nextNode();
				navigationLink = grandChild.getProperty("navigationLink")
						.getString();
				navigationText = grandChild.getProperty("navigationText")
						.getString();
				navigationItem.setNavigationLink(navigationLink);
				navigationItem.setNavigationText(navigationText);
				submenuItems.add(navigationItem);
			}
		} catch (Exception e) {

		}
		return submenuItems;
	}

	public List<HeaderNavigationBean> getMultiFieldItems() {
		return submenuItems;
	}

}
