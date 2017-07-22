package com.hemendra.webservice.quartz.service;

import com.hemendra.webservice.quartz.component.StringTypeConverter;
import com.hemendra.webservice.quartz.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.bipros.emsintegration.wsdl.IncidentInstanceType;
import com.bipros.emsintegration.wsdl.IncidentKeysType;
import com.bipros.emsintegration.wsdl.IncidentModelType;
import com.bipros.emsintegration.wsdl.ObjectFactory;
import com.bipros.emsintegration.wsdl.StatusType;
import com.bipros.emsintegration.wsdl.UpdateIncidentRequest;
import com.bipros.emsintegration.wsdl.UpdateIncidentResponse;

@Service
public class UpdateIncidentService {
	@Autowired
	private WebServiceTemplate serviceTemplate;
	@Autowired
	private ObjectFactory objectFactory;
	@Autowired
	private StringTypeConverter stringTypeConverter;
	@Autowired
	private UpdateIncidentRequest updateIncidentRequest;
	
	public boolean updateIncidentService(IncidentKeysType incidentKeysType, IncidentInstanceType incidentInstanceType) {
		/**
		 * 
	   <soapenv:Body>
	      <ns:UpdateIncidentRequest attachmentInfo="" attachmentData="" ignoreEmptyElements="true" updateconstraint="-1">
	         <ns:model query="">
	            <ns:keys query="" updatecounter="">
	               <ns:IncidentID type="String" mandatory="" readonly="">IM10002</ns:IncidentID>
	            </ns:keys>
	            <ns:instance query="" uniquequery="" recordid="" updatecounter="">
	               <ns:Status type="String" mandatory="" readonly="">Assign</ns:Status>
	            </ns:instance>
	            <!--Optional:-->
	            <ns:messages>
	               <com:message type="String" mandatory="" readonly="" severity="" module=""></com:message>
	            </ns:messages>
	         </ns:model>
	      </ns:UpdateIncidentRequest>
	   </soapenv:Body>
		 * 
		 */
		
		//Create the instance of model, keys, instance
		IncidentModelType incidentModelType = new IncidentModelType();
		incidentModelType.setKeys(incidentKeysType);
		incidentModelType.setInstance(incidentInstanceType);
		
		updateIncidentRequest.setModel(incidentModelType);
		
		UpdateIncidentResponse updateIncidentResponse = 
				(UpdateIncidentResponse) serviceTemplate.marshalSendAndReceive(updateIncidentRequest, new SoapActionCallback(AppConstants.SoapAction.UPDATE));
		StatusType status = updateIncidentResponse.getStatus();
		
		return (StatusType.SUCCESS == status);
	} 
}
