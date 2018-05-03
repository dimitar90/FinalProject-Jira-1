package com.jira.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.db.DBManager;
import com.jira.dto.CommentViewDto;
import com.jira.exception.CommentException;
import com.jira.exception.DatabaseException;
import com.jira.exception.TaskException;
import com.jira.interfaces.ICommentTaskDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.CommentTask;
import com.jira.model.User;

@Component
public class CommentTaskDao implements ICommentTaskDao {
	private static final String INVALID_DATA = "Invalid credentials";
	
	private static final String SELECT_COMMENTS_BY_TASK_ID = "SELECT description, date, user_id FROM comments WHERE task_id = ? ;";
	private static final String INSERT_QUERY = "INSERT INTO comments (description, date, user_id, task_id) VALUES (?, ?, ?, ?);";

	
	private final DBManager dbManager;
	private final IUserDao userDao;
	
	@Autowired
	public CommentTaskDao(DBManager dbManager, IUserDao userDao) {
		this.dbManager = dbManager;
		this.userDao = userDao;
	}

	public void save(CommentTask comment) throws CommentException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(INSERT_QUERY)) {
			pr.setString(1, comment.getDescription());
			pr.setTimestamp(2, Timestamp.valueOf(comment.getDateTime()));
			pr.setInt(3, comment.getUserId());
			pr.setInt(4, comment.getTaskId());

			pr.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommentException(INVALID_DATA, e);
		}
	}
	
	public List<CommentViewDto> getCommentsByTaskId(int taskId) throws DatabaseException {
		
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_COMMENTS_BY_TASK_ID)) {
			pr.setInt(1, taskId);
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
			
			return comments;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}
}
