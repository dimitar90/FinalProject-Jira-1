package com.jira.dto;


public class ProjectTypeBusinessDto {

	private int id;
	private String name;
	private String projectType;
	private String projectCategory;
	private String projectLead;

	public ProjectTypeBusinessDto(int id, String name, String projectType, String projectCategory, String projectLead) {
		this.setId(id);
		this.setName(name);
		this.setProjectType(projectType);
		this.setProjectCategory(projectCategory);
		this.setProjectLead(projectLead);
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

	public static ProjectTypeBusinessDto getDto(int id, String projectName, String projectType, String projectCategory, String projectLead) {
		return new ProjectTypeBusinessDto(id, projectName, projectType, projectCategory, projectLead);
	}
}
