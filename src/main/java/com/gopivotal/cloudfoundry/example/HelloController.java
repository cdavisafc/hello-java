package com.gopivotal.cloudfoundry.example;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.UriBasedServiceInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gopivotal.cloudfoundry.example.springcloud.BasicHttpWebServiceInfo;

@Controller
public class HelloController {

	/**
	 * This simple controller just looks up the URI of the bound service and returns a JSP page that injects that URI.
	 * If there is more than one service bound, or the service bound does not have a URI then an error is thrown.
	 */
	@RequestMapping(value = "/testwebservicebinding", method = RequestMethod.GET)
	public String webServiceBindingTest(Locale locale, Model model) {
		
		// using spring cloud to get the single service bound to this app and get the uri
		// There must be a single service bound and it must contain the fields of a URIBasedServiceInfo
		// object, else an error will be displayed.
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		if (cloud.getServiceInfos().size() == 1 && cloud.getServiceInfos().get(0) instanceof BasicHttpWebServiceInfo) {
			BasicHttpWebServiceInfo serviceInfo = (BasicHttpWebServiceInfo) cloud.getServiceInfos().get(0);
			String uri = serviceInfo.getHost();
			// TODO: Currently this assumes no port, accurate if the http web service is deployed to CF, but may not be if the http web service is external
			// TODO: When UriBasedServiceInfo class exposes scheme, update to reflect whether it is http or https
			uri = "http://" + serviceInfo.getHost() + "/" + serviceInfo.getPath();
			Integer port = (Integer) cloud.getApplicationInstanceInfo().getProperties().get("port");
			
			model.addAttribute("serviceURI", uri);
			model.addAttribute("port",port);
			return "hello";
		} else {
			int numSvc = cloud.getServiceInfos().size();
			String serviceInfoName = ((numSvc == 1) ? cloud.getServiceInfos().get(0).getClass().getName() : "unknown");
			model.addAttribute("numServices", numSvc);
			model.addAttribute("type", serviceInfoName);
			return "error";
		}
	}
	
	@RequestMapping(value = "/testsessions", method = RequestMethod.GET)
	public String sessionTest(HttpSession session, Model model) {
		// playing with session state management
		
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		Integer port = (Integer) cloud.getApplicationInstanceInfo().getProperties().get("port");
		
		String ports = (String) session.getAttribute("ports");
		System.out.println("This port: " + port + " session ports: " + ports);
		if (ports == null) ports = "";
		ports = ports + " - " + port.toString();
		session.setAttribute("ports", ports);
		
		model.addAttribute("ports", ports);
		model.addAttribute("port",port);
		
		// look up if 
		return "hello";
		
	}
	
	@RequestMapping(value = "/broken", method = RequestMethod.GET)
	public void killInstance() {
		System.exit(1);
	}
	

}
