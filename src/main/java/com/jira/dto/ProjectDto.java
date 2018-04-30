package com.jira.dto;

public class ProjectDto {

	private int id;
	private String name;
	private String projectType;
	private String projectCategory;
	private String projectLead;
	private int projectLeadId;
	
	public ProjectDto(int id, String name, String projectType, String projectCategory, String projectLead,int projectLeadId) {
		this.setId(id);
		this.setName(name);
		this.setProjectType(projectType);
		this.setProjectCategory(projectCategory);
		this.setProjectLead(projectLead);
		this.projectLeadId = projectLeadId;
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

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getProjectLead() {
		return projectLead;
	}

	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}

	
	public int getProjectLeadId() {
		return projectLeadId;
	}
	
	public void setProjectLeadId(int projectLeadId) {
		this.projectLeadId = projectLeadId;
	}
	public static ProjectDto getDto(int id, String projectName, String projectType, String projectCategory, String projectLead,int projectLeadId) {
		return new ProjectDto(id, projectName, projectType, projectCategory, projectLead,projectLeadId);
	}
	
	

}
