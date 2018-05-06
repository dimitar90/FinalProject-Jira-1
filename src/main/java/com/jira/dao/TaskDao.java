package com.jira.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.jira.db.DBManager;
import com.jira.dto.CommentViewDto;
import com.jira.dto.TaskBasicViewDto;
import com.jira.dto.TaskViewDetailsDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.TaskException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.ICommentTaskDao;
import com.jira.interfaces.IProjectDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.interfaces.ITaskStateDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Project;
import com.jira.model.Task;
import com.jira.model.TaskIssue;
import com.jira.model.TaskIssueType;
import com.jira.model.TaskPriority;
import com.jira.model.TaskState;
import com.jira.model.TaskStateType;
import com.jira.model.User;

@Component
public class TaskDao implements ITaskDao {
	private static final String PATH_IMAGE_PREFFIX = "D:\\images\\tasks";
	private static final String INVALID_DATA = "Invalid credentials!";

	private static final String BETWEEN_TWO_DUE_DATE_PART = "(due_date BETWEEN ? AND ?)";
	private static final String BEFORE_DUE_DATE_QUERY_PART = "due_date <= ?";
	private static final String AFTER_DUE_DATE_QUERY_PART = "due_date >= ?";
	
	private static final String BETWEEN_TWO_START_DATE_PART = "WHERE (t.start_date BETWEEN ? AND ?) AND t.is_deleted = 0 ";
	private static final String BEFORE_START_DATE_QUERY_PART = "WHERE t.start_date <= ? AND t.is_deleted = 0 ";
	private static final String AFTER_START_DATE_QUERY_PART = " WHERE t.start_date >= ? AND t.is_deleted = 0 ";

	private static final String DELETE_TASK_QUERY = "UPDATE tasks SET is_deleted = 1 WHERE id = ?";
	private static final String CHANGE_STATE_TASK_QUERY = "UPDATE tasks SET state_id = ? WHERE id = ?";
	private static final String SELECT_TASKS_BY_ID_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE is_deleted = 0 AND id = ?;";
	private static final String SELECT_IMAGE_QUERY = "SELECT image_url FROM task_images WHERE task_id = ?";
	private static final String INSERT_TASK_QUERY = "INSERT INTO tasks (summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_IMAGE_TASK_QUERY = "INSERT INTO task_images (image_url, task_id) VALUES (?, ?)";
	private static final String SELECT_ALL_OPEN_TASKS_BY_USER_ID_QUERY = "SELECT t.id, t.summary, t.due_date, t.start_date, t.description, t.project_id, t.priority_id, t.state_id, t.issue_id, t.creator_id, t.assignee_id FROM tasks AS t INNER JOIN states AS s ON s.id = t.state_id WHERE s.type <> ? AND assignee_id = ? AND t.is_deleted = 0 ORDER BY due_date;";
	private static final String SELECT_TASKS_BY_PROJECT_ID_QUERY = "SELECT id, summary, due_date, start_date, description, project_id, priority_id, state_id, issue_id, creator_id, assignee_id FROM tasks WHERE project_id = ? AND is_deleted = 0 ORDER BY due_date;";
	private static final String GET_COUNT_OF_TASKS = "SELECT COUNT(*) FROM tasks WHERE is_deleted = 0";
	private static final String SELECT_TASK_BY_PAGE = "SELECT id, summary, due_date, priority_id, project_id, state_id, assignee_id, creator_id FROM tasks WHERE is_deleted = 0 ORDER BY id DESC LIMIT ?, ? ;";
	private static final String IS_EXIST_TASK_QUERY = "SELECT COUNT(*) FROM tasks WHERE id = ? AND is_deleted = 0";
	private static final String SELECT_TASKS_BY_PART_OF_NAME_QEURY = "SELECT * FROM tasks WHERE (summary LIKE ?) AND is_deleted = 0 ORDER BY id DESC;";
	private static final String SELECT_COUNT_FOR_ALL_ISSUE_TYPES_QUERY = "SELECT i.type AS type, COUNT(t.id) AS count FROM tasks AS t INNER JOIN issues AS i ON i.id = t.issue_id INSERT_WHERE_CLAUSE GROUP BY t.issue_id;";
	private static final String SELECT_COUNT_FOR_ALL_STATE_TYPES_QUERY = "SELECT s.type AS type, COUNT(t.id) AS count FROM tasks AS t INNER JOIN states AS s ON s.id = t.state_id INSERT_WHERE_CLAUSE GROUP BY t.state_id;";
	private static final String GET_FIRST_TASK_START_DATE_QUERY = "SELECT MIN(start_date) AS first_date FROM tasks WHERE is_deleted = 0;";
	private static final String GET_LAST_TASK_START_DATE_QUERY = "SELECT MAX(start_date) AS last_date FROM tasks WHERE is_deleted = 0;";
	
