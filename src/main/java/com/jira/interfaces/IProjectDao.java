package com.jira.interfaces;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.jira.dto.ProjectDto;
import com.jira.exception.DatabaseException;
import com.jira.model.Project;

@Component
public interface IProjectDao {
	public void saveProject(Project p) throws SQLException, DatabaseException;

	public Collection<Project> getAllProjects() throws Exception;

	public Project getById(int projectId) throws DatabaseException;

	public Collection<ProjectDto> getAllProjectDtos() throws Exception;
	
	public boolean isExistById(int projectId) throws Exception;
}
