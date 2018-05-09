package com.jira.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jira.dao.CommentDao;
import com.jira.dao.ProjectDao;
import com.jira.dao.TaskDao;
import com.jira.dao.TaskIssueDao;
import com.jira.dao.TaskPriorityDao;
import com.jira.dao.TaskStateDao;
import com.jira.dao.UserDao;
import com.jira.db.DBManager;
import com.jira.dto.TaskBasicViewDto;
import com.jira.dto.TaskViewDetailsDto;
import com.jira.exception.DatabaseException;
import com.jira.interfaces.ICommentDao;
import com.jira.interfaces.IProjectDao;
import com.jira.interfaces.ITaskDao;
import com.jira.interfaces.ITaskIssueDao;
import com.jira.interfaces.ITaskPriorityDao;
import com.jira.interfaces.ITaskStateDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Task;

public class TaskDaoTest {

	@Test
	public void testCreateNewTaskSuccessfully() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		Task task = new Task("summary", LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 1, 1);
		int taskId = taskDao.saveTask(task);
		
		assertEquals(task.getId(), taskId);
	}
	
	@Test(expected=DatabaseException.class)
	public void testCreateTaskWithoutSummaryThrowsException() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		Task task = new Task(null, LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 1, 1);
		int taskId = taskDao.saveTask(task);
		
		assertSame(task.getId(), taskId);
	}
	
	@Test(expected=DatabaseException.class)
	public void testCreateTaskWithNoExistCreatorThrowsDatabaseExcetpion() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		Task task = new Task("summary", LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 99999, 1);
		int taskId = taskDao.saveTask(task);
		
		assertSame(task.getId(), taskId);
	}

	
	@Test()
	public void testGetByIdReturnCorrectTask() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		Task task = new Task("TestForCorrectReturnedTaskSummary", LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 1, 1);
		int taskId = taskDao.saveTask(task);
		TaskViewDetailsDto returnedTask = taskDao.getById(taskId);
		
		assertEquals(returnedTask.getId(), taskId);
		assertEquals(returnedTask.getSummary(), "TestForCorrectReturnedTaskSummary");
	}
	
	@Test()
	public void testGetFilteredTasksByDueDateReturnsCorrectResult() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		List<Integer> issueTypeIds = new ArrayList<>();
		issueTypeIds.add(1);
		issueTypeIds.add(2);
		issueTypeIds.add(3);
		issueTypeIds.add(4);
		issueTypeIds.add(5);
		
		String startDateToString = "2018-05-05";
		String endDateToString = "2018-05-10";
		
		List<TaskBasicViewDto> tasks = taskDao.getFilteredTasksByIssueTypeAndDate(issueTypeIds, startDateToString, endDateToString);
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(startDateToString, formatter);
		LocalDate endDate = LocalDate.parse(endDateToString, formatter);
		
		assertTrue(tasks.stream().allMatch(t -> t.getDueDate().compareTo(startDate) >= 0 && t.getDueDate().compareTo(endDate) <= 0));
	}
	
	@Test()
	public void testGetFilteredTasksWithSearchByPartOfSummaryReturnsCorrectResult() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		String searchPattern = "um";
		List<TaskBasicViewDto> tasks = taskDao.getTasksWhichIncludePartOfSearchStringInName(searchPattern);
		
		assertTrue(tasks.stream().allMatch(t -> t.getSummary().contains(searchPattern)));
	}
	
	@Test()
	public void testChangeStateToTaskWorkingCorrectly() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		int newStateId = 3;
		Task task = new Task("summary", LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 1, 1);
		int taskId = taskDao.saveTask(task);
		taskDao.changeStateById(taskId, newStateId);
		TaskViewDetailsDto returnedTask = taskDao.getById(taskId);
		
		assertEquals(newStateId, returnedTask.getState().getId());
	}
	
	@Test()
	public void testIsExistByIdReturnTrueWhenPassExistingTaskId() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		Task task = new Task("summary", LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 1, 1);
		int taskId = taskDao.saveTask(task);
		
		assertTrue(taskDao.isExistById(taskId));
	}
	
	@Test()
	public void testIsExistByIdReturnFalseWhenPassInvalidTaskId() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		assertFalse(taskDao.isExistById(999999));
	}
	
	@Test()
	public void testDeleteTaskByIdWorkingCorrectly() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		Task task = new Task("summary", LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 1, 1);
		int taskId = taskDao.saveTask(task);
		
		taskDao.deleteById(taskId);
		
		assertFalse(taskDao.isExistById(taskId));
	}
	
	@Test()
	public void testGetCountOfTasksWorkingCorrectly() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		ITaskPriorityDao taskPriorityDao = new TaskPriorityDao(dbManager);
		ITaskStateDao taskStateDao = new TaskStateDao(dbManager);
		ITaskIssueDao taskIssueDao = new TaskIssueDao(dbManager);
		IUserDao userDao = new UserDao(dbManager);
		IProjectDao projectDao = new ProjectDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		ITaskDao taskDao = new TaskDao(dbManager, taskPriorityDao, taskStateDao, taskIssueDao, userDao, projectDao, commentDao);
		
		int currentTasksCount = taskDao.getCountOfTasks();
		
		Task task = new Task("summary", LocalDate.now().toString(), LocalDate.now().toString(), "description", 1, 1 , 1, 1, 1);
		int taskId = taskDao.saveTask(task);
		
		assertEquals(taskDao.getCountOfTasks(), currentTasksCount + 1);
	}
}
