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
import com.jira.exception.TaskIssueException;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.model.TaskPriority;
import com.jira.model.TaskPriorityType;

@Component
public class TaskPriorityDao implements ITaskPriorityDao {
	private static final String SELECT_TASK_PRIORITIES_QUERY = "SELECT id, type FROM priorities";

	private static Map<Integer, TaskPriority> taskPriorities = new HashMap<>();

	private final DBManager dbManager;

	@Autowired
	public TaskPriorityDao(DBManager dbManager) {
		this.dbManager = dbManager;
		
		try {
			this.fillTaskIssuesFromDb();
		} catch (TaskIssueException e) {
			e.printStackTrace();
		}
	}
	
	public List<TaskPriority> getAll() {
		return new ArrayList<TaskPriority>(taskPriorities.values());
	}

	private void fillTaskIssuesFromDb() throws TaskIssueException {
		try {
			Statement st = dbManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery(SELECT_TASK_PRIORITIES_QUERY);
			while (rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("type");
				
				taskPriorities.put(id, new TaskPriority(id, this.getTaskIssueTypeByString(type)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TaskIssueException();
		}
	}
	
	private TaskPriorityType getTaskIssueTypeByString(String type) {
		for (TaskPriorityType t : TaskPriorityType.values()) {
			if (t.getValue().equals(type)) {
				return t;
			}
		}
		
		return null;
	}

	public TaskPriority getById(int priorityId) {
		if (!taskPriorities.containsKey(priorityId)) {
			return null;
		}
		
		return taskPriorities.get(priorityId);
	}

	@Override
	public boolean isExistById(Integer priorityId) {
		return taskPriorities.containsKey(priorityId);
	}
}
