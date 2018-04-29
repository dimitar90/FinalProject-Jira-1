package com.jira.model;

public class ProjectCategory {
	private int id;
	private ProjectCategoryEnum category;

	public ProjectCategory(ProjectCategoryEnum category) {
		this.setCategory(category);
	}

	public ProjectCategory(int id, ProjectCategoryEnum category) {
		this(category);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProjectCategoryEnum getCategory() {
		return category;
	}

	public void setCategory(ProjectCategoryEnum category) {
		this.category = category;
	}

	public static ProjectCategory getProjectCategory(int id, ProjectCategoryEnum category) {
		return new ProjectCategory(id, category);
	}

}
