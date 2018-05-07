package com.jira.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jira.dto.TaskBasicViewDto;
import com.jira.dto.TaskViewDetailsDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.TaskException;
import com.jira.exception.UserDataException;
import com.jira.model.Task;

@Component
public interface ITaskDao {
	public int saveTask(Task task) throws DatabaseException, SQLException;
	
	public TaskViewDetailsDto getById(int taskId) throws DatabaseException;
	
	public void changeStateById(int taskId, int newStateId) throws DatabaseException;
	
	public void deleteById(Integer taskId) throws DatabaseException;
	
	public List<TaskBasicViewDto> getAllByProjectId(int pId) throws Exception;
	
	public List<TaskBasicViewDto> getAllOpenTasksByUserId(int userId) throws DatabaseException;
	
	public int getCountOfTasks() throws DatabaseException;

	public List<TaskBasicViewDto> getByCurrentPageNumberAndTaskPerPage(Integer pageNumber, int tasksCountOnPage) throws DatabaseException, Exception;

	public boolean isExistById(Integer taskId) throws DatabaseException;

	public List<TaskBasicViewDto> getFilteredTasksByIssueTypeAndDate(List<Integer> issueTypeIds, String firstDate,
			String secondDate) throws DatabaseException;

	public void saveFileToDisk(Task task, MultipartFile f, String randomUUIDString) throws IOException;

	public List<TaskBasicViewDto> getTasksWhichIncludePartOfSearchStringInName(String searchPart) throws DatabaseException;

	public Map<String, Integer> getCountForIssueTypes(String firstDate, String secondDate) throws DatabaseException;

	public Map<String, Integer> getCountForStateTypes(String firstDate, String secondDate) throws DatabaseException;

	public String getValidDataRangeForTaskCharts() throws DatabaseException;
}
