package com.hemendra.webservice.quartz.service;

import java.util.List;

import javax.xml.bind.JAXBElement;

import com.hemendra.webservice.quartz.component.StringTypeConverter;
import com.hemendra.webservice.quartz.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.bipros.emsintegration.wsdl.IncidentInstanceType;
import com.bipros.emsintegration.wsdl.IncidentKeysType;
import com.bipros.emsintegration.wsdl.ObjectFactory;
import com.bipros.emsintegration.wsdl.RetrieveIncidentListRequest;
import com.bipros.emsintegration.wsdl.RetrieveIncidentListResponse;
import com.bipros.emsintegration.wsdl.StringType;

@Service
public class IncidentManagementService {
	@Autowired
	private WebServiceTemplate serviceTemplate;
	
	@Autowired
	private ObjectFactory objectFactory;
	
	@Autowired
	private StringTypeConverter stringTypeConverter;
	
	@Autowired
	private RetrieveIncidentListRequest retrieveIncidentListRequest;
	
	public List<IncidentInstanceType> getAllIncidents(List<String> incidentIds) {
		List<IncidentInstanceType> incidentInstanceTypes = null;
		retrieveIncidentListRequest.getKeys().clear();
		
		for(String incidentId: incidentIds) {
			StringType stringTypeIncidentId = stringTypeConverter.convert(incidentId);
			JAXBElement<StringType> createIncidentKeysTypeIncidentID = objectFactory.createIncidentKeysTypeIncidentID(stringTypeIncidentId);
			//Prepare the Request
			IncidentKeysType type = new IncidentKeysType();
			type.setIncidentID(createIncidentKeysTypeIncidentID);
			retrieveIncidentListRequest.getKeys().add(type);
		}
		
		//Send Request and accept response
		RetrieveIncidentListResponse incidentListResponse = 
		(RetrieveIncidentListResponse) serviceTemplate.marshalSendAndReceive(retrieveIncidentListRequest, new SoapActionCallback(AppConstants.SoapAction.RETRIVE_LIST));
		incidentInstanceTypes = incidentListResponse.getInstance();
		
		return incidentInstanceTypes;
	}
	
}
