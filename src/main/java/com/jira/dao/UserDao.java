package com.jira.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

//import org.hibernate.boot.model.relational.Database;
//import org.hibernate.boot.spi.MetadataBuildingOptions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.db.DBManager;
import com.jira.dto.UserDto;
import com.jira.exception.DatabaseException;
import com.jira.exception.UserDataException;
import com.jira.interfaces.IUserDao;
import com.jira.model.User;

@Component
public class UserDao implements IUserDao {
	private static final String MSG_SQL_INVALID_DATA = "Invalid username or password";
	private static final String MSG_INVALID_DATA = "Wrong email or password";
	private static final String MSG_INVALID_USER_NAME_FOR_DB = "Invalid name for user";
	private static final String MSG_INVALID_USER_ID_FOR_DB = "Invalid id for user";
	private static final String MSG_INVALID_PROJECT_ID_FOR_DB = "Invalid id of project";
	private static final String MSG_WRONG_EMAIL = "User doesn't exist";
	private static final String MSG_INVALID_CRETITENTIALS = "Invalid creditentials";
	private static final String MSG_INVALID_TOKEN = "No such token in";

	@Autowired
	private final DBManager manager;

	@Autowired
	public UserDao(DBManager dbManager) {
		this.manager = dbManager;
	}

	@Override
	public void saveUser(User u) throws DatabaseException, SQLException {
		String sql = "INSERT INTO users(full_name,password,email,image_url) VALUES (?,?,?,?)";
		try {
			this.manager.getConnection().setAutoCommit(false);
			String password = u.getPassword();
			String username = u.getName();
			String email = u.getEmail();
			String imageUrl = u.getImageUrl();
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, username);
			ps.setString(2, hashed);
			ps.setString(3, email);
			ps.setString(4, imageUrl);
			ps.executeUpdate();
			ResultSet res = ps.getGeneratedKeys();

			res.next();
			u.setIt(res.getInt(1));

			this.manager.getConnection().commit();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			this.manager.getConnection().rollback();
			throw new DatabaseException(MSG_INVALID_DATA);
		} finally {
			this.manager.getConnection().setAutoCommit(true);
		}
	}

	@Override
	public User getUser(String email, String password) throws UserDataException {
		String sql = "SELECT id,email,full_name,password,image_url FROM users WHERE email = ?";
		try {
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setString(1, email);

		ResultSet result = ps.executeQuery();

		if (result.next()) {
			User u = User.getUser(result.getInt("id"),
					result.getString("full_name"),
					result.getString("password"),
					result.getString("email"),
					result.getString("image_url"));

			if (BCrypt.checkpw(password, u.getPassword())) {
				return u;
			}
		}
		ps.close();
		return null;
		}catch (SQLException e) {
			throw new UserDataException(MSG_SQL_INVALID_DATA);
		}

	}

	@Override
	public List<User> getAll() throws SQLException {
		String sql = "SELECT id, full_name, email, image_url FROM users";
		List<User> users = new LinkedList<User>();

		Statement st = this.manager.getConnection().createStatement();

		ResultSet result = st.executeQuery(sql);

		while (result.next()) {
			users.add(User.getUser(result.getInt("id"), result.getString("full_name"), result.getString("email"),
					result.getString("image_url")));
		}
		st.close();
		return users;
	}

	@Override
	public User createUser(String username, String email, String password) {
		return User.getUser(username, email, password);
	}

	@Override
	public User createUser(String username, String email, String password, String imageUrl) {
		return User.getUser(username, email, password, imageUrl);
	}

	@Override
	public void changeName(String newName, int userId) throws DatabaseException, SQLException {
		String sql = "UPDATE users SET full_name = ? WHERE id = ?";
		try {
			this.manager.getConnection().setAutoCommit(false);
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, newName);
			ps.setInt(2, userId);
			ps.executeUpdate();

			this.manager.getConnection().commit();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			this.manager.getConnection().rollback();
			throw new DatabaseException(MSG_INVALID_DATA);
		} finally {
			this.manager.getConnection().setAutoCommit(true);
		}
	}

	@Override
	public void changePassword(String newPass, int userId) throws SQLException, DatabaseException {
		String sql = "UPDATE users SET password = ? WHERE id = ?";

		String hashed = BCrypt.hashpw(newPass, BCrypt.gensalt());
		try {
			this.manager.getConnection().setAutoCommit(false);
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, hashed);
			ps.setInt(2, userId);
			ps.executeUpdate();
			this.manager.getConnection().commit();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			this.manager.getConnection().rollback();
			throw new DatabaseException(MSG_INVALID_DATA);
		} finally {
			this.manager.getConnection().setAutoCommit(true);
		}
	}

	@Override
	public int getUserId(String email, String password) throws  UserDataException {
		String sql = "SELECT id,password FROM users WHERE email = ?";
		try {
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setString(1, email);
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			int id = result.getInt("id");
			String hashed = result.getString("password");
			if (BCrypt.checkpw(password, hashed)) {
				return id;
			}
		}
		ps.close();

		return 0;
		}catch (SQLException e) {
			throw new UserDataException(MSG_INVALID_DATA);
		}
	}

	@Override
	public User getUserById(int id) throws UserDataException, SQLException {
		String sql = "SELECT * FROM users WHERE id = ?";
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setInt(1, id);

		ResultSet result = ps.executeQuery();

		if (result.next()) {
			return User.getUser(result.getInt("id"), result.getString("full_name"), result.getString("email"),
					result.getString("image_url"));
		}
		ps.close();
		throw new UserDataException(MSG_SQL_INVALID_DATA);

	}

	@Override
	public void changeImageUrl(String imageUrl, User u) throws SQLException, DatabaseException {
		String sql = "UPDATE users SET image_url = ? WHERE email = ?";
		try {
			this.manager.getConnection().setAutoCommit(false);
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, imageUrl);
			ps.setString(2, u.getEmail());
			ps.executeUpdate();

			this.manager.getConnection().commit();
			ps.close();
			u.changeImageUrl(imageUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			this.manager.getConnection().rollback();
			throw new DatabaseException(MSG_INVALID_DATA);
		} finally {
			this.manager.getConnection().setAutoCommit(true);
		}
	}

	@Override
	public boolean isExistById(int userId) throws DatabaseException {
		String sql = "SELECT id FROM users WHERE id = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet result = ps.executeQuery();

			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_SQL_INVALID_DATA);
		}
		return false;
	}

	public int getuserByName(String leadName) throws DatabaseException {
		String sql = "SELECT id FROM users WHERE full_name = ?";

		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, leadName);

			ResultSet result = ps.executeQuery();

			result.next();
			int leadId = result.getInt("id");
			ps.close();
			return leadId;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_INVALID_USER_NAME_FOR_DB);
		}

	}

	public UserDto getUserDtoById(Integer id) throws DatabaseException {
		String sql = "SELECT id,email,full_name,image_url FROM users WHERE id = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();

			result.next();

			int userId = result.getInt("id");
			String email = result.getString("email");
			String name = result.getString("full_name");
			String imageUrl = result.getString("image_url");

			UserDto dto = UserDto.getDto(userId, email, name, imageUrl);

			ps.close();
			return dto;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_INVALID_USER_ID_FOR_DB);
		}
	}

	@Override
	public boolean chechEmail(String email) throws SQLException, UserDataException {
		String sql = "SELECT id FROM users WHERE email = ?";
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setString(1, email);
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			return true;
		}

		return false;
	}

	@Override
	public UserDto getDtoByProjectId(int projectId) throws UserDataException, DatabaseException {
		String sql = "SELECT project_lead_id FROM projects WHERE is_deleted = 0 AND id = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setInt(1, projectId);
			ResultSet result = ps.executeQuery();

			result.next();

			int leadId = result.getInt(1);
			ps.close();
			return this.getUserDtoById(leadId);
		} catch (SQLException e) {
			throw new UserDataException(MSG_INVALID_PROJECT_ID_FOR_DB);
		}
	}

	@Override
	public Optional<User> findByEmail(String userEmail) throws DatabaseException {//>>
		String sql = "SELECT id,email,full_name,image_url FROM users WHERE email = ?";
		Optional<User> optional = Optional.empty();
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, userEmail);
			ResultSet result = ps.executeQuery();

			if (result.next()) {
				int id = result.getInt("id");
				String email = result.getString("email");
				String name = result.getString("full_name");
				String imageUrl = result.getString("image_url");

				User user = User.getUser(id, name, email, imageUrl);

				optional = Optional.of(user);			}
			ps.close();
			return optional;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_INVALID_CRETITENTIALS);
		}

	}

	@Override
	public Optional<User> findByResetToken(String resetToken) throws DatabaseException {
		String sql = "SELECT id,email,full_name,image_url FROM users WHERE reset_token = ?";
		Optional<User> optional = Optional.empty();
		try {
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setString(1, resetToken);
		ResultSet result = ps.executeQuery();
		
		if (result.next()) {
			int id = result.getInt("id");
			String email = result.getString("email");
			String name = result.getString("full_name");
			String imageUrl = result.getString("image_url");

			User user = User.getUser(id, name, email, imageUrl);

			optional = Optional.of(user);
		}
		ps.close();
		return optional;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_INVALID_TOKEN);
		}
	}

	public void saveToken(User user) throws DatabaseException {
		String sql = "UPDATE users SET reset_token = ? WHERE email = ?";
		try {
		String email = user.getEmail();
		String token = user.getResetToken();
		
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		ps.setString(1, token);
		ps.setString(2, email);
		ps.executeUpdate();
		ps.close();
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(MSG_INVALID_CRETITENTIALS);
		}
	}

	public void resetPassword(User user) throws DatabaseException {
		String sql = "UPDATE users SET password = ? WHERE reset_token = ? AND email = ?";
		String password = user.getPassword();
		
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		String email = user.getEmail();
		String token = user.getResetToken();
		try {
		PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
		
		ps.setString(1, hashed);
		ps.setString(2, token);
		ps.setString(3, email);
		ps.executeUpdate();
		
		ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Invalid creditentials");
		}
	}

	public void resetToken(User user) throws DatabaseException {
		String sql = "UPDATE users SET reset_token = null WHERE email = ?";
		try {
			PreparedStatement ps = this.manager.getConnection().prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.executeUpdate();
			
			ps.close();
			}catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Invalid creditentials");
			}
	}


}
