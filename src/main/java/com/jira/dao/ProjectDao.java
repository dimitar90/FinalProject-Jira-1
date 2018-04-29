package com.jira.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.db.DBManager;
import com.jira.dto.ProjectDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.ProjectException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.IProjectDao;
import com.jira.model.Project;
import com.jira.model.User;

@Component
public class ProjectDao implements IProjectDao{
	private static final String MSG_SQL_INVALID_DATA = "Invalid credentials project dao";
	private static final String MSG_INVALID_PROJECT_NAME = "Invalid project name in roject dao";
	@Autowired
	private final DBManager manager;
	
	@Autowired
	private UserDao userDao;
	

	@Autowired
	private ProjectTypeDao projectTypeDao;
	
	@Autowired
	private ProjectCategoryDao projectCategoryDao;
	
	@Autowired
	private ProjectDao(DBManager dbManager) {
		this.manager = dbManager;
	}

	public Project getProject(String projectName, int projCategoryId, int projTypeId, int leadId) {
		return Project.createProject(projectName, projCategoryId, projTypeId, leadId);
	}

	@Override
	public void saveProject(Project p) throws SQLException, DatabaseException {
		try {
			String sql = "INSERT INTO projects (name, project_type_id, project_category_id, project_lead_id) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			this.manager.getConnection().setAutoCommit(false);
			ps.setString(1, p.getName());
			ps.setInt(2, p.getProjectTypeId());
			ps.setInt(3, p.getProjectCategoryId());
			ps.setInt(4, p.getProjectLeadId());
			ps.executeUpdate();

			ps.close();
			this.manager.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			this.manager.getConnection().rollback();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		} finally {
			this.manager.getConnection().setAutoCommit(true);
		}
	}

	@Override
	public List<Project> getAllProjects() throws DatabaseException {
		String sql = "SELECT id, name, project_type_id, project_category_id, project_lead_id FROM projects WHERE is_deleted = 0";
		try {
			PreparedStatement pr = this.manager.getConnection().prepareStatement(sql);
			ResultSet result = pr.executeQuery();
			List<Project> projects = new ArrayList<>();

			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int projectTypeId = result.getInt("project_type_id");
				int projectCategoryId = result.getInt("project_category_id");
				int projectLeadId = result.getInt("project_lead_id");

				projects.add(Project.getProject(id, name, projectTypeId, projectCategoryId, projectLeadId));
			}

			return projects;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
	}

	@Override
	public Project getById(int projectId) throws DatabaseException {
		String sql = "SELECT id, name, project_type_id, project_category_id, project_lead_id FROM projects WHERE is_deleted = ? AND id = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setBoolean(1, false);
			ps.setInt(2, projectId);

			ResultSet result = ps.executeQuery();
			Project project = null;

			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int projectTypeId = result.getInt("project_type_id");
				int projectCategoryId = result.getInt("project_category_id");
				int projectLeadId = result.getInt("project_lead_id");

				project = new Project(id, name, projectTypeId, projectCategoryId, projectLeadId);
			}

			return project;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
	}

	@Override
	public Collection<ProjectDto> getAllProjectDtos() throws Exception {
		String sql = "SELECT id, name, project_type_id, project_category_id, project_lead_id FROM projects WHERE is_deleted = 0";
		try {
			PreparedStatement pr = this.manager.getConnection().prepareStatement(sql);
			ResultSet result = pr.executeQuery();
			List<ProjectDto> projects = new ArrayList<>();

			while (result.next()) {
				int id = result.getInt("id");
				String projectName = result.getString("name");
				int projectTypeId = result.getInt("project_type_id");
				int projectCategoryId = result.getInt("project_category_id");
				int projectLeadId = result.getInt("project_lead_id");

				String projectType = projectTypeDao.getProjectTypeById(projectTypeId);
				String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);
				User user = userDao.getUserById(projectLeadId);
				projects.add(ProjectDto.getDto(id, projectName, projectType, projectCategory, user.getName()));
			}
			pr.close();
			return projects;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
	}

	public ProjectDto getProjectDtoByName(String projectName)
			throws DatabaseException, SQLException, ProjectException, UserDataException {
		String sql = "SELECT * FROM projects WHERE is_deleted = 0 AND name = ?";

		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setString(1, projectName);
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			int projectTypeId = result.getInt("project_type_id");
			int projectCategoryId = result.getInt("project_category_id");
			int projectLeadId = result.getInt("project_lead_id");

			String projectType = projectTypeDao.getProjectTypeById(projectTypeId);
			String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);
			User user = userDao.getUserById(projectLeadId);

			ProjectDto dto = ProjectDto.getDto(id, name, projectType, projectCategory, user.getName());
			ps.close();
			return dto;
		}

		throw new DatabaseException(MSG_INVALID_PROJECT_NAME);
	}

	public List<String> getProjectNamesWithPrefix(String prefix) throws DatabaseException {
		List<String> projectNames = new ArrayList<>();

		String sql = "SELECT name FROM projects WHERE name LIKE ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, prefix + "%");
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				projectNames.add(result.getString("name"));
			}
			return projectNames;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
		
	}

	@Override
	public boolean isExistById(int projectId) throws DatabaseException {
		String sql = "SELECT id FROM users WHERE id = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
		return false;
	} 
}
