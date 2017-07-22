package com.hemendra.webservice.quartz.component;

public  interface Converter<T, F> {
	T convert(F from);
}
