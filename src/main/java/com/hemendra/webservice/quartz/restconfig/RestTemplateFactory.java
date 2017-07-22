package com.hemendra.webservice.quartz.restconfig;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {
	
	@Value("${ims.host}")
	private String imsHost;
	
	@Value("${ims.port}")
	private int imsPort;

	private RestTemplate restTemplate;

	public RestTemplate getObject() {
		return restTemplate;
	}

	public Class<RestTemplate> getObjectType() {
		return RestTemplate.class;
	}

	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		HttpHost host = new HttpHost(imsHost, imsPort, "http");
		restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(host));
	}

}
