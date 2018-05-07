package com.jira.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.exception.ProjectException;
import com.jira.model.ProjectCategory;
import com.jira.model.ProjectCategoryEnum;

@Component
public interface IProjectCategoryDao {
	public void generateProjectCategoriesFromDb() throws Exception;

	public ProjectCategoryEnum getProjectCategoryEnum(String name) throws Exception;

	public List<ProjectCategory> getAllCategories();

	public String getProjectCategoryById(int projectCategoryId);
	public List<Integer> getAllIds();
}
