//class used to implement structure of User database table
public class User {
	private String username;
	private String password;
	private String fullName;
	private String address;
	private String question1, question2, question3;
	private String answer1, answer2, answer3;
	private String dob;
	private String email;
	private String ssn;
	
	public User() {};
	
	public User(String user) {
		this.username = user;
	} 
	
	public User(String user, String pass) {
		this.username = user;
		this.password = pass;
	}
	
	public User(String user, String pass, String e) {
		this.username = user;
		this.password = pass;
		this.email = e;
	}
	
	public User(String user, String q1, String q2, String q3, String ans1, String ans2, String ans3) {
		this.username = user;
		this.question1 = q1;
		this.question2 = q2;
		this.question3 = q3;
		this.answer1 = ans1;
		this.answer2 = ans2;
		this.answer3 = ans3;
	}

	public void setName(String nm) {
		this.fullName = nm;
	}
	
	public String getName() {
		return fullName;
	}

	public void setUsername(String user) {
		this.username = user;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String p) {
		this.password = p;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setDOB(String birth) {
		this.dob = birth;
	}
	
	public String getDOB() {
		return dob;
	}
	
	public void setAddress(String ad) {
		this.address = ad;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setSSN(String ss) {
		this.ssn = ss;
	}
	
	public String getSSN() {
		return ssn;
	}
	
	public void setEmail(String e) {
		this.email = e;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setQuestions(String q1, String q2, String q3) {
		this.question1 = q1;
		this.question2 = q2;
		this.question3 = q3;
	}
	
	public String getQuestion1() {
		return question1;
	}
	
	public String getQuestion2() {
		return question2;
	}
	
	public String getQuestion3() {
		return question3;
	}
	
	public void setAnswers(String ans, String ans2, String ans3) {		
		this.answer1 = ans;
		this.answer2 = ans2;
		this.answer3 = ans3;
	}
	
	public String getAnswer1() {
		return answer1;
	}
	
	public String getAnswer2() {
		return answer2;
	}
	
	public String getAnswer3() {
		return answer3;
	}
}
