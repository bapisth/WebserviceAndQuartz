package com.hemendra.webservice.quartz.step.fetchid;

import org.springframework.batch.item.ItemProcessor;

public class FetchIncidentIDListItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		System.out.println("CustomItemProcessor :" +item);
		return item;
	}

}
