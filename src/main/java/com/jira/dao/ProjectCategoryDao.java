package com.jira.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.db.DBManager;
import com.jira.exception.ProjectException;
import com.jira.interfaces.IProjectCategoryDao;
import com.jira.model.ProjectCategory;
import com.jira.model.ProjectCategoryEnum;

@Component
public class ProjectCategoryDao implements IProjectCategoryDao{
	private static final String MSG_NO_AVAILABLE_DATA = "No such enum data";
	private int id;
	private static Map<Integer,ProjectCategory> projectCategories = new HashMap<>();
	
	@Autowired
	private  DBManager dbManager;
	
	
	private ProjectCategoryDao() throws SQLException, ProjectException {
		this.generateProjectCategoriesFromDb();
	}


	
	public void generateProjectCategoriesFromDb() throws SQLException, ProjectException {	
		String sql = "SELECT id, name FROM project_categories";
		Statement st = this.dbManager.getConnection().createStatement();
		
		ResultSet result = st.executeQuery(sql);
		
		while(result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			
			ProjectCategory pc = ProjectCategory.getProjectCategory(id,this.getProjectCategoryEnum(name)); 
			projectCategories.put(id, pc);
		}
		st.close();
	}

	public ProjectCategoryEnum getProjectCategoryEnum(String name) throws ProjectException {
		for (ProjectCategoryEnum pce : ProjectCategoryEnum.values()) {
			if (pce.getValue().equalsIgnoreCase(name)) {
				return pce;
			}
		}
		throw new ProjectException(MSG_NO_AVAILABLE_DATA);
	}


	public List<ProjectCategory> getAllCategories(){
		return new ArrayList<ProjectCategory>(projectCategories.values());
	}

	public String getProjectCategoryById(int projectCategoryId) {
		return projectCategories.get(projectCategoryId).getCategory().getValue();
	}
}
