package com.hemendra.webservice.quartz.model;

import java.io.Serializable;
import java.util.Date;

public final class IncidentInstanceData implements Serializable {
	private String incidentID;
	private String category;
	private Date openTime;
	private String openedBy;
	private String urgency;
	private Date updatedTime;
	private String assignmentGroup;
	private Date closedTime;
	private String closedBy;
	private String closureCode;
	private String affectedCI;
	private String description;
	private String solution;
	private String assignee;
	private String contact;
	private String journalUpdates;
	private String alertStatus;
	private String contactLastName;
	private String contactFirstName;
	private String company;
	private String title;
	private String ticketOwner;
	private String updatedBy;
	private String status;
	private String phase;
	private String area;
	private String slaAgreementID;
	private String siteCategory;
	private String subarea;
	private String problemType;
	private String resolutionFixType;
	private String source;
	private String userPriority;
	private String location;
	private String explanation;
	private String impact;
	private String folder;
	private String service;
	private String incidentManager;

	public IncidentInstanceData(IncidentInstanceDataBuilder incidentInstanceDataBuilder) {
		this.incidentID = incidentInstanceDataBuilder.incidentID;
		this.category = incidentInstanceDataBuilder.category;
		this.openTime = incidentInstanceDataBuilder.openTime;
		this.openedBy = incidentInstanceDataBuilder.openedBy;
		this.urgency = incidentInstanceDataBuilder.urgency;
		this.updatedTime = incidentInstanceDataBuilder.updatedTime;
		this.assignmentGroup = incidentInstanceDataBuilder.assignmentGroup;
		this.closedTime = incidentInstanceDataBuilder.closedTime;
		this.closedBy = incidentInstanceDataBuilder.closedBy;
		this.closureCode = incidentInstanceDataBuilder.closureCode;
		this.affectedCI = incidentInstanceDataBuilder.affectedCI;
		this.description = incidentInstanceDataBuilder.description;
		this.solution = incidentInstanceDataBuilder.solution;
		this.assignee = incidentInstanceDataBuilder.assignee;
		this.contact = incidentInstanceDataBuilder.contact;
		this.journalUpdates = incidentInstanceDataBuilder.journalUpdates;
		this.alertStatus = incidentInstanceDataBuilder.alertStatus;
		this.contactLastName = incidentInstanceDataBuilder.contactLastName;
		this.contactFirstName = incidentInstanceDataBuilder.contactFirstName;
		this.company = incidentInstanceDataBuilder.company;
		this.title = incidentInstanceDataBuilder.title;
		this.ticketOwner = incidentInstanceDataBuilder.ticketOwner;
		this.updatedBy = incidentInstanceDataBuilder.updatedBy;
		this.status = incidentInstanceDataBuilder.status;
		this.phase = incidentInstanceDataBuilder.phase;
		this.area = incidentInstanceDataBuilder.area;
		this.slaAgreementID = incidentInstanceDataBuilder.slaAgreementID;
		this.siteCategory = incidentInstanceDataBuilder.siteCategory;
		this.subarea = incidentInstanceDataBuilder.subarea;
		this.problemType = incidentInstanceDataBuilder.problemType;
		this.resolutionFixType = incidentInstanceDataBuilder.resolutionFixType;
		this.source = incidentInstanceDataBuilder.source;
		this.userPriority = incidentInstanceDataBuilder.userPriority;
		this.location = incidentInstanceDataBuilder.location;
		this.explanation = incidentInstanceDataBuilder.explanation;
		this.impact = incidentInstanceDataBuilder.impact;
		this.folder = incidentInstanceDataBuilder.folder;
		this.service = incidentInstanceDataBuilder.service;
		this.incidentManager = incidentInstanceDataBuilder.incidentManager;
	}

	public String getIncidentID() {
		return incidentID;
	}

