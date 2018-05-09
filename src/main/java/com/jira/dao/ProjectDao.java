package com.jira.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class ProjectDao implements IProjectDao {
	private static final String MSG_SQL_INVALID_DATA = "Invalid credentials project dao";
	private static final String MSG_INVALID_PROJECT_NAME = "Invalid project name in project dao";
	private static final String MSG_INVALID_ID = "No project with such id";
	private static final String MSG_INVALID_PROJECT_ID = "Invalid project id in project dao";
	private static final String SELECT_PROJECTS_BY_PAGE = "SELECT id, name, project_type_id, project_category_id, project_lead_id FROM projects WHERE is_deleted = 0 LIMIT ?, ?;";
	private static final int MIN_LENGTH_PROJECT_NAME = 2;
	private static final String SELECT_PROJECTS_BY_PAGE_AND_USER_ID = "SELECT id, name, project_type_id, project_category_id, project_lead_id FROM projects WHERE is_deleted = 0 AND project_lead_id = ? LIMIT ?, ?";

	@Autowired
	private final DBManager manager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProjectTypeDao projectTypeDao;

	@Autowired
	private ProjectCategoryDao projectCategoryDao;

	@Autowired
	public ProjectDao(DBManager dbManager) {
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
	public List<ProjectDto> getAllProjectDtos() throws Exception {
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
				projects.add(
						ProjectDto.getDto(id, projectName, projectType, projectCategory, user.getName(), user.getId()));
			}
			pr.close();
			return projects;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
	}
	@Override
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

			ProjectDto dto = ProjectDto.getDto(id, name, projectType, projectCategory, user.getName(), projectLeadId);
			
			ps.close();
			
			return dto;
		}
		return null;
	}
	
	@Override
	public List<String> getLimitedProjectNamesWithPrefix(String prefix) throws DatabaseException {
		List<String> projectNames = new ArrayList<>();

		String sql = "SELECT name FROM projects WHERE is_deleted = 0 AND name LIKE ? LIMIT 3";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, prefix + "%");
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				projectNames.add(result.getString("name"));
			}
			return projectNames;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
	}
	
	@Override
	public List<Integer> getCategoriesId(String[] categories) {
		List<Integer> categoryIds = new ArrayList<>();
		if (categories == null) {
			categoryIds.addAll(this.projectCategoryDao.getAllIds());
		}else {
			for (String id : categories) {
				categoryIds.add(Integer.parseInt(id));
			}
		}
		return categoryIds;
	}
	
	@Override
	public boolean isExistById(int projectId) throws DatabaseException {
		String sql = "SELECT id FROM projects WHERE id = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, projectId);
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

	@Override
	public void isValidProjectName(String projectName) throws ProjectException {
		if (projectName.isEmpty() && projectName.length() < MIN_LENGTH_PROJECT_NAME) {
			throw new ProjectException(MSG_INVALID_PROJECT_NAME);
		}

	}

	@Override
	public ProjectDto getProjectDtoById(int id) throws DatabaseException, UserDataException {
		String sql = "SELECT id, name, project_type_id, project_category_id, project_lead_id FROM projects WHERE id = ? AND is_deleted = 0";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();

			result.next();

			int rsId = result.getInt("id");
			String name = result.getString("name");
			int projectTypeId = result.getInt("project_type_id");
			int projectCategoryId = result.getInt("project_category_id");
			int projectLeadId = result.getInt("project_lead_id");

			String projectType = projectTypeDao.getProjectTypeById(projectTypeId);
			String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);
			User user = userDao.getUserById(projectLeadId);

			ProjectDto dto = ProjectDto.getDto(rsId, name, projectType, projectCategory, user.getName(), projectLeadId);
			ps.close();
			return dto;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_INVALID_ID);
		}

	}

	@Override
	public List<ProjectDto> getAllBelongingToUser(int id) throws UserDataException, DatabaseException {
		String sql = "SELECT id, name, project_type_id, project_category_id FROM projects WHERE project_lead_id = ? AND is_deleted = 0";
		List<ProjectDto> listDto = new ArrayList<>();
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				int projectId = result.getInt("id");
				String projectName = result.getString("name");
				int projectTypeId = result.getInt("project_type_id");
				int projectCategoryId = result.getInt("project_category_id");

				String projectType = projectTypeDao.getProjectTypeById(projectTypeId);
				String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);

				User user = userDao.getUserById(id);

				ProjectDto dto = ProjectDto.getDto(projectId, projectName, projectType, projectCategory, user.getName(),
						id);
				listDto.add(dto);
			}
			ps.close();

			return listDto;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_INVALID_ID);
		}
	}
	
	@Override
	public void deleteProjectById(int id) throws DatabaseException, SQLException {
		String sql = "UPDATE projects SET is_deleted = 1 WHERE id =  ? AND is_deleted = 0";
		String deleteTasksOnThisProjectSql = "UPDATE tasks SET is_deleted = 1 WHERE project_id = ?";
		try {
			this.manager.getConnection().setAutoCommit(false);
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		    ps = this.manager.getConnection().prepareStatement(deleteTasksOnThisProjectSql);
		    ps.setInt(1, id);
			ps.executeUpdate();
			
			ps.close();
			this.manager.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			this.manager.getConnection().rollback();
			throw new DatabaseException(MSG_INVALID_PROJECT_ID);
		} finally {
			this.manager.getConnection().setAutoCommit(true);
		}
	}

	@Override
	public int getLeadByProjectId(int id) throws DatabaseException {
		String sql = "SELECT project_lead_id FROM projects WHERE id = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();

			if (result.next()) {
				return result.getInt("project_lead_id");
			}

			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("");
		}

	}

	@Override
	public int getCount() throws Exception {
		String sql = "SELECT COUNT(*) FROM projects WHERE is_deleted = 0";
		try {
			Statement stmt = this.manager.getConnection().createStatement();

			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt(1);
			stmt.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
	}

	@Override
	public List<ProjectDto> getProjectPerPage(int numPage, int projectOnPage) throws Exception {
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(SELECT_PROJECTS_BY_PAGE);
			ps.setInt(1, (numPage * projectOnPage));
			ps.setInt(2, projectOnPage);
			ResultSet result = ps.executeQuery();
			List<ProjectDto> projects = this.getProjectsDtoFromResult(result);

			ps.close();
			return projects;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA, e);
		}
	}

	@Override
	public List<ProjectDto> getProjectsDtoFromResult(ResultSet result) throws SQLException, UserDataException {
		List<ProjectDto> resultList = new ArrayList<>();

		while (result.next()) {
			int rsId = result.getInt("id");
			String name = result.getString("name");
			int projectTypeId = result.getInt("project_type_id");
			int projectCategoryId = result.getInt("project_category_id");
			int projectLeadId = result.getInt("project_lead_id");

			String projectType = projectTypeDao.getProjectTypeById(projectTypeId);
			String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);
			User user = userDao.getUserById(projectLeadId);

			ProjectDto dto = ProjectDto.getDto(rsId, name, projectType, projectCategory, user.getName(), projectLeadId);

			resultList.add(dto);
		}
		
		return resultList;
	}

	@Override
	public int getProjectsCount(int id) throws SQLException {
		String sql = " SELECT COUNT(*) FROM projects WHERE project_lead_id = ? AND is_deleted = 0";

		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			return result.getInt(1);
		}
		ps.close();
		return 0;
	}
	
	@Override
	public int getCountOfUserProjects(int userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM projects WHERE is_deleted = 0 AND project_lead_id = ?";
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet result = ps.executeQuery();
		int projectCount = 0;
		if (result.next()) {
			projectCount = result.getInt(1);
			
		}
		ps.close();
		return projectCount;
	}
	
	@Override
	public List<ProjectDto> getProjectPerPageAndUserId(int userId, int numPage, int projectOnPage)
			throws UserDataException, DatabaseException {
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(SELECT_PROJECTS_BY_PAGE_AND_USER_ID);
			ps.setInt(1, userId);
			ps.setInt(2, (numPage * projectOnPage));
			ps.setInt(3, projectOnPage);
			ResultSet result = ps.executeQuery();
			List<ProjectDto> projects = this.getProjectsDtoFromResult(result);

			ps.close();
			return projects;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA, e);
		}
	}
	
	@Override
	public List<ProjectDto> getProjectsFilteredByCategories(List<Integer> categoriesId) throws SQLException, UserDataException {
		List<String> queryParts = new LinkedList<>();

		this.createQueryBasedOnCategoryIds(categoriesId,queryParts);
		
		StringBuilder finalQuery = new StringBuilder(
				"SELECT id,name,project_type_id,project_category_id,project_lead_id FROM projects WHERE ");
				finalQuery.append(queryParts.get(0)).append(" AND  is_deleted = 0");
				try (PreparedStatement ps = this.manager.getConnection().prepareStatement(finalQuery.toString().trim())) {
					this.setParametersToExecuteTheQueryCategories(categoriesId,ps);
					
					ResultSet result = ps.executeQuery();
					
					List<ProjectDto> projects = this.extractResult(result);
					
					return projects;
				}
	}

	private List<ProjectDto> extractResult(ResultSet result) throws UserDataException, SQLException {
		List<ProjectDto> projects = new ArrayList<>();
		while(result.next()) {
			
			int id = result.getInt("id");
			String projectName = result.getString("name");
			int projectTypeId = result.getInt("project_type_id");
			int projectCategoryId = result.getInt("project_category_id");
			int projectLeadId = result.getInt("project_lead_id");

			String projectType = projectTypeDao.getProjectTypeById(projectTypeId);
			String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);
			User user = userDao.getUserById(projectLeadId);
			
			
			ProjectDto dto = ProjectDto.getDto(id, projectName, projectType, projectCategory, user.getName(), projectLeadId);
			projects.add(dto);
		}
		
		return projects;
	}

	private void setParametersToExecuteTheQueryCategories(List<Integer> categoriesId, PreparedStatement ps) throws SQLException {
		for (int index = 1; index <= categoriesId.size(); index++) {
			ps.setInt(index, categoriesId.get(index - 1));
		}
	}

	private void createQueryBasedOnCategoryIds(List<Integer> categoriesId,List<String> queryParts) {
		StringBuilder currentQuery = new StringBuilder();
		currentQuery.append("project_category_id IN (");

		for (int index = 0; index < categoriesId.size(); index++) {
			currentQuery.append("?").append(",");
		}

		currentQuery.deleteCharAt(currentQuery.length() - 1);
		currentQuery.append(")");

		queryParts.add(currentQuery.toString().trim());
	}
	
	@Override
	public boolean checkProjectName(String projectName) {
		return ((projectName.isEmpty()) || (!projectName.matches("^[_A-z0-9]*((-|\\s)*[_A-z0-9])*$")) || (projectName.length() < MIN_LENGTH_PROJECT_NAME));
	}


}
