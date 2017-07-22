package com.hemendra.webservice.quartz.step.fetchiddetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.hemendra.webservice.quartz.model.IncidentInstanceData;
import com.hemendra.webservice.quartz.model.IncidentInstanceData.IncidentInstanceDataBuilder;
import com.hemendra.webservice.quartz.service.IncidentManagementService;
import com.hemendra.webservice.quartz.util.AppConstants;
import com.bipros.emsintegration.wsdl.DecimalType;
import com.bipros.emsintegration.wsdl.IncidentInstanceType;
import com.bipros.emsintegration.wsdl.IncidentInstanceType.Description;
import com.bipros.emsintegration.wsdl.IncidentInstanceType.Explanation;
import com.bipros.emsintegration.wsdl.IncidentInstanceType.JournalUpdates;
import com.bipros.emsintegration.wsdl.IncidentInstanceType.Solution;
import com.bipros.emsintegration.wsdl.StringType;

public class FetchDetailInfoForIncidentIDItemReader implements ItemReader<IncidentInstanceData> {
	private List<String> incidentListFromFirstStep;

	@Autowired
	private IncidentManagementService incidentManagementService;

	private int incidentInstanceDataCount = 0;
	private List<IncidentInstanceData> incidentInstanceDataList = null;

	@Override
	public IncidentInstanceData read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		boolean incidentInstanceDataIsNotInitialized = incidentInstanceDataIsNotInitialized();
		if (incidentInstanceDataIsNotInitialized) {
			incidentInstanceDataList = new ArrayList<>();
			incidentInstanceDataList = getIncidentIdDetail();
		}

