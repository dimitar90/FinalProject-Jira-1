package com.jira.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.db.DBManager;
import com.jira.exception.TaskIssueException;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.model.TaskIssue;
import com.jira.model.TaskIssueType;

@Component
public class TaskIssueDao implements ITaskIssueDao{
	private static final String SELECT_TASK_ISSUES_QUERY = "SELECT id, type FROM issues";
	
	private static Map<Integer, TaskIssue> taskIssues = new HashMap<>();

	private final DBManager dbManager;

	@Autowired
	public TaskIssueDao(DBManager dbManager) {
		this.dbManager = dbManager;
		
		try {
			this.fillTaskIssuesFromDb();
		} catch (TaskIssueException e) {
			e.printStackTrace();
		}
	}

	private void fillTaskIssuesFromDb() throws TaskIssueException {
		try {
			Statement st = dbManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery(SELECT_TASK_ISSUES_QUERY);
			while (rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("type");
				
				taskIssues.put(id, new TaskIssue(id, this.getTaskIssueTypeByString(type)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TaskIssueException();
		}
	}
	
	private TaskIssueType getTaskIssueTypeByString(String type) {
		for (TaskIssueType t : TaskIssueType.values()) {
			if (t.getValue().equals(type)) {
				return t;
			}
		}
		
		return null;
	}

	public List<TaskIssue> getAll() {
		return new ArrayList<>(taskIssues.values());
	}

	public TaskIssue getById(int issueId) {
		if (!taskIssues.containsKey(issueId)) {
			return null;
		}
		
		return taskIssues.get(issueId);
	}

	@Override
	public boolean isExistById(Integer issueTypeId) {
		return taskIssues.containsKey(issueTypeId);
	}

	@Override
	public Set<Integer> getAllIds() {
		return new HashSet<>(taskIssues.keySet());
	}
}
