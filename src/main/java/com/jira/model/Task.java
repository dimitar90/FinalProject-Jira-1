package com.jira.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Task {
	private int id;
	private String summary;
	private String dueDate;
	private String startDate;
	private String description;
	private boolean isDeleted;
	private int projectId;
	private int priorityId;
	private int issueId;
	private int creatorId;
	private int assigneeId;
	private List<String> imageUrls;
	
	public Task() {}
	
	public Task(String summary, String dueDate, String startDate, String description, int projectId,
			int priorityId,int issueId, int creatorId, int assigneeId) {
		this.setSummary(summary);
		this.setDueDate(dueDate);
		this.setStartDate(startDate);
		this.setDescription(description);
		this.setProjectId(projectId);
		this.setPriorityId(priorityId);
		this.setIssueId(issueId);
		this.setCreatorId(creatorId);
		this.setAssigneeId(assigneeId);
		this.setDeleted(false);
		this.imageUrls = new ArrayList<>();
	}

	public Task(int id, String summary, String dueDate, String startDate, String description,
			int projectId, int priorityId, int issueId, int creatorId, int assigneeId) {
		this(summary, dueDate, startDate, description, projectId, priorityId, issueId, creatorId, assigneeId);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(int priorityId) {
		this.priorityId = priorityId;
	}
	
	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public List<String> getImageUrls() {
		return Collections.unmodifiableList(this.imageUrls);
	}

	public void addImageUrl(String imageUrl) {
		this.imageUrls.add(imageUrl);
	}
}
