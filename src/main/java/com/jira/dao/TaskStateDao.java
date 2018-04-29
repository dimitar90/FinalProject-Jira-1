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
import com.jira.interfaces.ITaskStateDao;
import com.jira.model.TaskState;
import com.jira.model.TaskStateType;

@Component
public class TaskStateDao implements ITaskStateDao {
	private static final String SELECT_TASK_STATES_QUERY = "SELECT id, type FROM states";
	private static Map<Integer, TaskState> taskStates = new HashMap<>();

	private final DBManager dbManager;

	@Autowired
	public TaskStateDao(DBManager dbManager) {
		this.dbManager = dbManager;
		
		try {
			this.fillTaskStatesFromDb();
		} catch (TaskIssueException e) {
			e.printStackTrace();
		}
	}

	private void fillTaskStatesFromDb() throws TaskIssueException {
		try {
			Statement st = dbManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery(SELECT_TASK_STATES_QUERY);
			while (rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("type");
				
				taskStates.put(id, new TaskState(id, this.getTaskStateTypeByString(type)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TaskIssueException();
		}
	}
	
	private TaskStateType getTaskStateTypeByString(String type) {
		for (TaskStateType t : TaskStateType.values()) {
			if (t.getValue().equals(type)) {
				return t;
			}
		}
		
		return null;
	}

	public TaskState getById(int stateId) {
		if (!taskStates.containsKey(stateId)) {
			return null;
		}
		
		return taskStates.get(stateId);
	}

	public List<TaskState> getAll() {
		return new ArrayList<TaskState>(taskStates.values());
	}

	@Override
	public boolean isExistById(Integer stateId) {
		if (stateId == null) {
			return false;
		}
		
		return taskStates.containsKey(stateId);
	}
}
