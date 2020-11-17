import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

public class DbOps {
	private Connection conn;
	private PreparedStatement ps;
	List<User> users = new ArrayList<User>();
	List<BannedPasswords> passes = new ArrayList<BannedPasswords>();
	List<Admin> requests = new ArrayList<Admin>();
	List<Admin> pendingRequests = new ArrayList<Admin>();
	
	// Establishes connection to database
	public DbOps() {
		try {
			String url = "jdbc:mysql://localhost:3306/user_authentication";
			String user = "root";
			String pass = "JoyS3nti313!!";
			
			conn = DriverManager.getConnection(url, user, pass);
			
			if (conn != null)
				System.out.println("Connected to DB");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Fills combo box with database question values
	public void fillComboBox(JComboBox<String> qComboBox) {
		
		try {
			String query = "SELECT Question FROM securityquestions";
			ps = conn.prepareStatement(query);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()) {
				qComboBox.addItem(rs.getString("Question"));
			}
		} 
		catch (SQLException sqle) {
			System.out.println(sqle);
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
			
	// Retrieves user details from database to be stored in a list for future checks
	public void obtainUserInfo() {
		try {
			String query = "SELECT Username, Password, Email FROM user";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()) {
				User user = new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Email"));
				users.add(user);
			}
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	// Returns a list containing pertinent user login information
	public List<User> getUserLoginDetails(){
		if (users.isEmpty()) {
			obtainUserInfo();
		}
		
		return users;
	}
	
	// Retrieves user security questions from database to be used in authenticating password resets
	public void obtainUserQuestions() {
		try {
			String query = "SELECT Username, Question1, Question2, Question3, Answer1, Answer2, Answer3 FROM user";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()) {
				User user = new User(rs.getString("Username"), rs.getString("Question1"), rs.getString("Question2"), rs.getString("Question3"), rs.getString("Answer1"), rs.getString("Answer2"), rs.getString("Answer3"));
				users.add(user);
			}
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	// Returns a list of security questions
	List<User> getSecurityQuestions(){
		while (users.isEmpty()) {
			obtainUserQuestions();
		}
		
		return users;
	}
	
	// Retrieves banned passwords from the database to be stored in a list for password checks
	public void obtainBannedPasses() {
		try {
			String query = "SELECT Password FROM bannedpasswords";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()) {					
				passes.add(new BannedPasswords(rs.getString("Password")));
			}
			
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	// Returns a list of banned passwords
	List<BannedPasswords> getBannedPasswords() {
		if (passes.isEmpty()) {
			obtainBannedPasses();
		}
		
		return passes;
	}
	
	// Inserts user-entered values into User table
	public void submitUserInfo(User user) {
		try {
			String query = "INSERT INTO user (Username, Password, FullName, DateofBirth, Address, Email,"
					+ "SSN, Question1, Answer1, Question2, Answer2, Question3, Answer3)"
					+ "VALUES ('"+ user.getUsername() + "', '"+ user.getPassword() + "', '"+ user.getName() 
					+ "', '"+ user.getDOB() + "', '"+ user.getAddress() + "', '"+ user.getEmail() + "', '"+ user.getSSN() 
					+ "', '"+ user.getQuestion1() + "', '"+ user.getAnswer1() + "', '"+ user.getQuestion2()
					+ "', '"+ user.getAnswer2() + "', '" + user.getQuestion3() + "', '" + user.getAnswer3() + "' )";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.execute();
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	// Inserts password reset data into Admin table
	public void submitRequests(Admin admin) {
		try {
			String query = "INSERT INTO admin (Username, DateSubmitted, ResetApproval, Display)"
					+ "VALUES ('"+ admin.getUsername() + "', '"+ admin.getDateSubmitted() + "', '" + admin.getApproval()
					+ "', '" + admin.getDisplay() + "' )";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.execute();
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	// Retrieves password reset requests from database
	public void obtainPasswordRequests() {
	    try {
	      String query = "SELECT Username, DateSubmitted, ResetApproval, Display FROM admin";
	      PreparedStatement ps = this.conn.prepareStatement(query);
	      ps.execute();
	      ResultSet rs = ps.getResultSet();
	      while (rs.next()) {
	        Admin admin = new Admin(rs.getString("Username"), rs.getString("DateSubmitted"), rs.getInt("ResetApproval"), rs.getInt("Display"));
	        this.requests.add(admin);
	      } 
	    } 
	    catch (SQLException sqle) {
	      sqle.printStackTrace();
	    } 
	    finally {
	    	try {
	    		ps.close();
	    	}
	    	catch(SQLException sqle) {
	    		sqle.printStackTrace();
	    	}
	    }
	  }
	
	//returns a list of password reset requests
		public List<Admin> getPasswordRequests(){
			if (requests.isEmpty()) {
				obtainPasswordRequests();
			}
			
			return requests;
		}
	
	// Retrieves pending password reset requests from database
	public void obtainPendingRequests() {
		try {
			String query = "SELECT * FROM admin WHERE Display = 1";
			//String query = "SELECT Username, DateSubmitted, ResetApproval, Display FROM admin";
			ps = conn.prepareStatement(query);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()) {
				Admin admin = new Admin(rs.getString("Username"), rs.getString("DateSubmitted"), rs.getInt("ResetApproval"), rs.getInt("Display"));
				pendingRequests.add(admin);
			}
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	// Returns a List of pending password reset requests
	List<Admin> getPendingRequests() {
		if (pendingRequests.isEmpty()) {
			obtainPendingRequests();
		}
		
		return pendingRequests;
	}
	
	// Removes row from database table after admin approves request
	public void updateAdminRequests(String user, int approval, int display) {
		try {
			String query = "Update admin SET ResetApproval = ?, Display = ? WHERE Username = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, approval);
			ps.setInt(2, display);
			ps.setString(3, user);
			ps.executeUpdate();
		} 
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	// Updates user password to changed password
	void updatePassword(String user, String password) {
		try {
			String query = "UPDATE User SET Password = ? WHERE Username = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, password);
			ps.setString(2, user);
			ps.executeUpdate();
		} 
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				ps.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}	
}
