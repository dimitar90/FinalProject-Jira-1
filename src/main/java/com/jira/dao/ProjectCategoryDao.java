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
import com.jira.exception.DatabaseException;
import com.jira.exception.ProjectException;
import com.jira.interfaces.IProjectCategoryDao;
import com.jira.model.ProjectCategory;
import com.jira.model.ProjectCategoryEnum;

@Component
public class ProjectCategoryDao implements IProjectCategoryDao {
	private static final String MSG_NO_AVAILABLE_DATA = "No such enum data";
	private static final String CATEGORY_QUERY = "SELECT pc.name AS category_name, COUNT(p.id) AS count FROM projects AS p \r\n"
			+ "INNER JOIN project_categories AS pc ON pc.id = p.project_category_id GROUP BY p.project_category_id";
	private int id;
	private static Map<Integer, ProjectCategory> projectCategories = new HashMap<>();
	private static Map<String, Integer> categoriesForChart = new HashMap<>();

	@Autowired
	private final DBManager dbManager;

	@Autowired
	private ProjectCategoryDao(DBManager manager) throws SQLException, ProjectException {
		this.dbManager = manager;
		this.generateProjectCategoriesFromDb();
		this.generateCategoriesForChart();
	}

	public void generateCategoriesForChart() {
		for (ProjectCategory c : projectCategories.values()) {
			categoriesForChart.put(c.getCategory().getValue(), 0);
		}
	}

	@Override
	public void generateProjectCategoriesFromDb() throws SQLException, ProjectException {
		String sql = "SELECT id, name FROM project_categories";
		Statement st = this.dbManager.getConnection().createStatement();

		ResultSet result = st.executeQuery(sql);

		while (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");

			ProjectCategory pc = ProjectCategory.getProjectCategory(id, this.getProjectCategoryEnum(name));
			projectCategories.put(id, pc);
		}
		st.close();
	}

	@Override
	public ProjectCategoryEnum getProjectCategoryEnum(String name) throws ProjectException {
		for (ProjectCategoryEnum pce : ProjectCategoryEnum.values()) {
			if (pce.getValue().equalsIgnoreCase(name)) {
				return pce;
			}
		}
		throw new ProjectException(MSG_NO_AVAILABLE_DATA);
	}

	@Override
	public List<ProjectCategory> getAllCategories() {
		return new ArrayList<ProjectCategory>(projectCategories.values());
	}

	@Override
	public String getProjectCategoryById(int projectCategoryId) {
		return projectCategories.get(projectCategoryId).getCategory().getValue();
	}

	@Override
	public List<Integer> getAllIds() {
		return new ArrayList<>(this.projectCategories.keySet());
	}

	public Map<String, Integer> getCategoriesForChart() throws DatabaseException {
		Map<String, Integer> copyCategories = new HashMap<>();

		for (String c : categoriesForChart.keySet()) {
			categoriesForChart.put(c, 0);
		}

		return this.calculateCategories();
	}

	private Map<String, Integer> calculateCategories() throws DatabaseException {
		String sql = CATEGORY_QUERY;
		try {
			Statement stmt = this.dbManager.getConnection().createStatement();
			stmt.executeQuery(sql);
			ResultSet result = stmt.getResultSet();

			while (result.next()) {
				String category = result.getString(1);
				int count = result.getInt(2);

				categoriesForChart.put(category, count);

			}
			stmt.close();
			return categoriesForChart;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_NO_AVAILABLE_DATA);
		}
	}
}
