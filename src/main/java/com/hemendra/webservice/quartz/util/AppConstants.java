package com.hemendra.webservice.quartz.util;

public interface AppConstants {
	String INCIDENT_ID_FETCH_STEP_EXECUTION_KEY = "incidentIdList";
	String INCIDENT_ID_DETAIL_FETCH_STEP_EXECUTION_KEY="incidentIdDetailData";
	String EMS_INTEGRATION_JOB_NAME = "imsIntegration";
	
	interface SoapAction {
		String RETRIVE_KEYS_LIST = "RetrieveKeysList";
		String RETRIVE_LIST = "RetrieveList";
		String UPDATE = "Update";
	}

}
