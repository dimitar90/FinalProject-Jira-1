package com.jira.model;

public class Project {
	private int id;
	private String name;
	private boolean isDeleted;
	private int projectTypeId;
	private int projectCategoryId;
	private int projectLeadId;

	public Project() {
		
	}
	
	public Project(int projectTypeId, String name, int projectCategoryId, int projectLeadId) {
		this.setName(name);
		this.setProjectTypeId(projectTypeId);
		this.setProjectCategoryId(projectCategoryId);
		this.setProjectLeadId(projectLeadId);
	}

	public Project(int id, String name, int projectTypeId, int projectCategoryId, int projectLeadId) {
		this(projectTypeId, name, projectCategoryId, projectLeadId);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(int projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public int getProjectCategoryId() {
		return projectCategoryId;
	}

	public void setProjectCategoryId(int projectCategoryId) {
		this.projectCategoryId = projectCategoryId;
	}

	public int getProjectLeadId() {
		return projectLeadId;
	}

	public void setProjectLeadId(int projectLeadId) {
		this.projectLeadId = projectLeadId;
	}

	public static Project createProject(String name, int projectCategoryId, int projectTypeId, int leadId) {
		return new Project(projectTypeId, name, projectCategoryId, leadId);
	}

	public static Project getProject(int id, String name, int projectTypeId, int projectCategoryId, int projectLeadId) {
		return new Project(id, name, projectTypeId, projectCategoryId, projectLeadId);
	}
}
