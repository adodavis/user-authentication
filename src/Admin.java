// Class used to facilitate changes to Admin database table and display results to admin view
public class Admin {
	String username;
	String dateSubmitted;
	int approval;
	int display;
	
	Admin(String user){
		this.username = user;
	}
	
	Admin(String user, String date, int approval, int display){
		this.username = user;
		this.dateSubmitted = date;
		this.approval = approval;
		this.display = display;
	}
	
	String getUsername() {
		return this.username;
	}
	
	String getDateSubmitted() {
		return this.dateSubmitted;
	}
	
	int getApproval() {
		return this.approval;
	}
	
	int getDisplay() {
		return this.display;
	}
}
