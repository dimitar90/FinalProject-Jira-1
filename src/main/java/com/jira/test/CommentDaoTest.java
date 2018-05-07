package com.jira.test;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import com.jira.dao.CommentDao;
import com.jira.dao.UserDao;
import com.jira.db.DBManager;
import com.jira.dto.CommentViewDto;
import com.jira.exception.CommentException;
import com.jira.exception.DatabaseException;
import com.jira.interfaces.ICommentDao;
import com.jira.interfaces.IUserDao;
import com.jira.model.Comment;

public class CommentDaoTest {

	@Test
	public void testSaveCommentWorkCorrectly() throws DatabaseException {
		DBManager dbManager = new DBManager();
		IUserDao userDao = new UserDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		
		Comment comment = new Comment("description", LocalDateTime.now(), 1, 1);
		int commentId = commentDao.save(comment);
		
		assertEquals(comment.getId(), commentId);
	}
	
	@Test(expected=DatabaseException.class)
	public void testThrowsDatabaseExceptionWhenPassedInvalidDescription() throws DatabaseException {
		DBManager dbManager = new DBManager();
		IUserDao userDao = new UserDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		
		Comment comment = new Comment(null, LocalDateTime.now(), 1, 1);
		commentDao.save(comment);
	}
	
	@Test(expected=DatabaseException.class)
	public void testThrowsDatabaseExceptionWhenPassedNotExistingCreator() throws DatabaseException {
		DBManager dbManager = new DBManager();
		IUserDao userDao = new UserDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		
		Comment comment = new Comment(null, LocalDateTime.now(), 999999, 1);
		commentDao.save(comment);
	}
	
	@Test()
	public void testGetCommentsByTaskIdReturnsCorrectResult() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		IUserDao userDao = new UserDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		int taskId = 1;
		String sql = "SELECT COUNT(*) FROM comments WHERE task_id = ?";
		PreparedStatement pr = dbManager.getConnection().prepareStatement(sql);
		pr.setInt(1, taskId);
		ResultSet rs = pr.executeQuery();
		rs.next();
		int commentsCount = rs.getInt(1);
		
		List<CommentViewDto> comments = commentDao.getCommentsByTaskId(taskId);
		
		assertEquals(commentsCount, comments.size());
	}
	
	@Test()
	public void testGetCommentsCountByTaskIdReturnsCorrectResult() throws DatabaseException, SQLException {
		DBManager dbManager = new DBManager();
		IUserDao userDao = new UserDao(dbManager);
		ICommentDao commentDao = new CommentDao(dbManager, userDao);
		int taskId = 1;
		String sql = "SELECT COUNT(*) FROM comments WHERE task_id = ?";
		PreparedStatement pr = dbManager.getConnection().prepareStatement(sql);
		pr.setInt(1, taskId);
		ResultSet rs = pr.executeQuery();
		rs.next();
		int commentsCountFromSql = rs.getInt(1);
		
		int commentCountFromMethod = commentDao.getCommentsCountByTaskId(taskId);
		
		assertEquals(commentsCountFromSql, commentCountFromMethod);
	}
	
}
