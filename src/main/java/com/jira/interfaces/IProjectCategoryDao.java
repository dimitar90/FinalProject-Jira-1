package com.jira.interfaces;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.jira.model.ProjectCategory;
import com.jira.model.ProjectCategoryEnum;

@Component
public interface IProjectCategoryDao {
	public void generateProjectCategoriesFromDb() throws Exception;

	public ProjectCategoryEnum getProjectCategoryEnum(String name) throws Exception;

	public Collection<ProjectCategory> getAllCategories();

	public String getProjectCategoryById(int projectCategoryId);
	public Collection<Integer> getAllIds();
}
