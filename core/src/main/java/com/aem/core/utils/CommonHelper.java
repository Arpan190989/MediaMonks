package com.aem.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;


/**
 * The type Common helper.
 */
public final class CommonHelper {

	
    /**
     * Instantiates a new common helper.
     */
    private CommonHelper() {
    }

    /**
     * Gets service resolver.
     *
     * @param resolverFactory the resolver factory
     * @return the service resolver
     * @throws LoginException the login exception
     */
    public static ResourceResolver getServiceResolver(ResourceResolverFactory resolverFactory) throws LoginException {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, CommonConstants.WRITE_SERVICE);
        return resolverFactory.getServiceResourceResolver(param);
    }

        
}