	public String getCategory() {
		return category;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public String getOpenedBy() {
		return openedBy;
	}

	public String getUrgency() {
		return urgency;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public Date getClosedTime() {
		return closedTime;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public String getClosureCode() {
		return closureCode;
	}

	public String getAffectedCI() {
		return affectedCI;
	}

	public String getDescription() {
		return description;
	}

	public String getSolution() {
		return solution;
	}

	public String getAssignee() {
		return assignee;
	}

	public String getContact() {
		return contact;
	}

	public String getJournalUpdates() {
		return journalUpdates;
	}

	public String getAlertStatus() {
		return alertStatus;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public String getCompany() {
		return company;
	}

	public String getTitle() {
		return title;
	}

	public String getTicketOwner() {
		return ticketOwner;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public String getStatus() {
		return status;
	}

	public String getPhase() {
		return phase;
	}

	public String getArea() {
		return area;
	}

	public String getSlaAgreementID() {
		return slaAgreementID;
	}

	public String getSiteCategory() {
		return siteCategory;
	}

	public String getSubarea() {
		return subarea;
	}

	public String getProblemType() {
		return problemType;
	}

	public String getResolutionFixType() {
		return resolutionFixType;
	}

	public String getSource() {
		return source;
	}

	public String getUserPriority() {
		return userPriority;
	}

	public String getLocation() {
		return location;
	}

	public String getExplanation() {
		return explanation;
	}

	public String getImpact() {
		return impact;
	}

	public String getFolder() {
		return folder;
	}

	public String getService() {
		return service;
	}

	public String getIncidentManager() {
		return incidentManager;
	}

	public static class IncidentInstanceDataBuilder {
		private String incidentID;
		private String category;
		private Date openTime;
		private String openedBy;
		private String urgency;
		private Date updatedTime;
		private String assignmentGroup;
		private Date closedTime;
		private String closedBy;
		private String closureCode;
		private String affectedCI;
		private String description;
		private String solution;
		private String assignee;
		private String contact;
		private String journalUpdates;
		private String alertStatus;
		private String contactLastName;
		private String contactFirstName;
		private String company;
		private String title;
		private String ticketOwner;
		private String updatedBy;
		private String status;
		private String phase;
		private String area;
		private String slaAgreementID;
		private String siteCategory;
		private String subarea;
		private String problemType;
		private String resolutionFixType;
		private String source;
		private String userPriority;
		private String location;
		private String explanation;
		private String impact;
		private String folder;
		private String service;
		private String incidentManager;

		public IncidentInstanceDataBuilder withIncidentID(String incidentID) {
			this.incidentID = incidentID;
			return this;
		}

		public IncidentInstanceDataBuilder withCategory(String category) {
			this.category = category;
			return this;
		}

		public IncidentInstanceDataBuilder withOpenTime(Date openTime) {
			this.openTime = openTime;
			return this;
		}

		public IncidentInstanceDataBuilder withOpenedBy(String openedBy) {
			this.openedBy = openedBy;
			return this;
		}

		public IncidentInstanceDataBuilder withUrgency(String urgency) {
			this.urgency = urgency;
			return this;
		}

		public IncidentInstanceDataBuilder withUpdatedTime(Date updatedTime) {
			this.updatedTime = updatedTime;
			return this;
		}

		public IncidentInstanceDataBuilder withAssignmentGroup(String assignmentGroup) {
			this.assignmentGroup = assignmentGroup;
			return this;
		}

		public IncidentInstanceDataBuilder withClosedTime(Date closedTime) {
			this.closedTime = closedTime;
			return this;
		}

		public IncidentInstanceDataBuilder withClosedBy(String closedBy) {
			this.closedBy = closedBy;
			return this;
		}

		public IncidentInstanceDataBuilder withClosureCode(String closureCode) {
			this.closureCode = closureCode;
			return this;
		}

		public IncidentInstanceDataBuilder withAffectedCI(String affectedCI) {
			this.affectedCI = affectedCI;
			return this;
		}

		public IncidentInstanceDataBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public IncidentInstanceDataBuilder withSolution(String solution) {
			this.solution = solution;
			return this;
		}

		public IncidentInstanceDataBuilder withAssignee(String assignee) {
			this.assignee = assignee;
			return this;
		}

		public IncidentInstanceDataBuilder withContact(String contact) {
			this.contact = contact;
			return this;
		}

		public IncidentInstanceDataBuilder withJournalUpdates(String journalUpdates) {
			this.journalUpdates = journalUpdates;
			return this;
		}

		public IncidentInstanceDataBuilder withAlertStatus(String alertStatus) {
			this.alertStatus = alertStatus;
			return this;
		}

		public IncidentInstanceDataBuilder withContactLastName(String contactLastName) {
			this.contactLastName = contactLastName;
			return this;
		}

		public IncidentInstanceDataBuilder withContactFirstName(String contactFirstName) {
			this.contactFirstName = contactFirstName;
			return this;
		}

		public IncidentInstanceDataBuilder withCompany(String company) {
			this.company = company;
			return this;
		}

		public IncidentInstanceDataBuilder withTitle(String title) {
			this.title = title;
			return this;
		}

		public IncidentInstanceDataBuilder withTicketOwner(String ticketOwner) {
			this.ticketOwner = ticketOwner;
			return this;
		}

		public IncidentInstanceDataBuilder withUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
			return this;
		}

		public IncidentInstanceDataBuilder withStatus(String status) {
			this.status = status;
			return this;
		}

		public IncidentInstanceDataBuilder withPhase(String phase) {
			this.phase = phase;
			return this;
		}

		public IncidentInstanceDataBuilder withArea(String area) {
			this.area = area;
			return this;
		}

		public IncidentInstanceDataBuilder withSlaAgreementID(String slaAgreementID) {
			this.slaAgreementID = slaAgreementID;
			return this;
		}

		public IncidentInstanceDataBuilder withSiteCategory(String siteCategory) {
			this.siteCategory = siteCategory;
			return this;
		}

		public IncidentInstanceDataBuilder withSubarea(String subarea) {
			this.subarea = subarea;
			return this;
		}

		public IncidentInstanceDataBuilder withProblemType(String problemType) {
			this.problemType = problemType;
			return this;
		}

		public IncidentInstanceDataBuilder withResolutionFixType(String resolutionFixType) {
			this.resolutionFixType = resolutionFixType;
			return this;
		}

		public IncidentInstanceDataBuilder withSource(String source) {
			this.source = source;
			return this;
		}

		public IncidentInstanceDataBuilder withUserPriority(String userPriority) {
			this.userPriority = userPriority;
			return this;
		}

		public IncidentInstanceDataBuilder withLocation(String location) {
			this.location = location;
			return this;
		}

		public IncidentInstanceDataBuilder withExplanation(String explanation) {
			this.explanation = explanation;
			return this;
		}

		public IncidentInstanceDataBuilder withImpact(String impact) {
			this.impact = impact;
			return this;
		}

		public IncidentInstanceDataBuilder withFolder(String folder) {
			this.folder = folder;
			return this;
		}

		public IncidentInstanceDataBuilder withService(String service) {
			this.service = service;
			return this;
		}

		public IncidentInstanceDataBuilder withIncidentManager(String incidentManager) {
			this.incidentManager = incidentManager;
			return this;
		}

		public IncidentInstanceData build() {
			return new IncidentInstanceData(this);
		}
	}

	@Override
	public String toString() {
		return "IncidentInstanceData [incidentID=" + incidentID + ", category=" + category + ", openTime=" + openTime
				+ ", openedBy=" + openedBy + ", urgency=" + urgency + ", updatedTime=" + updatedTime
				+ ", assignmentGroup=" + assignmentGroup + ", closedTime=" + closedTime + ", closedBy=" + closedBy
				+ ", closureCode=" + closureCode + ", affectedCI=" + affectedCI + ", description=" + description
				+ ", solution=" + solution + ", assignee=" + assignee + ", contact=" + contact + ", journalUpdates="
				+ journalUpdates + ", alertStatus=" + alertStatus + ", contactLastName=" + contactLastName
				+ ", contactFirstName=" + contactFirstName + ", company=" + company + ", title=" + title
				+ ", ticketOwner=" + ticketOwner + ", updatedBy=" + updatedBy + ", status=" + status + ", phase="
				+ phase + ", area=" + area + ", slaAgreementID=" + slaAgreementID + ", siteCategory=" + siteCategory
				+ ", subarea=" + subarea + ", problemType=" + problemType + ", resolutionFixType=" + resolutionFixType
				+ ", source=" + source + ", userPriority=" + userPriority + ", location=" + location + ", explanation="
				+ explanation + ", impact=" + impact + ", folder=" + folder + ", service=" + service
				+ ", incidentManager=" + incidentManager + "]";
	}
}
