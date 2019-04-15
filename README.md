# Media Monks Assignment

This is a project template designed to demonstrate Media Monks assignment.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, Bean, Service,Servlets as well as component-related Java code.
* apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode specific configs if required
* content: contains sample content using the components from the apps
* tests: Java bundle containing JUnit tests that are executed server-side. 

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
	
## Require Step

Go to helper folder present under cloned repository
Install mediamonks_ServiceUser.zip
Once installed go to the useradmin i.e localhost:4801/useradmin and login with admin credentials.
Search for mediamonks user.
Give admin rights to mediamonk user.	

## Description

#Template-Types
1.Media Monk Language Page - Use this template to create language pages . For example en_us, de_de. In the page properties of language pages, path of the global-header data page should be authored in order to see header on all 	pages. This template have a baked in parsys which can be used by author to add more components.
2. Media Monk Empty Page - Use this template to create empty pages. These pages can be used as helper folder for example /content/media-monks and /content/media-monks/www. Note:- Redirect functionality can be authored on these pages.
3. Media Monk Content Page - This is similar to Language page, the only difference being that the page properties of this page doesn't have an option to select path of global-header component.

#Content Description

1. en_us.html - This page is designed from Language Page template. The page properties of this page have a customized tab Global authoring where the path of header page is authored.
2. global-header.html - A Global header component is dragged on this page and authored. All the pages will refer this data page to get hold of header component.
3. Weather.html - This is the main page , where Header component, Image component and Weather-Widget component can be accessed. At the end of parsys a Weather-Widget component is dropped at the end, demonstrating temperature of Amsterdam.

#Servlet Description
1. Weather Servlet - A GET servlet weather servlet is created which will pass on the city name as the request param and will fetch the weather details. 
			For example: - http://localhost:4801/content/media-monks/jcr:content/servlets/weathergetter.weather.json?city=Amsterdam and http://localhost:4801/content/media-monks/jcr:content/servlets/weathergetter.weather.json?city=  if not city is selected Amsterdam would be considered as default city.
			

Service Description
1. Canal Height Service - A canal height service is created which will fetch the list of last 20 entries of Canal Height. The entries of canal and respective reading date are authored in a canal data page present at /en_us/canal-height. On this page a component Canal is dragged and dropped. And entries of height and date are done.
			Usage - Passing a integer as a parameter to the calling serlvet will return data having height above the sent height.
			For example - 
			
					 


 
