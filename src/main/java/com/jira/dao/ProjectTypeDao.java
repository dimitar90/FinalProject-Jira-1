package com.jira.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.db.DBManager;
import com.jira.dto.ProjectTypeBusinessDto;
import com.jira.dto.ProjectTypeSoftwareDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.ProjectException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.IProjectTypeDao;
import com.jira.model.ProjectType;
import com.jira.model.ProjectTypeEnum;
import com.jira.model.User;

@Component
public class ProjectTypeDao implements IProjectTypeDao {
	private static final String SELECT_ALL_SOFTWARE_PROJECT_QUERY = "SELECT P.id, P.name, P.project_type_id, P.project_category_id, P.project_lead_id FROM projects P\r\n"
			+ "JOIN project_types PT\r\n" + "ON(P.project_type_id = PT.id)\r\n"
			+ "WHERE PT.type = 'Software' AND is_deleted = 0";
	private static final String SELECT_ALL_BUSINESS_PROJECT_QUERY = "SELECT P.id, P.name, P.project_type_id, P.project_category_id, P.project_lead_id FROM projects P\r\n"
			+ "JOIN project_types PT\r\n" + "ON(P.project_type_id = PT.id)\r\n"
			+ "WHERE PT.type = 'Business' AND is_deleted = 0";
	private static final String MSG_INVALID_ENUM_TYPE = "No enum type in project type";
	private static final String MSG_DATA_BASE_PROBLEM = "Invalid credentials project type dao";
	private static Map<Integer, ProjectType> projectTypes = new HashMap<>();

	@Autowired
	private  DBManager dbManager;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProjectCategoryDao projectCategoryDao;
	
	private ProjectTypeDao() throws SQLException, ProjectException {
		this.generateProjectTypesFromDb();
	}


	 @Override
	  public void generateProjectTypesFromDb() throws SQLException, ProjectException {
		String sql = "SELECT * FROM project_types";
		Statement stmt = this.dbManager.getConnection().createStatement();
		ResultSet result = stmt.executeQuery(sql);

		while (result.next()) {
			int id = result.getInt("id");
			String type = result.getString("type");

			projectTypes.put(id, ProjectType.getProjectType(id, this.getProjectTypeEnum(type)));
		}
		stmt.close();
	}
	 
	@Override
	public ProjectTypeEnum getProjectTypeEnum(String type) throws ProjectException {
		for (ProjectTypeEnum pte : ProjectTypeEnum.values()) {
			if (pte.getValue().equalsIgnoreCase(type)) {
				return pte;
			}
		}
		throw new ProjectException(MSG_INVALID_ENUM_TYPE);
	}
	@Override
	public List<ProjectType> getAllProjectTypes() {
		return new ArrayList<ProjectType>(projectTypes.values());
	}
	@Override
	public Collection<ProjectTypeSoftwareDto> getAllSoftwareProjects()
			throws DatabaseException, SQLException, ProjectException, UserDataException {
		String sql = SELECT_ALL_SOFTWARE_PROJECT_QUERY;
		List<ProjectTypeSoftwareDto> dtoList = new ArrayList<>();
		try {

			PreparedStatement pr = this.dbManager.getConnection().prepareStatement(sql);
			ResultSet result = pr.executeQuery();

			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int projectTypeId = result.getInt("project_type_id");
				int projectCategoryId = result.getInt("project_category_id");
				int projectLeadId = result.getInt("project_lead_id");

				String projectType = projectTypes.get(projectTypeId).getType().getValue();
				String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);
				User projectLead = userDao.getUserById(projectLeadId);

				dtoList.add(
						ProjectTypeSoftwareDto.getDto(id, name, projectType, projectCategory, projectLead.getName()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_DATA_BASE_PROBLEM);
		}
		return dtoList;
	}
	@Override
	public Collection<ProjectTypeBusinessDto> getAllBusinessProjects() throws SQLException, DatabaseException, UserDataException, ProjectException {
		String sql = SELECT_ALL_BUSINESS_PROJECT_QUERY;
		List<ProjectTypeBusinessDto> dtoList = new ArrayList<>();
		try {

			PreparedStatement pr = this.dbManager.getConnection().prepareStatement(sql);
			ResultSet result = pr.executeQuery();

			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int projectTypeId = result.getInt("project_type_id");
				int projectCategoryId = result.getInt("project_category_id");
				int projectLeadId = result.getInt("project_lead_id");

				String projectType = projectTypes.get(projectTypeId).getType().getValue();
				String projectCategory = projectCategoryDao.getProjectCategoryById(projectCategoryId);
				User projectLead = userDao.getUserById(projectLeadId);

				dtoList.add(
						ProjectTypeBusinessDto.getDto(id, name, projectType, projectCategory, projectLead.getName()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_DATA_BASE_PROBLEM);
		}
		return dtoList;
	}
	@Override
	public String getProjectTypeById(int id) {
		return projectTypes.get(id).getType().getValue();
	}
}
