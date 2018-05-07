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
import com.jira.interfaces.ICommentDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Comment;
import com.jira.model.User;
import com.jira.util.ImageConvertor;
import com.mysql.jdbc.Statement;

@Component
public class CommentDao implements ICommentDao {
	private static final String INVALID_DATA = "Invalid credentials";
	
	private static final String SELECT_COMMENTS_BY_TASK_ID = "SELECT description, date, user_id FROM comments WHERE task_id = ? ;";
	private static final String INSERT_QUERY = "INSERT INTO comments (description, date, user_id, task_id) VALUES (?, ?, ?, ?);";
	private static final String SELECT_COUNT_OF_COMMENTS_BY_TASK_ID_QUERY = "SELECT COUNT(*) FROM comments WHERE task_id = ?";

	private final DBManager dbManager;
	private final IUserDao userDao;
	
	@Autowired
	public CommentDao(DBManager dbManager, IUserDao userDao) {
		this.dbManager = dbManager;
		this.userDao = userDao;
	}

	public int save(Comment comment) throws  DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			pr.setString(1, comment.getDescription());
			pr.setTimestamp(2, Timestamp.valueOf(comment.getDateTime()));
			pr.setInt(3, comment.getUserId());
			pr.setInt(4, comment.getTaskId());
			
			ResultSet rs = pr.getGeneratedKeys();
			
			if (rs.next()) {
				Integer commentId = rs.getInt(1);
				comment.setId(commentId);
			}
			
			pr.executeUpdate();
			return comment.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
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
				String userImageBase64 = ImageConvertor.convertFromLocalPathToBase64String(user.getImageUrl());
				//String userAvatarName = user.getImageUrl().substring(user.getImageUrl().lastIndexOf("\\") + 1);
				CommentViewDto comment = new CommentViewDto(description, dateTime, user.getName(), userImageBase64);
				comments.add(comment);
			}
			
			return comments;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}
	
	public int getCommentsCountByTaskId(int taskId) throws DatabaseException {
		try (PreparedStatement pr = dbManager.getConnection().prepareStatement(SELECT_COUNT_OF_COMMENTS_BY_TASK_ID_QUERY)) {
			pr.setInt(1, taskId);
			ResultSet rs = pr.executeQuery();
			
			rs.next();
			return rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(INVALID_DATA, e);
		}
	}
}
