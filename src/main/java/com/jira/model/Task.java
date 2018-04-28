package com.jira.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jira.dto.CommentViewDto;

public class Task {
	private int id;
	private String summary;
	private LocalDate dueDate;
	private LocalDate startDate;
	private String description;
	private boolean isDeleted;
	private int projectId;
	private int priorityId;
	private int stateId;
	private int issueId;
	private int creatorId;
	private int assigneeId;
	private List<String> imageUrls;
	
	public Task() {}
	
	public Task(String summary, LocalDate dueDate, LocalDate startDate, String description, int projectId,
			int priorityId, int stateId, int issueId, int creatorId, int assigneeId) {
		this.setSummary(summary);
		this.setDueDate(dueDate);
		this.setStartDate(startDate);
		this.setDescription(description);
		this.setProjectId(projectId);
		this.setPriorityId(priorityId);
		this.setStateId(stateId);
		this.setIssueId(issueId);
		this.setCreatorId(creatorId);
		this.setAssigneeId(assigneeId);
		this.setDeleted(false);
		this.imageUrls = new ArrayList<>();
	}

	public Task(int id, String summary, LocalDate dueDate, LocalDate startDate, String description,
			int projectId, int priorityId, int stateId, int issueId, int creatorId, int assigneeId) {
		this(summary, dueDate, startDate, description, projectId, priorityId, stateId, issueId, creatorId, assigneeId);
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

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
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

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
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

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = new ArrayList<String> (imageUrls);
	}
}
