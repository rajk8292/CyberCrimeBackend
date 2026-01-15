package com.app.cybercrime.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class CrimeCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseId;
    private String caseTitle;
    private String crimeType;
    private String description;
    private String status;
    private String assignedOfficer;
    private LocalDate createdDate;
	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public String getCaseTitle() {
		return caseTitle;
	}
	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}
	public String getCrimeType() {
		return crimeType;
	}
	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssignedOfficer() {
		return assignedOfficer;
	}
	public void setAssignedOfficer(String assignedOfficer) {
		this.assignedOfficer = assignedOfficer;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

    // Getters and Setters
    // (or use Lombok @Data)
}
