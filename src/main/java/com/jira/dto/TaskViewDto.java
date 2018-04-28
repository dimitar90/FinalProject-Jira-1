package com.jira.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.stereotype.Component;

import com.jira.model.Project;
import com.jira.model.TaskIssue;
import com.jira.model.TaskPriority;
import com.jira.model.TaskState;
import com.jira.model.User;

@EntityScan
public class TaskViewDto {
	private int id;
	private String summary;
	private LocalDate dueDate;
	private LocalDate startDate;
	private String description;
	private Project project;
	private TaskPriority priority;
	private TaskState state;
	private TaskIssue issue;
	private User creator;
	private User assignee;
	private List<String> imageUrls;
	private Set<CommentViewDto> comments;

	public TaskViewDto(int id, Project project, String summary, LocalDate dueDate, LocalDate startDate, String description, TaskPriority priority, TaskState state, TaskIssue issue, User creator, User assignee) {
		this.setId(id);
		this.setProject(project);
		this.setSummary(summary);
		this.setDueDate(dueDate);
		this.setStartDate(startDate); 
		this.setDescription(description);
		this.setPriority(priority);
		this.setState(state); 
		this.setIssue(issue);
		this.setCreator(creator); 
		this.setAssignee(assignee);
		this.imageUrls = new ArrayList<>();
		this.comments = new TreeSet<CommentViewDto>((c1, c2) -> c2.getDateTime().compareTo(c1.getDateTime()));
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
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public TaskIssue getIssue() {
		return issue;
	}

	public void setIssue(TaskIssue issue) {
		this.issue = issue;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = new ArrayList<>(imageUrls);
	}

	public Set<CommentViewDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentViewDto> comments) {
		this.comments.addAll(new ArrayList<>(comments));
	}
}
