package com.app.cybercrime.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Evidence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evidenceId;
    private Long caseId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadTime;
	public Long getEvidenceId() {
		return evidenceId;
	}
	public void setEvidenceId(Long evidenceId) {
		this.evidenceId = evidenceId;
	}
	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public LocalDateTime getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

    // Getters and Setters
}
