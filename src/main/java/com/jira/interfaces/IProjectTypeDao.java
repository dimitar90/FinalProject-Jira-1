package com.jira.interfaces;

import java.util.Collection;
import java.util.List;


import com.jira.dto.ProjectTypeBusinessDto;
import com.jira.dto.ProjectTypeSoftwareDto;
import com.jira.model.ProjectType;
import com.jira.model.ProjectTypeEnum;

public interface IProjectTypeDao {
	void generateProjectTypesFromDb() throws Exception;

	public ProjectTypeEnum getProjectTypeEnum(String type) throws Exception;

	public Collection<ProjectType> getAllProjectTypes();

	public Collection<ProjectTypeSoftwareDto> getAllSoftwareProjects() throws Exception;

	public Collection<ProjectTypeBusinessDto> getAllBusinessProjects() throws Exception;
	
	public String getProjectTypeById(int id);
	
	public Collection<Integer> getAllIds();
}