		IncidentInstanceData instanceData = null;
		if (incidentInstanceDataList != null && incidentInstanceDataCount < incidentInstanceDataList.size()) {
			instanceData = incidentInstanceDataList.get(incidentInstanceDataCount++);
			return instanceData;
		} else {
			this.incidentInstanceDataList = null;
			this.incidentInstanceDataCount = 0;
			return null;
		}
	}

	private boolean incidentInstanceDataIsNotInitialized() {
		return this.incidentInstanceDataList == null;
	}

	// Retrieve the list of Incidents from the
	@SuppressWarnings("unchecked")
	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();
		this.incidentListFromFirstStep = (List<String>) jobContext.get(AppConstants.INCIDENT_ID_FETCH_STEP_EXECUTION_KEY);
		System.out.println("Reader 2 someObject :" + incidentListFromFirstStep);
	}

	private List<IncidentInstanceData> getIncidentIdDetail() {
		List<IncidentInstanceData> instanceDatas = new ArrayList<>();
		List<IncidentInstanceType> allIncidents = new ArrayList<>();

		if (incidentListFromFirstStep != null) {
			allIncidents = incidentManagementService.getAllIncidents(incidentListFromFirstStep);
			instanceDatas = getIncidentInstanceData(allIncidents);
		}

		return instanceDatas;

	}

	private List<IncidentInstanceData> getIncidentInstanceData(List<IncidentInstanceType> allIncidents) {
		List<IncidentInstanceData> incidentInstancDatas = null;
		incidentInstancDatas = allIncidents.stream().map(data -> {
			
			long openDateTime = 0L;
			if (data!= null && data.getOpenTime() != null && data.getOpenTime().getValue() != null) {
				XMLGregorianCalendar openTime = data.getOpenTime().getValue().getValue();
				openDateTime = openTime.toGregorianCalendar().getTime().getTime();
			}

			long updatedDateTime = 0L;
			if (data!= null && data.getUpdatedTime() != null && data.getUpdatedTime().getValue() != null) {
				XMLGregorianCalendar updatedTime = data.getUpdatedTime().getValue().getValue();
				updatedDateTime = updatedTime.toGregorianCalendar().getTime().getTime();
			}

			long closedDateTime = 0L;
			if (data!= null && data.getClosedTime() != null && data.getClosedTime().getValue() != null) {
				XMLGregorianCalendar closedTime = data.getClosedTime().getValue().getValue();
				closedDateTime = closedTime.toGregorianCalendar().getTime().getTime();
			}

			Description description = data.getDescription();
			String descriptionData = "";
			if (description != null) {
				List<StringType> descriptionList = description.getDescription();
				descriptionData = descriptionList.stream().map(descData -> {
					String desc = "";
					if (descData.getValue() != null) {
						desc = descData.getValue();
					}
					return desc;
				}).collect(Collectors.joining("/n"));
			}

			JournalUpdates journalUpdates = data.getJournalUpdates();
			String journalUpdatesData = "";
			if (journalUpdates != null) {
				List<StringType> journalUpdatesList = journalUpdates.getJournalUpdates();
				journalUpdatesData = journalUpdatesList.stream().map(jrnlUpdat -> {
					return jrnlUpdat.getValue();
				}).collect(Collectors.joining("/n"));
			}

			Solution solution = data.getSolution();
			String solutionData = "";
			if (solution != null) {
				List<StringType> solutionList = solution.getSolution();
				solutionData = solutionList.stream().map(solnData -> {

					return solnData.getValue();
				}).collect(Collectors.joining("/n"));
			}

			JAXBElement<DecimalType> slaAgrmntID = data.getSLAAgreementID();
			String slaAgreementId = "";
			if (slaAgrmntID != null && slaAgrmntID.getValue() != null) {
				BigDecimal value = slaAgrmntID.getValue().getValue();
				slaAgreementId = String.valueOf("" + value);
			}

			Explanation explanation = data.getExplanation();
			String explanationData = "";
			if (explanation != null) {
				List<StringType> explanationList = explanation.getExplanation();
				explanationData = explanationList.stream().map(explnData -> {
					return explnData.getValue();
				}).collect(Collectors.joining("/n"));
			}
			
			IncidentInstanceData instanceData = null;
			instanceData = new IncidentInstanceDataBuilder()
					.withIncidentID(data.getIncidentID()!=null? data.getIncidentID().getValue().getValue():"")
					.withOpenTime(new Date(openDateTime))
					.withOpenedBy(data.getOpenedBy() != null ? data.getOpenedBy().getValue().getValue() : "")
					.withUrgency(data.getUrgency() != null ? data.getUrgency().getValue().getValue() : "")
					.withUpdatedTime(new Date(updatedDateTime))
					.withAssignmentGroup(data.getAssignmentGroup() != null ? data.getAssignmentGroup().getValue().getValue() : "")
					.withClosedTime(new Date(closedDateTime))
					.withClosedBy(data.getClosedBy() != null ? data.getClosedBy().getValue().getValue() : "")
					.withClosureCode(data.getClosureCode() != null ? data.getClosureCode().getValue().getValue() : "")
					.withAffectedCI(data.getAffectedCI() != null ? data.getAffectedCI().getValue().getValue() : "")
					.withDescription(descriptionData).withSolution(solutionData)
					.withAssignee(data.getAssignee() != null ? data.getAssignee().getValue().getValue() : "")
					.withContact(data.getContact() != null ? data.getContact().getValue().getValue() : "")
					.withJournalUpdates(journalUpdatesData)
					.withAlertStatus(data.getAlertStatus() != null ? data.getAlertStatus().getValue().getValue() : "")
					.withContactLastName(data.getContactLastName() != null ? data.getContactLastName().getValue().getValue(): "")
					.withContactFirstName(data.getContactFirstName() != null ? data.getContactFirstName().getValue().getValue(): "")
					.withCompany(data.getCompany() != null ? data.getCompany().getValue().getValue() : "")
					.withTitle(data.getTitle() != null ? data.getTitle().getValue().getValue() : "")
					.withTicketOwner(data.getTicketOwner() != null ? data.getTicketOwner().getValue().getValue() : "")
					.withUpdatedBy(data.getUpdatedBy() != null ? data.getUpdatedBy().getValue().getValue() : "")
					.withStatus(data.getStatus() != null ? data.getStatus().getValue().getValue() : "")
					.withPhase(data.getPhase() != null ? data.getPhase().getValue().getValue() : "")
					.withArea(data.getArea() != null ? data.getArea().getValue().getValue() : "")
					.withSlaAgreementID(slaAgreementId)
					.withSiteCategory(data.getSiteCategory() != null ? data.getSiteCategory().getValue().getValue() : "")
					.withSubarea(data.getSubarea() != null ? data.getSubarea().getValue().getValue() : "")
					.withProblemType(data.getProblemType() != null ? data.getProblemType().getValue().getValue() : "")
					.withResolutionFixType(data.getResolutionFixType() != null ? data.getResolutionFixType().getValue().getValue() : "")
					.withSource(data.getSource() != null ? data.getSource().getValue().getValue() : "")
					.withUserPriority(data.getUserPriority() != null ? data.getUserPriority().getValue().getValue() : "")
					.withLocation(data.getLocation() != null ? data.getLocation().getValue().getValue() : "")
					.withExplanation(explanationData)
					.withImpact(data.getImpact() != null ? data.getImpact().getValue().getValue() : "")
					.withFolder(data.getFolder() != null ? data.getFolder().getValue().getValue() : "")
					.withService(data.getService() != null ? data.getService().getValue().getValue() : "")
					.withIncidentManager(data.getIncidentManager() != null ? data.getIncidentManager().getValue().getValue(): "")
					.build();
			return instanceData;
		}).collect(Collectors.toList());
		
		return incidentInstancDatas;
	}

}
