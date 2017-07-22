package com.hemendra.webservice.quartz.service;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.hemendra.webservice.quartz.component.StringTypeConverter;
import com.hemendra.webservice.quartz.util.AppConstants;
import com.bipros.emsintegration.wsdl.IncidentInstanceType;
import com.bipros.emsintegration.wsdl.IncidentKeysType;
import com.bipros.emsintegration.wsdl.IncidentModelType;
import com.bipros.emsintegration.wsdl.ObjectFactory;
import com.bipros.emsintegration.wsdl.RetrieveIncidentKeysListRequest;
import com.bipros.emsintegration.wsdl.RetrieveIncidentKeysListResponse;
import com.bipros.emsintegration.wsdl.StringType;

@Service
public class FetchIncidentIDListService {
	@Autowired
	private WebServiceTemplate serviceTemplate;
	@Autowired
	private ObjectFactory objectFactory;
	@Autowired
	private StringTypeConverter stringTypeConverter;
	@Autowired
	private RetrieveIncidentKeysListRequest retrieveIncidentKeysListRequest;
	
	public List<IncidentKeysType> getAllIncidentIDs(String status) {
		/**
		 * 
		<soapenv:Body>
	      <ns:RetrieveIncidentKeysListRequest>
	         <ns:model query="">
	            <ns:keys query="" updatecounter="">
	               <ns:IncidentID type="String" mandatory="" readonly=""></ns:IncidentID>
	            </ns:keys>
	            <ns:instance query="" uniquequery="" recordid="" updatecounter="">
	               <ns:Status type="String" mandatory="" readonly="">Categorize</ns:Status>
	            </ns:instance>
	         </ns:model>
	      </ns:RetrieveIncidentKeysListRequest>
	   </soapenv:Body>
		 */
		//Create objects as per the soap body 1. create model, keys and instance 
		IncidentModelType incidentModelType = new IncidentModelType();
		IncidentKeysType incidentKeysType = new IncidentKeysType();
		IncidentInstanceType incidentInstanceType = new IncidentInstanceType();
		
		StringType stringTypeStatus = stringTypeConverter.convert(status);
		JAXBElement<StringType> createIncidentInstanceTypeStatus = objectFactory.createIncidentInstanceTypeStatus(stringTypeStatus);
		incidentInstanceType.setStatus(createIncidentInstanceTypeStatus);
		
		//Set the instance
		incidentModelType.setKeys(incidentKeysType);
		incidentModelType.setInstance(incidentInstanceType);
		
		//Prepare the Request
		retrieveIncidentKeysListRequest.setModel(incidentModelType);
		
		//Send the request
		SoapActionCallback soapActionCallback = new SoapActionCallback(AppConstants.SoapAction.RETRIVE_KEYS_LIST);
		RetrieveIncidentKeysListResponse retrieveIncidentKeysListResponse = (RetrieveIncidentKeysListResponse) serviceTemplate.marshalSendAndReceive(retrieveIncidentKeysListRequest, soapActionCallback);
		return retrieveIncidentKeysListResponse.getKeys();
	}
}