	private final DBManager dbManager;
	private final ITaskPriorityDao taskPriorityDao;
	private final ITaskStateDao taskStateDao;
	private final ITaskIssueDao taskIssueDao;
	private final IUserDao userDao;
	private final IProjectDao projectDao;
	private final ICommentTaskDao commentDao;

	@Autowired
	public TaskDao(DBManager dbManager, ITaskPriorityDao taskPriorityDao, ITaskStateDao taskStateDao,
			ITaskIssueDao taskIssueDao, IUserDao userDao, IProjectDao projectDao, ICommentTaskDao commentDao) {
		this.dbManager = dbManager;
		this.taskPriorityDao = taskPriorityDao;
		this.taskStateDao = taskStateDao;
		this.taskIssueDao = taskIssueDao;
		this.userDao = userDao;
		this.projectDao = projectDao;
		this.commentDao = commentDao;
	}

	@Override
	public void saveTask(Task task) throws DatabaseException, SQLException {
		Connection conn = dbManager.getConnection();

		try {
			conn.setAutoCommit(false);
			PreparedStatement pr = conn.prepareStatement(INSERT_TASK_QUERY, Statement.RETURN_GENERATED_KEYS);
			
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

	@Override
	public TaskViewDetailsDto getById(int taskId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_TASKS_BY_ID_QUERY)) {
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
				Project project = projectDao.getById(projectId);

				viewTaskDto = new TaskViewDetailsDto(id, project, summary, dueDate, startDate, description, priority,
						state, issue, creator, assignee);
			}

			if (viewTaskDto != null) {
				this.addCommentsToTask(viewTaskDto);
				this.addImageToTask(viewTaskDto);
			}

			return viewTaskDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
	public List<TaskBasicViewDto> getByCurrentPageNumberAndTaskPerPage(Integer pageNumber, int tasksCountOnPage)
			throws Exception {
		try {
			PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_TASK_BY_PAGE);
			pr.setInt(1, (pageNumber * tasksCountOnPage));
			pr.setInt(2, tasksCountOnPage);
			ResultSet rs = pr.executeQuery();
			List<TaskBasicViewDto> tasks = this.fillListWithTaskBasicViewDtoFromResultSet(rs);

			return tasks;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
	public List<TaskBasicViewDto> getAllByProjectId(int pId) throws Exception {
		try {
			PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_TASKS_BY_PROJECT_ID_QUERY);
			pr.setInt(1, pId);
			ResultSet rs = pr.executeQuery();
			List<TaskBasicViewDto> tasks = this.fillListWithTaskBasicViewDtoFromResultSet(rs);

			return tasks;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
	public List<TaskBasicViewDto> getAllOpenTasksByUserId(int userId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection()
				.prepareStatement(SELECT_ALL_OPEN_TASKS_BY_USER_ID_QUERY)) {
			pr.setString(1, TaskStateType.done.getValue());
			pr.setInt(2, userId);
			ResultSet rs = pr.executeQuery();
			List<TaskBasicViewDto> tasks = this.fillListWithTaskBasicViewDtoFromResultSet(rs);

			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
	public List<TaskBasicViewDto> getFilteredTasksByIssueTypeAndDate(List<Integer> issueTypeIds, String firstDate, String secondDate) throws DatabaseException {
		List<String> allParts = new ArrayList<>();
		this.getSqlByIssueType(issueTypeIds, allParts);
		this.getSqlByDates(firstDate, secondDate, allParts);

		StringBuilder sqlQuery = new StringBuilder(
				"SELECT id, project_id, summary, priority_id, assignee_id, creator_id, due_date, state_id FROM tasks WHERE ");
		for (String sqlPart : allParts) {
			sqlQuery.append(sqlPart).append(" AND ");
		}
		sqlQuery.append(" is_deleted = 0;");

		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(sqlQuery.toString().trim())) {
			this.setParametersToFilteredPreparedStatement(issueTypeIds, firstDate, secondDate, pr);
			ResultSet rs = pr.executeQuery();
			List<TaskBasicViewDto> tasks = this.fillListWithTaskBasicViewDtoFromResultSet(rs);

			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
	public List<TaskBasicViewDto> getTasksWhichIncludePartOfSearchStringInName(String searchPart) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_TASKS_BY_PART_OF_NAME_QEURY)) {
			pr.setString(1, "%" + searchPart + "%");
			ResultSet rs = pr.executeQuery();
			List<TaskBasicViewDto> tasks = this.fillListWithTaskBasicViewDtoFromResultSet(rs);

			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
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

	@Override
	public void deleteById(Integer taskId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(DELETE_TASK_QUERY)) {
			pr.setInt(1, taskId);
			pr.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
	public int getCountOfTasks() throws DatabaseException {
		try (Statement st = dbManager.getConnection().createStatement()) {
			ResultSet rs = st.executeQuery(GET_COUNT_OF_TASKS);
			rs.next();
			int count = rs.getInt(1);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}

	@Override
	public boolean isExistById(Integer taskId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(IS_EXIST_TASK_QUERY)) {
			pr.setInt(1, taskId);
			ResultSet rs = pr.executeQuery();
			rs.next();

			if (rs.getInt(1) == 1) {
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}
	
	private List<TaskBasicViewDto> fillListWithTaskBasicViewDtoFromResultSet(ResultSet rs)
			throws SQLException, DatabaseException, Exception {
		List<TaskBasicViewDto> tasks = new ArrayList<>();

		while (rs.next()) {
			int id = rs.getInt("id");
			String summary = rs.getString("summary");
			LocalDate dueDate = rs.getDate("due_date").toLocalDate();
			int projectId = rs.getInt("project_id");
			int priorityId = rs.getInt("priority_id");
			int stateId = rs.getInt("state_id");
			int assigneeId = rs.getInt("assignee_id");
			int creatorId = rs.getInt("creator_id");

			TaskPriority priority = taskPriorityDao.getById(priorityId);
			TaskState state = taskStateDao.getById(stateId);
			User assignee = userDao.getUserById(assigneeId);
			User creator = userDao.getUserById(creatorId);
			Project project = projectDao.getById(projectId);

			TaskBasicViewDto viewTaskDto = new TaskBasicViewDto(id, project, summary, priority, assignee, creator,
					dueDate, state);

			tasks.add(viewTaskDto);
		}

		return tasks;
	}

	private void setParametersToFilteredPreparedStatement(List<Integer> issueTypeIds, String firstDate,
			String secondDate, PreparedStatement pr) throws SQLException {

		for (int index = 1; index <= issueTypeIds.size(); index++) {
			pr.setInt(index, issueTypeIds.get(index - 1));
		}

		int issueIdsSize = issueTypeIds.size();

		if (firstDate.isEmpty() && secondDate.isEmpty()) {
			return;
		}

		if (!firstDate.isEmpty() && !secondDate.isEmpty()) {
			pr.setDate((issueIdsSize + 1), java.sql.Date.valueOf(firstDate));
			pr.setDate((issueIdsSize + 2), java.sql.Date.valueOf(secondDate));
			return;
		}

		if (firstDate.isEmpty()) {
			pr.setDate((issueIdsSize + 1), java.sql.Date.valueOf(secondDate));
			return;
		}

		if (secondDate.isEmpty()) {
			pr.setDate((issueIdsSize + 1), java.sql.Date.valueOf(firstDate));
			return;
		}
	}

	private void getSqlByDates(String firstDate, String secondDate, List<String> allParts) {
		if (firstDate.isEmpty() && secondDate.isEmpty()) {
			return;
		}

		if (!firstDate.isEmpty() && !secondDate.isEmpty()) {
			allParts.add(BETWEEN_TWO_DUE_DATE_PART);
			return;
		}

		if (firstDate.isEmpty()) {
			allParts.add(BEFORE_DUE_DATE_QUERY_PART);
			return;
		}

		allParts.add(AFTER_DUE_DATE_QUERY_PART);
	}

	private void getSqlByIssueType(List<Integer> selectedIssueTypeIds, List<String> allParts) {
		StringBuilder query = new StringBuilder();
		query.append("issue_id IN (");

		for (int index = 0; index < selectedIssueTypeIds.size(); index++) {
			query.append("?").append(",");
		}

		query.deleteCharAt(query.length() - 1);
		query.append(")");

		allParts.add(query.toString().trim());
	}

	private void addImageToTask(TaskViewDetailsDto task) throws DatabaseException {
		if (task == null) {
			return;
		}

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



	private void addCommentsToTask(TaskViewDetailsDto viewTaskDto) throws TaskException, DatabaseException {
		if (viewTaskDto == null) {
			return;
		}

		List<CommentViewDto> comments = this.commentDao.getCommentsByTaskId(viewTaskDto.getId());
		viewTaskDto.setComments(comments);
	}

	@Override
	public void saveFileToDisk(Task task, MultipartFile f, String randomUUIDString) throws IOException {
		String dirPath = PATH_IMAGE_PREFFIX + "\\" + randomUUIDString + "\\";
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String fullPath = dirPath + f.getOriginalFilename();
		File convFile = new File(fullPath);
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(f.getBytes());
		fos.close();
		
		task.addImageUrl(fullPath);
	}

	@Override
	public Map<String, Integer> getCountForIssueTypes(String firstDate, String secondDate) throws DatabaseException {
		String startDateRangeQuery = this.getStartDateRangeQueryByTwoDates(firstDate, secondDate); 
		String sql = SELECT_COUNT_FOR_ALL_ISSUE_TYPES_QUERY.replace("INSERT_WHERE_CLAUSE", startDateRangeQuery);
		
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(sql)) {
			Map<String, Integer> issues = new HashMap<>();
			//fill map with default values
			this.fillIssueMapWithDefaultValues(issues);
			this.setDateValuesInPreparedStatementForChartSelectQuery(pr,firstDate,secondDate);
			
			ResultSet rs = pr.executeQuery();
			this.fillMapWithValuesFromDb(issues, rs);
			
			return issues;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}
	
	@Override
	public Map<String, Integer> getCountForStateTypes(String firstDate, String secondDate) throws DatabaseException{
		String startDateRangeQuery = this.getStartDateRangeQueryByTwoDates(firstDate, secondDate); 
		String sql = SELECT_COUNT_FOR_ALL_STATE_TYPES_QUERY.replace("INSERT_WHERE_CLAUSE", startDateRangeQuery);
		
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(sql)) {
			Map<String, Integer> states = new HashMap<>();
			//fill map with default values
			this.fillStatesMapWithDefaultValues(states);
			this.setDateValuesInPreparedStatementForChartSelectQuery(pr, firstDate,  secondDate);
			
			ResultSet rs = pr.executeQuery();
			this.fillMapWithValuesFromDb(states, rs);

			return states;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}
	
	private void setDateValuesInPreparedStatementForChartSelectQuery(PreparedStatement pr, String firstDate, String secondDate) throws SQLException {
		if (firstDate.isEmpty() && secondDate.isEmpty()) {
			return;
		}

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
	
	private String getStartDateRangeQueryByTwoDates(String firstDate, String secondDate) {
		if (firstDate.isEmpty() && secondDate.isEmpty()) {
			return "";
		}

		if (!firstDate.isEmpty() && !secondDate.isEmpty()) {
			return BETWEEN_TWO_START_DATE_PART;
		}

		if (firstDate.isEmpty()) {
			return BEFORE_START_DATE_QUERY_PART;
		}
		
		return AFTER_START_DATE_QUERY_PART;
	}
	
	private void fillIssueMapWithDefaultValues(Map<String, Integer> issues) {
		for (TaskIssueType issueType : TaskIssueType.values()) {
			issues.put(issueType.getValue(), 0);
		}
	}
	
	private void fillStatesMapWithDefaultValues(Map<String, Integer> states) {
		for (TaskStateType stateType : TaskStateType.values()) {
			states.put(stateType.getValue(), 0);
		}
	}
	
	private void fillMapWithValuesFromDb(Map<String, Integer> map, ResultSet rs) throws SQLException {
		while (rs.next()) {
			String type = rs.getString("type");
			int count = rs.getInt("count");
			
			map.put(type, count);
		}
	}

	@Override
	public String getValidDataRangeForTaskCharts() throws DatabaseException {
		try (Statement st = dbManager.getConnection().createStatement()){
			ResultSet rs = st.executeQuery(GET_FIRST_TASK_START_DATE_QUERY);
			String firstValidDate = "";
			String lastValidDate = "";
			
			while (rs.next()) {
				firstValidDate = rs.getString("first_date");
			}
			
			rs = st.executeQuery(GET_LAST_TASK_START_DATE_QUERY);
			
			while (rs.next()) {
				lastValidDate = rs.getString("last_date");
			}
			
			return firstValidDate + " - " + lastValidDate;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}
}
