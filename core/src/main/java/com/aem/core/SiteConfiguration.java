package com.aem.core;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

/**
 * The Interface SiteConfiguration.
 */
@Configuration(label = "Site Configurations", description = "Used for configuring Site specific configurations")
public @interface SiteConfiguration {

   
    @Property(label = "Canal Data Page", description = "Canal Data Page Path")
    String canalDataPage();
 
}
