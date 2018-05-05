package com.jira.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.dto.ProjectDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.ProjectException;
import com.jira.exception.UserDataException;
import com.jira.model.Project;

@Component
public interface IProjectDao {
	public void saveProject(Project p) throws SQLException, DatabaseException;

	public Collection<Project> getAllProjects() throws Exception;

	public Project getById(int projectId) throws DatabaseException;

	public Collection<ProjectDto> getAllProjectDtos() throws Exception;

	public boolean isExistById(int projectId) throws Exception;

	public void isValidProjectName(String projectName) throws Exception;

	public ProjectDto getProjectDtoById(int id) throws Exception;

	public List<ProjectDto> getAllBelongingToUser(int id) throws Exception;

	public void deleteProjectById(int id) throws Exception;
	
	public int getLeadByProjectId(int projectId) throws Exception;
	
	public int getProjectsCount(int id) throws Exception;

	public List<ProjectDto> getProjectPerPageAndUserId(int userId,int numPage,int projectOnPage) throws Exception;

	public int getCount() throws Exception;

	List<ProjectDto> getProjectPerPage(int numPage, int projectOnPage) throws Exception;
	
	List<ProjectDto> getProjectsDtoFromResult(ResultSet result) throws Exception;
	
	public int getCountOfUserProjects(int userId) throws Exception; 
	
}
