package com.jira.model;

public class ProjectType {
	private int id;
	private ProjectTypeEnum type;

	public ProjectType(ProjectTypeEnum type) {
		this.setType(type);
	}

	public ProjectType(int id, ProjectTypeEnum type) {
		this(type);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProjectTypeEnum getType() {
		return type;
	}

	public void setType(ProjectTypeEnum type) {
		this.type = type;
	}

	public static ProjectType getProjectType(int id, ProjectTypeEnum type) {
		return new ProjectType(id, type);
	}
}
