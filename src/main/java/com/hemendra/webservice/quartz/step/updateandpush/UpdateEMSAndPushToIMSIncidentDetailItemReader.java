package com.hemendra.webservice.quartz.step.updateandpush;

import java.util.List;

import javax.xml.bind.JAXBElement;

import com.hemendra.webservice.quartz.component.StringTypeConverter;
import org.json.simple.JSONObject;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;
import com.hemendra.webservice.quartz.model.IncidentInstanceData;
import com.hemendra.webservice.quartz.service.UpdateIncidentService;
import com.hemendra.webservice.quartz.util.AppConstants;
import com.bipros.emsintegration.wsdl.IncidentInstanceType;
import com.bipros.emsintegration.wsdl.IncidentKeysType;
import com.bipros.emsintegration.wsdl.ObjectFactory;
import com.bipros.emsintegration.wsdl.StringType;

public class UpdateEMSAndPushToIMSIncidentDetailItemReader implements ItemReader<IncidentInstanceData> {

	private List<IncidentInstanceData> incidentInstanceDetailDatas = null;
	private int incidentInstanceDetailDataCount = 0;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${ims.username}")
	private String imsUserName;

	@Value("${ims.password}")
	private String imsPassword;
	
	@Value("${ims.host}")
	private String imsHost;
	
	@Value("${ims.port}")
	private int imsPort;
	
	@Value("${ems.update.status}")
	private String updateStatus;
	
	@Value("${ims.process.definition.id}")
	private String processDefinitionId;
	
	@Value("${ims.process.tenant.id}")
	private String tenantId;
	
	@Value("${ims.process.incident.category.ems}")
	private String emsCategory;
	
	@Autowired
	private UpdateIncidentService updateIncidentService;
		
	@Autowired
	private ObjectFactory objectFactory;
	
	@Autowired
	private StringTypeConverter stringTypeConverter;

	@Override
	public IncidentInstanceData read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		IncidentInstanceData instanceData = null;

		if (incidentInstanceDetailDatas != null
				&& incidentInstanceDetailDataCount < incidentInstanceDetailDatas.size()) {
			instanceData = incidentInstanceDetailDatas.get(incidentInstanceDetailDataCount++);
			
			//As we have all the data from EMS, update the status of incident to assign then push to IMS.
			StringType stringTypeincidentID = stringTypeConverter.convert(instanceData.getIncidentID());
			JAXBElement<StringType> elementTypeincidentID = objectFactory.createIncidentInstanceTypeIncidentID(stringTypeincidentID);
			
			IncidentKeysType incidentKeysType = new IncidentKeysType();
			incidentKeysType.setIncidentID(elementTypeincidentID);
			
			StringType stringTypeStatus = stringTypeConverter.convert(updateStatus);
			JAXBElement<StringType> elementTypeStatus = objectFactory.createIncidentInstanceTypeStatus(stringTypeincidentID);
			
			IncidentInstanceType incidentInstanceType = new IncidentInstanceType();
			incidentInstanceType.setStatus(elementTypeStatus);
			
			//update Ems incident status
			boolean updateStatus = updateIncidentService.updateIncidentService(incidentKeysType, incidentInstanceType);
			System.out.println("Update to EMS ............."+ updateStatus);
			
			//If the update to the EMS is successfull, push the data to the IMS
			if(!updateStatus) { //change this tomorro, it is for testing purpose keep when true
				// Add new Incident for Process AFCSIncident
				JSONObject requestObject = new JSONObject();
				requestObject.put("processDefinitionKey", processDefinitionId);
				requestObject.put("tenantId", tenantId);
				requestObject.put("title", instanceData.getTitle());
				requestObject.put("incidentDateTime", "" );
				requestObject.put("incidentCategory", emsCategory);
				requestObject.put("incidentSubCategory", "1001");
				requestObject.put("busNum", "OD02XC7897");
				requestObject.put("operatorId", "OPERA89");
				requestObject.put("priority", "Low");
				requestObject.put("severity", "Low");
				requestObject.put("equipmentId", "");
				requestObject.put("equipmentName", "");
				requestObject.put("routeId", "");
				requestObject.put("stopId", "");
				requestObject.put("location", "");
				requestObject.put("incidentDesc", "");
				
				//StringEntity requestEntity = new StringEntity(requestObject.toJSONString(), ContentType.APPLICATION_JSON);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> requestEntity = new HttpEntity<String>(requestObject.toString(),headers);
				
				restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(imsUserName, imsPassword));
				ResponseEntity<String> responseEntity = restTemplate.exchange("http://"+imsHost+":"+imsPort+"/engine-rest/imsExternalTask/createIncident", HttpMethod.POST, 
						requestEntity, String.class);
				System.out.println("################################------------------------------+++++++++++++++++++++" +responseEntity.getStatusCodeValue());
				
				if(responseEntity.hasBody()) {
					System.out.println("Response Body :"+responseEntity.getBody());
				}
			}			
			return instanceData;
		} else {
			this.incidentInstanceDetailDatas = null;
			this.incidentInstanceDetailDataCount = 0;
			return null;
		}
		
		
		
		
	}

	// Retrieve the list of Incidents from the
	@SuppressWarnings("unchecked")
	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();
		incidentInstanceDetailDatas = (List<IncidentInstanceData>) jobContext
				.get(AppConstants.INCIDENT_ID_DETAIL_FETCH_STEP_EXECUTION_KEY);
		System.out.println("Reader 3 someObject :" + incidentInstanceDetailDatas);
	}

}
