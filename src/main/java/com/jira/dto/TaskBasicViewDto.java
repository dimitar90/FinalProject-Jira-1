package com.jira.dto;

import java.time.LocalDate;

import com.jira.model.Project;
import com.jira.model.TaskPriority;
import com.jira.model.TaskState;
import com.jira.model.User;

public class TaskBasicViewDto {
	private int id;
	private Project project;
	private String summary;
	private TaskPriority priority;
	private User assignee;
	private LocalDate dueDate;
	private TaskState state;

	public TaskBasicViewDto(int id, Project project, String summary, TaskPriority priority, User assignee,
			LocalDate dueDate, TaskState state) {
		super();
		this.setId(id);
		this.setProject(project);
		this.setSummary(summary);
		this.setPriority(priority);
		this.setAssignee(assignee);
		this.setDueDate(dueDate);
		this.setState(state);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}
}
