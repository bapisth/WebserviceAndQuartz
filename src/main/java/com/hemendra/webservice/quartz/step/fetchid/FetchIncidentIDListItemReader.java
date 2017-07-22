package com.hemendra.webservice.quartz.step.fetchid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.hemendra.webservice.quartz.service.FetchIncidentIDListService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bipros.emsintegration.wsdl.IncidentKeysType;

/**
 * Created by hp on 15-07-2017.
 */
public class FetchIncidentIDListItemReader implements ItemReader<String> {
	
	@Value("${ems.fetch.status}")
	private String INCIDENT_ID_FETCH_STATUS;
	
	@Autowired
	private FetchIncidentIDListService fetchIncidentIDListService;

	private int incidentIDCount = 0;
	private List<String> incidentIds = null;

	@PostConstruct
	public void initialize() {
		this.incidentIds = incidentKeysTypes();
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		boolean incidentDataIsNotInitialized = incidentDataIsNotInitialized();
		System.out.println("incidentDataIsNotInitialized :" + incidentDataIsNotInitialized);
		if (incidentDataIsNotInitialized) {
			incidentIds = new ArrayList<>();
			incidentIds = incidentKeysTypes();
		}

		String incidentId = null;

		if (incidentIds!= null && incidentIDCount < incidentIds.size()) {
			incidentId = incidentIds.get(incidentIDCount++);
			return incidentId;
		} else {
			this.incidentIds = null;
			this.incidentIDCount = 0;
			return null;
		}
	}

	private boolean incidentDataIsNotInitialized() {
		return this.incidentIds == null;
	}

	private List<String> incidentKeysTypes() {
		List<IncidentKeysType> allIncidentIDs = fetchIncidentIDListService.getAllIncidentIDs(INCIDENT_ID_FETCH_STATUS);

		List<String> incidentIDs = allIncidentIDs.stream().map(data -> {
			String incidentId = data.getIncidentID().getValue().getValue();
			return incidentId;
		}).collect(Collectors.toList());

		return incidentIDs;
	}
}
