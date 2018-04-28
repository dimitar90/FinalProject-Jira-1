package com.jira.dao;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.db.DBManager;
import com.jira.exception.CommentException;
import com.jira.interfaces.ICommentTaskDao;
import com.jira.model.CommentTask;

@Component
public class CommentTaskDao implements ICommentTaskDao {
	private static final String INVALID_DATA = "Invalid credentials";

	private static final String INSERT_QUERY = "INSERT INTO comments (description, date, user_id, task_id) VALUES (?, ?, ?, ?);";

	
	private final DBManager dbManager;

	@Autowired
	public CommentTaskDao(DBManager dbManager) {
		this.dbManager = dbManager;
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
}
