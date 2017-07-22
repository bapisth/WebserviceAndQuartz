package com.hemendra.webservice.quartz.component;

import org.springframework.stereotype.Component;
import com.bipros.emsintegration.wsdl.FloatType;

@Component
public class FloatTypeConverter implements Converter<FloatType, Float> {
	
	@Override
	public FloatType convert(Float value) {
		FloatType floatType = new FloatType();
		floatType.setValue(value);
		return floatType;
	}
	
}
