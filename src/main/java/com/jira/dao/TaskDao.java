package com.jira.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jira.db.DBManager;
import com.jira.dto.CommentViewDto;
import com.jira.dto.TaskBasicViewDto;
import com.jira.dto.TaskViewDetailsDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.TaskException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.IProjectDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.interfaces.ITaskStateDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Project;
import com.jira.model.Task;
import com.jira.model.TaskIssue;
import com.jira.model.TaskPriority;
import com.jira.model.TaskState;
import com.jira.model.TaskStateType;
import com.jira.model.User;

@Component
public class TaskDao implements ITaskDao{
	private static final String INVALID_DATA = "Invalid credentials!";

	private static final String DELETE_TASK_QUERY = "UPDATE tasks SET is_deleted = 1 WHERE id = ?";
	private static final String CHANGE_STATE_TASK_QUERY = "UPDATE tasks SET state_id = ? WHERE id = ?";
	private static final String SELECT_TASKS_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE is_deleted = 0;";
	private static final String SELECT_TASKS_BY_ID_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE is_deleted = 0 AND id = ?;";
	private static final String SELECT_IMAGE_QUERY = "SELECT image_url FROM task_images WHERE task_id = ?";
	private static final String INSERT_TASK_QUERY = "INSERT INTO tasks (summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_IMAGE_TASK_QUERY = "INSERT INTO task_images (image_url, task_id) VALUES (?, ?)";
	private static final String SELECT_COMMENTS_BY_TASK_ID = "SELECT description, date, user_id FROM comments WHERE task_id = ?;";
	private static final String SELECT_ALL_OPEN_TASKS_BY_USER_ID_QUERY = "SELECT t.id, t.summary, t.due_date, t.start_date, t.description, t.project_id, t.priority_id, t.state_id, t.issue_id, t.creator_id, t.assignee_id FROM tasks AS t INNER JOIN states AS s ON s.id = t.state_id WHERE s.type <> ? AND assignee_id = ? AND t.is_deleted = 0;";
	private static final String SELECT_TASKS_BY_PROJECT_ID_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE project_id = ? AND is_deleted = 0;";
	private static final String SELECT_TASKS_BETWEEN_TWO_DATES_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE (due_date BETWEEN ? AND ?) AND is_deleted = 0;";
	private static final String SELECT_TASKS_AFTER_DATE_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE due_date >= ? AND is_deleted = 0;";
	private static final String SELECT_TASKS_BEFORE_DATE_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE due_date <= ? AND is_deleted = 0;";
	
	private final DBManager dbManager;
	private final ITaskPriorityDao taskPriorityDao;
	private final ITaskStateDao taskStateDao;
	private final ITaskIssueDao taskIssueDao;
	private final IUserDao userDao;
	private final IProjectDao projectDao;
	
	@Autowired
	public TaskDao(DBManager dbManager, ITaskPriorityDao taskPriorityDao, ITaskStateDao taskStateDao,
			ITaskIssueDao taskIssueDao, IUserDao userDao, IProjectDao projectDao) {
		this.dbManager = dbManager;
		this.taskPriorityDao = taskPriorityDao;
		this.taskStateDao = taskStateDao;
		this.taskIssueDao = taskIssueDao;
		this.userDao = userDao;
		this.projectDao = projectDao;
	}
	
	public void saveTask(Task task) throws DatabaseException, SQLException {
		Connection conn = dbManager.getConnection();

		try {
			PreparedStatement pr = conn.prepareStatement(INSERT_TASK_QUERY, Statement.RETURN_GENERATED_KEYS);
			conn.setAutoCommit(false);

			pr.setString(1, task.getSummary());
			pr.setString(2, task.getDueDate().toString());
			pr.setString(3, task.getStartDate().toString());
			pr.setString(4, task.getDescription());
			pr.setInt(5, task.getProjectId());
			pr.setInt(6, task.getPriorityId());
			// set default state - 1 (To do)
			pr.setInt(7, 1);
			pr.setInt(8, task.getIssueId());
			pr.setInt(9, task.getCreatorId());
			pr.setInt(10, task.getAssigneeId());
			pr.executeUpdate();
			ResultSet rs = pr.getGeneratedKeys();

			if (rs.next()) {
				Integer taskId = rs.getInt(1);
				task.setId(taskId);
			}

			for (String imagePath : task.getImageUrls()) {
				pr = conn.prepareStatement(INSERT_IMAGE_TASK_QUERY);
				pr.setString(1, imagePath);
				pr.setInt(2, task.getId());
				pr.executeUpdate();
			}

			pr.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			throw new DatabaseException(INVALID_DATA);
		} finally {
			conn.setAutoCommit(true);
		}
	}

