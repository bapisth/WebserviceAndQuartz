package com.hemendra.webservice.quartz;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.hemendra.webservice.quartz.restconfig.RestTemplateFactory;
import com.bipros.emsintegration.wsdl.ObjectFactory;
import com.bipros.emsintegration.wsdl.RetrieveIncidentKeysListRequest;
import com.bipros.emsintegration.wsdl.RetrieveIncidentListRequest;
import com.bipros.emsintegration.wsdl.UpdateIncidentRequest;

@Configuration
@ComponentScan(basePackages = "com.bipros.emsintegration")
public class EMSConfiguration {
	// This should not be changed as it is the wsdl schema, changing the value will
	// cause error in accessing the soap.
	private static final String DEFAULT_EMS_URI = "http://10.10.1.132:13080/SM/7/ws";

	@Value("${ems.username}")
	private String emsUserName;

	@Value("${ems.password}")
	private String emsPassword;

	@Bean
	public ObjectFactory objectFactory() {
		return new ObjectFactory();
	}

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.bipros.emsintegration.wsdl");
		return marshaller;
	}

	// Saop web service access
	@Bean
	public WebServiceTemplate serviceTemplate() {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(jaxb2Marshaller());
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
		webServiceTemplate.setDefaultUri(DEFAULT_EMS_URI);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender());
		return webServiceTemplate;
	}

	@Bean
	public HttpComponentsMessageSender httpComponentsMessageSender() {
		HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
		// set the basic authorization credentials
		httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());
		return httpComponentsMessageSender;
	}

	@Bean
	public UsernamePasswordCredentials usernamePasswordCredentials() {
		// pass the user name and password to be used
		return new UsernamePasswordCredentials(emsUserName, emsPassword);
	}

	@Bean
	public RetrieveIncidentKeysListRequest incidentKeysListRequest() {
		return objectFactory().createRetrieveIncidentKeysListRequest();
	}

	@Bean
	public RetrieveIncidentListRequest retrieveIncidentListRequest() {
		return objectFactory().createRetrieveIncidentListRequest();
	}

	@Bean
	public UpdateIncidentRequest updateIncidentRequest() {
		return objectFactory().createUpdateIncidentRequest();
	}
	
	@Bean
	public RestTemplateFactory restTemplateFactory() {
		return new RestTemplateFactory();
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplateFactory restTemplateFactory = restTemplateFactory();
		try {
			restTemplateFactory.afterPropertiesSet();
			RestTemplate restTemplate = restTemplateFactory.getObject();
			return restTemplate;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
