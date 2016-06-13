package WebApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;

import java.sql.ResultSetMetaData;


@ApplicationScoped
public class UserManager 
{
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CONN_STRING = "jdbc:mysql://localhost:3306/user";
	
	private Object[][] colums = {{"id", "email", "first_name", "last_name" }};
	
	private String[] userType = {"user_info", "nytttable"}; 

	private UserComponent everyTable = new UserGroup("Every Table", 0);
	private UserComponent user = new UserGroup("user_info", 1);
	private UserComponent actors = new UserGroup("actors", 2);
	
	public UserManager()
	{
		everyTable.add(user);
		everyTable.add(actors);
		
		updateTable("user_info");
	}
	
	/**
	 * Get table
	 * @param table
	 * @return
	 */
	public Object[][] getTable(String table)
	{
		for(int i = 0; i < userType.length; i++)
		{
			if(everyTable.getComponent(i).getGroupName() == table)
				return everyTable.getComponent(i).getTable();
		}
		return null;
	}
	
	/**
	 * Get user from Table
	 * @param id
	 * @param tableIndex
	 * @return
	 */
	public UserComponent getUserComponent(int id)
	{
		return user.getByIdComponent(id);
	}
	
	/**
	 * Get table from SQL
	 * @param table
	 */
	public void updateTable(String table)
	{
		// SQL Query
		String sql = "SELECT * FROM " + table;
		
		// Clear table and try SQL query
		clearTable(table);
		try (
				Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
			) 
		{	
			// Loop Through SQL users and add components
			while(rs.next())
			{
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 0; i < userType.length; i++)
				{
					if(everyTable.getComponent(i).getGroupName() == table)
					{
						Object[] user = new Object[rsmd.getColumnCount()];
						
						for(int e = 0; e < rsmd.getColumnCount(); e++)
						{
							user[e] = rs.getObject(e + 1);
						}
						everyTable.getComponent(i).add(new User(i + 1, user));
					}
				}
			}
		}
		catch (SQLException e) 
		{
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL state: " + e.getSQLState());
		}
	}
	
	/**
	 * Get Table and clear it.
	 * @param table
	 */
	public void clearTable(String table)
	{
		for(int i = 0; i < userType.length; i++)
		{
			if(everyTable.getComponent(i).getGroupName() == table)
				everyTable.getComponent(i).clear();
		}
	}
	
	/**
	 * Insert user into SQL.
	 * @param user
	 * @return boolean
	 * @throws Exception
	 */
	public boolean insertUser(UserComponent user) throws Exception
	{
		// Create SQL Query depending on Table
		String colum = "";
		String values = "NULL";
		for(int i = 0; i < colums[user.getUserType() - 1].length; i++)
		{
			if(i != 0)
				values += "?";
				
			colum += colums[user.getUserType() - 1][i];
			
			if(i != colums[user.getUserType() - 1].length - 1)
			{
				colum += ", ";
				values += ", ";
			}
		}
		String sql = "INSERT INTO " + userType[user.getUserType() - 1] + "(" + colum  + ") " +
				"VALUES (" + values + ")";
		
		
		
				ResultSet keys = null;
		try (
				Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) 
		{	
			// Insert info depending on Table
			for(int i = 0; i < user.getUser().length; i++)
			{
				if(user.getUser()[i].getClass().equals(Integer.class))
				{
					stmt.setInt(i + 1, (int) user.getUser()[i]);
				}
				else if(user.getUser()[i].getClass().equals(String.class))
				{
					if(user.getUser()[i].equals("true"))
						stmt.setInt(i + 1, 1);
					else if(user.getUser()[i].equals("false"))
						stmt.setInt(i + 1, 0);
					else
						stmt.setString(i + 1, (String) user.getUser()[i]);
				}
				else if(user.getUser()[i].getClass().equals(Float.class))
				{
					stmt.setFloat(i + 1, (Float) user.getUser()[i]);
				}
				else if(user.getUser()[i].getClass().equals(Boolean.class))
				{
					stmt.setObject(i + 1, user.getUser()[i]);
				}
			}
			
			int affected = stmt.executeUpdate();
			
			// Update Table when done
			if (affected == 1){
				updateTable(userType[user.getUserType() - 1]);
			} else {
				return false;
			}
		}
		catch (SQLException e) 
		{
			System.err.println(e);
			return false;
		} finally
		{
			if (keys != null) keys.close();
		}
		
		return true;
	}
	
	/**
	 * Update SQL row
	 * @param user
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateUser(UserComponent user) throws Exception 
	{
		// Create SQL Query depending on Table
		String colum = "";
		for(int i = 1; i < colums[0].length; i++)
		{
			colum += colums[0][i];
			
			if(i != colums[0].length - 1)
			{
				colum += " = ?, ";
			}
		}
		colum += " = ? WHERE id = ?";
		
		String sql = "UPDATE " + userType[user.getUserType() - 1] + " SET " + colum;
		
		try (
				Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql);
			)
		{
			// Update info depending on Table
			for(int i = 1; i < user.getUser().length; i++)
			{
				if(user.getUser()[i].getClass().equals(Integer.class))
				{
					stmt.setInt(i, (int) user.getUser()[i]);
				}
				else if(user.getUser()[i].getClass().equals(String.class))
				{
					if(user.getUser()[i].equals("true"))
						stmt.setInt(i, 1);
					else if(user.getUser()[i].equals("false"))
						stmt.setInt(i, 0);
					else
						stmt.setString(i, (String) user.getUser()[i]);
				}
				else if(user.getUser()[i].getClass().equals(Float.class))
				{
					stmt.setFloat(i, (Float) user.getUser()[i]);
				}
				else if(user.getUser()[i].getClass().equals(Boolean.class))
				{
					stmt.setBoolean(i, (Boolean) user.getUser()[i]);
				}
			}
			stmt.setInt(user.getUser().length, user.getId());
			
			int affected = stmt.executeUpdate();
			
			// Update Table when done
			if (affected == 1) {
				updateTable(userType[1]);
				return true;
			} else {
				return false;
			}
			
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
	
	/**
	 * Remove SQL row
	 * @param user
	 * @return boolean
	 * @throws Exception
	 */
	public boolean removeUser(UserComponent user) throws Exception {
		
		// Create SQL query depending on Table
		String sql = "DELETE FROM " + userType[user.getUserType() - 1] + " WHERE " +
						userType[user.getUserType() - 1] + ".id = ?";
		
		try (
				Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(sql);
			)
		{
			// Get row from ID and delete
			stmt.setInt(1, user.getId());
			
			int affected = stmt.executeUpdate();
			
			// Update Table when done
			if (affected == 1) {
				updateTable(userType[user.getUserType() - 1]);
				return true;
			} else {
				return false;
			}
			
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
}
