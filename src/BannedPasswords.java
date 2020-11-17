//class used to obtain records from BannedPasswords database table
public class BannedPasswords {
	private String password;

	public BannedPasswords(String pass) {
		this.password = pass;
	}
	
	public String getPassword() {
		return password;
	}
}