	public TaskViewDetailsDto getById(int taskId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection()
				.prepareStatement(SELECT_TASKS_BY_ID_QUERY)) {
			pr.setInt(1, taskId);
			ResultSet rs = pr.executeQuery();
			TaskViewDetailsDto viewTaskDto = null;

			while (rs.next()) {
				int id = rs.getInt("id");
				String summary = rs.getString("summary");
				LocalDate dueDate = rs.getDate("due_date").toLocalDate();
				LocalDate startDate = rs.getDate("start_date").toLocalDate();
				String description = rs.getString("description");
				int projectId = rs.getInt("project_id");
				int priorityId = rs.getInt("priority_id");
				int stateId = rs.getInt("state_id");
				int issueId = rs.getInt("issue_id");
				int creatorId = rs.getInt("creator_id");
				int assigneeId = rs.getInt("assignee_id");

				TaskPriority priority = taskPriorityDao.getById(priorityId);
				TaskState state = taskStateDao.getById(stateId);
				TaskIssue issue = taskIssueDao.getById(issueId);
				User creator = userDao.getUserById(creatorId);
				User assignee = userDao.getUserById(assigneeId);
				//Project project = projectDao.getById(projectId);
				Project project = new Project();
				
				viewTaskDto = new TaskViewDetailsDto(id, project, summary, dueDate, startDate, description, priority, state,
						issue, creator, assignee);
			}

			this.addCommentsToTask(viewTaskDto);
			this.addImageToTask(viewTaskDto);
			return viewTaskDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	private void addCommentsToTask(TaskViewDetailsDto viewTaskDto) throws TaskException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_COMMENTS_BY_TASK_ID)) {
			pr.setInt(1, viewTaskDto.getId());
			ResultSet rs = pr.executeQuery();
			List<CommentViewDto> comments = new ArrayList<>();

			while (rs.next()) {
				String description = rs.getString("description");
				LocalDateTime dateTime = rs.getTimestamp("date").toLocalDateTime();
				int userId = rs.getInt("user_id");

				User user = userDao.getUserById(userId);
				CommentViewDto comment = new CommentViewDto(description, dateTime, user);
				comments.add(comment);
			}

			viewTaskDto.setComments(comments);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TaskException();
		}

	}

	public List<TaskBasicViewDto> getAll() throws Exception {
		List<TaskBasicViewDto> tasks = new ArrayList<>();

		try {
			PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_TASKS_QUERY);
			ResultSet rs = pr.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String summary = rs.getString("summary");
				LocalDate dueDate = rs.getDate("due_date").toLocalDate();
				int projectId = rs.getInt("project_id");
				int priorityId = rs.getInt("priority_id");
				int stateId = rs.getInt("state_id");
				int assigneeId = rs.getInt("assignee_id");

				TaskPriority priority = taskPriorityDao.getById(priorityId);
				TaskState state = taskStateDao.getById(stateId);
				User assignee = userDao.getUserById(assigneeId);
				//Project project = projectDao.getById(projectId);
				Project project = new Project();
				
				TaskBasicViewDto viewTaskDto = new TaskBasicViewDto(id, project, summary, priority, assignee, dueDate, state);
				
				tasks.add(viewTaskDto);
			}

			return tasks;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	public void changeStateById(int taskId, int newStateId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(CHANGE_STATE_TASK_QUERY)) {
			pr.setInt(1, newStateId);
			pr.setInt(2, taskId);
			pr.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Database error!");
		}
	}

	public void deleteById(Integer taskId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(DELETE_TASK_QUERY)) {
			pr.setInt(1, taskId);
			pr.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	public List<TaskViewDetailsDto> getAllByProjectId(int pId) throws Exception {
		List<TaskViewDetailsDto> tasks = new ArrayList<>();

		try {
			PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_TASKS_BY_PROJECT_ID_QUERY);
			pr.setInt(1, pId);
			ResultSet rs = pr.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String summary = rs.getString("summary");
				LocalDate dueDate = rs.getDate("due_date").toLocalDate();
				LocalDate startDate = rs.getDate("start_date").toLocalDate();
				String description = rs.getString("description");
				int projectId = rs.getInt("project_id");
				int priorityId = rs.getInt("priority_id");
				int stateId = rs.getInt("state_id");
				int issueId = rs.getInt("issue_id");
				int creatorId = rs.getInt("creator_id");
				int assigneeId = rs.getInt("assignee_id");

				TaskPriority priority = taskPriorityDao.getById(priorityId);
				TaskState state = taskStateDao.getById(stateId);
				TaskIssue issue = taskIssueDao.getById(issueId);
				User creator = userDao.getUserById(creatorId);
				User assignee = userDao.getUserById(assigneeId);
				//Project project = projectDao.getById(projectId);
				Project project = new Project();

				TaskViewDetailsDto viewTaskDto = new TaskViewDetailsDto(id, project, summary, dueDate, startDate, description,
						priority, state, issue, creator, assignee);
				tasks.add(viewTaskDto);
			}

			return tasks;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}

	}

	public List<TaskViewDetailsDto> getTasksByIssueTypeIds(List<Integer> selectedIssueTypeIds)
			throws Exception {
		List<TaskViewDetailsDto> tasks = new ArrayList<>();
		String selectTasksByIssueTypeQuery = this.createQuery(selectedIssueTypeIds);

		try {
			PreparedStatement pr = dbManager.getConnection().prepareStatement(selectTasksByIssueTypeQuery);
			ResultSet rs = pr.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String summary = rs.getString("summary");
				LocalDate dueDate = rs.getDate("due_date").toLocalDate();
				LocalDate startDate = rs.getDate("start_date").toLocalDate();
				String description = rs.getString("description");
				int projectId = rs.getInt("project_id");
				int priorityId = rs.getInt("priority_id");
				int stateId = rs.getInt("state_id");
				int issueId = rs.getInt("issue_id");
				int creatorId = rs.getInt("creator_id");
				int assigneeId = rs.getInt("assignee_id");

				TaskPriority priority = taskPriorityDao.getById(priorityId);
				TaskState state = taskStateDao.getById(stateId);
				TaskIssue issue = taskIssueDao.getById(issueId);
				User creator = userDao.getUserById(creatorId);
				User assignee = userDao.getUserById(assigneeId);
				//Project project = projectDao.getById(projectId);
				Project project = new Project();

				TaskViewDetailsDto viewTaskDto = new TaskViewDetailsDto(id, project, summary, dueDate, startDate, description,
						priority, state, issue, creator, assignee);
				tasks.add(viewTaskDto);
			}

			this.addImageUrlsToTasks(tasks);

			return tasks;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}

	}

	private void addImageToTask(TaskViewDetailsDto task) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_IMAGE_QUERY)) {
			pr.setInt(1, task.getId());
			ResultSet rs = pr.executeQuery();
			List<String> imagePaths = new ArrayList<>();

			while (rs.next()) {
				String imagePath = rs.getString("image_url");

				String base64Encoded = this.convertFromLocalPathToBase64String(imagePath);
				// if image does not exist return empty string and continue;
				if (base64Encoded.isEmpty()) {
					continue;
				}

				imagePaths.add(base64Encoded);
			}

			task.setImageUrls(imagePaths);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	private String convertFromLocalPathToBase64String(String imagePath)
			throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File(imagePath);

		if (!file.exists()) {
			return "";
		}

		FileInputStream fis = new FileInputStream(file);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		byte[] bytes = bos.toByteArray();

		byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
		String base64Encoded = new String(encodeBase64, "UTF-8");

		return base64Encoded;
	}

	private void addImageUrlsToTasks(List<TaskViewDetailsDto> tasks) throws SQLException, DatabaseException {
		try {
			for (TaskViewDetailsDto taskViewDto : tasks) {
				this.addImageToTask(taskViewDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	private String createQuery(List<Integer> selectedIssueTypeIds) {
		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE is_deleted = 0 AND issue_id IN (");

		for (Integer id : selectedIssueTypeIds) {
			query.append(id).append(",");
		}

		query.deleteCharAt(query.length() - 1);
		query.append(");");

		return query.toString();
	}

	public List<TaskViewDetailsDto> getAllOpenTasksByUserId(int userId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_ALL_OPEN_TASKS_BY_USER_ID_QUERY)) {
			pr.setString(1, TaskStateType.done.getValue());
			pr.setInt(2, userId);
			ResultSet rs = pr.executeQuery();

			List<TaskViewDetailsDto> tasks = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String summary = rs.getString("summary");
				LocalDate dueDate = rs.getDate("due_date").toLocalDate();
				LocalDate startDate = rs.getDate("start_date").toLocalDate();
				String description = rs.getString("description");
				int projectId = rs.getInt("project_id");
				int priorityId = rs.getInt("priority_id");
				int stateId = rs.getInt("state_id");
				int issueId = rs.getInt("issue_id");
				int creatorId = rs.getInt("creator_id");
				int assigneeId = rs.getInt("assignee_id");

				TaskPriority priority = taskPriorityDao.getById(priorityId);
				TaskState state = taskStateDao.getById(stateId);
				TaskIssue issue = taskIssueDao.getById(issueId);
				User creator = userDao.getUserById(creatorId);
				User assignee = userDao.getUserById(assigneeId);
				//Project project = projectDao.getById(projectId);
				Project project = new Project();
				
				TaskViewDetailsDto viewTaskDto = new TaskViewDetailsDto(id, project, summary, dueDate, startDate, description,
						priority, state, issue, creator, assignee);
				tasks.add(viewTaskDto);
			}

			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	public List<TaskViewDetailsDto> getTasksBetweenTwoDates(String firstDate, String secondDate)
			throws DatabaseException {
		String sql = this.getSqlByDates(firstDate, secondDate);
		
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(sql)){
			this.setParametersByDates(firstDate, secondDate, pr);
			
			ResultSet rs = pr.executeQuery();
			List<TaskViewDetailsDto> tasks = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String summary = rs.getString("summary");
				LocalDate dueDate = rs.getDate("due_date").toLocalDate();
				LocalDate startDate = rs.getDate("start_date").toLocalDate();
				String description = rs.getString("description");
				int projectId = rs.getInt("project_id");
				int priorityId = rs.getInt("priority_id");
				int stateId = rs.getInt("state_id");
				int issueId = rs.getInt("issue_id");
				int creatorId = rs.getInt("creator_id");
				int assigneeId = rs.getInt("assignee_id");

				TaskPriority priority = taskPriorityDao.getById(priorityId);
				TaskState state = taskStateDao.getById(stateId);
				TaskIssue issue = taskIssueDao.getById(issueId);
				User creator = userDao.getUserById(creatorId);
				User assignee = userDao.getUserById(assigneeId);
				//Project project = projectDao.getById(projectId);
				Project project = new Project();
				
				TaskViewDetailsDto viewTaskDto = new TaskViewDetailsDto(id, project, summary, dueDate, startDate, description,
						priority, state, issue, creator, assignee);
				tasks.add(viewTaskDto);
			}

			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}

	}


	private void setParametersByDates(String firstDate, String secondDate, PreparedStatement pr) throws SQLException {
		if (!firstDate.isEmpty() && !secondDate.isEmpty()) {
			pr.setDate(1, java.sql.Date.valueOf(firstDate));
			pr.setDate(2, java.sql.Date.valueOf(secondDate));
			return;
		}
		
		if (firstDate.isEmpty()) {
			pr.setDate(1, java.sql.Date.valueOf(secondDate));
			return;
		}
		
		if (secondDate.isEmpty()) {
			pr.setDate(1, java.sql.Date.valueOf(firstDate));
			return;
		}	
	}

	private String getSqlByDates(String firstDate, String secondDate) {
		if (!firstDate.isEmpty() && !secondDate.isEmpty()) {
			return SELECT_TASKS_BETWEEN_TWO_DATES_QUERY;
		}
		
		if (firstDate.isEmpty()) {
			return SELECT_TASKS_BEFORE_DATE_QUERY;
		}
		
		return SELECT_TASKS_AFTER_DATE_QUERY;
	}
}
