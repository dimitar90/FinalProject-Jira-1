package com.jira.model;

public enum ProjectCategoryEnum {
	noCategory("No Category"), atlassianAddOns("Atlassian Add-Ons"), atlassianProducts(
			"Atlassian Products"), commonModules("Common Modules"), confluencePlugins("Confluence Plugins");

	private String value;

	ProjectCategoryEnum(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
