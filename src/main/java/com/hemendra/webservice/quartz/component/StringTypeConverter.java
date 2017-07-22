package com.hemendra.webservice.quartz.component;

import org.springframework.stereotype.Component;
import com.bipros.emsintegration.wsdl.StringType;

@Component
public class StringTypeConverter implements Converter<StringType, String> {

	@Override
	public StringType convert(String value) {
		StringType stringType = new StringType();
		stringType.setValue(value);
		return stringType;
	}
	
}
