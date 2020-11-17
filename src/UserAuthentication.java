import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class UserAuthentication {

	private JFrame frame;
	private JPanel systemAccess;
	private JPanel userMenu;
	private JPanel userRegistration;
	private JPanel userLogin;
	private JPanel userRegistration2;
	private JPanel successfulLogin;
	private JPanel passwordReset;
	private JPanel securityQuestions;
	private JPanel adminSystem;
	private JPanel changePassword;
	private DbOps db;
	private User user;
	private Admin admin;
	private String resetUsername;
	private int randomIndex;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserAuthentication window = new UserAuthentication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserAuthentication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		systemAccess = new JPanel();
		frame.getContentPane().add(systemAccess, "name_76156688367700");
		systemAccess.setLayout(null);
		
		JLabel accountLbl = new JLabel("Choose your account type");
		accountLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		accountLbl.setHorizontalAlignment(SwingConstants.CENTER);
		accountLbl.setBounds(234, 113, 164, 14);
		systemAccess.add(accountLbl);
		
		JButton userBtn = new JButton("User");
		userBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		userBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				systemAccess.setVisible(false);
				userMenu.setVisible(true);
			}
		});
		userBtn.setBounds(271, 139, 89, 23);
		systemAccess.add(userBtn);
		
		JButton adminBtn = new JButton("Admin");
		adminBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		adminBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				systemAccess.setVisible(false);
				adminSystem.setVisible(true);
			}
		});
		adminBtn.setBounds(271, 173, 89, 23);
		systemAccess.add(adminBtn);
		
		user = new User();
		db = new DbOps();
		
		userMenuPanel();
		userRegistrationPanel();
		userLoginPanel();
		userRegistration2Panel();
		successfulLoginPanel();
		passwordResetPanel();
		adminSystemPanel();
		changePasswordPanel();
	}
	
	// Creates user menu panel
	void userMenuPanel() {
		userMenu = new JPanel();
		frame.getContentPane().add(userMenu, "name_77164554091800");
		userMenu.setLayout(null);
		
		JLabel actionLbl = new JLabel("Choose one of the following");
		actionLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
		actionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		actionLbl.setBounds(210, 96, 221, 19);
		userMenu.add(actionLbl);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userMenu.setVisible(false);
				userRegistration.setVisible(true);
			}
		});
		registerBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
		registerBtn.setBounds(257, 124, 131, 28);
		userMenu.add(registerBtn);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userMenu.setVisible(false);
				userLogin.setVisible(true);
			}
		});
		loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
		loginBtn.setBounds(257, 164, 131, 28);
		userMenu.add(loginBtn);
		
		JButton changePasswordBtn = new JButton("Change Password");
		changePasswordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userMenu.setVisible(false);
				changePassword.setVisible(true);
			}
		});
		changePasswordBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
		changePasswordBtn.setBounds(257, 205, 131, 28);
		userMenu.add(changePasswordBtn);
		
		backAction(userMenu, systemAccess);
	}
	
	// Creates admin view panel
	void adminSystemPanel() {
		adminSystem = new JPanel();
		frame.getContentPane().add(adminSystem, "name_187525165331300");
		adminSystem.setLayout(new BorderLayout());
		adminSystem.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Password Reset Requests", TitledBorder.CENTER, TitledBorder.TOP));
		
		String [] header = {"Username", "Date Submitted"};
		List<Admin> requests = db.getPendingRequests();
		int n = requests.size();
		String [][] records = new String[n][2];
		
		for (int i = 0; i < requests.size(); i++) {
			for (int j = 0; j < 1; j++) {
				records[i][0] = requests.get(i).getUsername();
				records[i][1] = requests.get(i).getDateSubmitted();
			}
		}
		
		// Displays password reset requests that have not been approved
		JTable table = new JTable(records, header);
		adminSystem.add(new JScrollPane(table), BorderLayout.CENTER);
		
		table.setRowSelectionAllowed(true);
		
		JButton select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				
				String user = (String) table.getModel().getValueAt(row, column);
				db.updateAdminRequests(user, 1, 0);
				
				showMessage(user + "'s password reset has been approved", "Request Approved", "success");
			}
		});
		JPanel command = new JPanel(new FlowLayout());
		command.add(select);
		adminSystem.add(command, BorderLayout.SOUTH);
	}
	
	// Creates user registration panel
	void userRegistrationPanel() {
		userRegistration = new JPanel();
		frame.getContentPane().add(userRegistration, "name_78121851363500");
		userRegistration.setLayout(null);
		
		JLabel welcomeLbl = new JLabel("Welcome!");
		welcomeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
		welcomeLbl.setBounds(26, 58, 85, 16);
		userRegistration.add(welcomeLbl);
		
		JLabel welcomeLbl2 = new JLabel("Sign Up Here");
		welcomeLbl2.setFont(new Font("SansSerif", Font.PLAIN, 11));
		welcomeLbl2.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLbl2.setBounds(26, 77, 75, 16);
		userRegistration.add(welcomeLbl2);
		
		JLabel imgLbl = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("img/welcome-icon.png")).getImage();
		imgLbl.setIcon(new ImageIcon(img));
		imgLbl.setBounds(26, 102, 96, 84);
		userRegistration.add(imgLbl);
		
		JLabel firstNameLbl = new JLabel("First Name");
		firstNameLbl.setBounds(190, 6, 63, 16);
		userRegistration.add(firstNameLbl);
		
		JTextField firstNameTextField = new JTextField();
		firstNameTextField.setBounds(190, 24, 162, 28);
		userRegistration.add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		JTextField lastNameTextField = new JTextField();
		lastNameTextField.setBounds(420, 24, 158, 28);
		userRegistration.add(lastNameTextField);
		lastNameTextField.setColumns(10);
		
		JLabel lastNameLbl = new JLabel("Last Name");
		lastNameLbl.setBounds(420, 6, 68, 16);
		userRegistration.add(lastNameLbl);
		
		JLabel addressLbl = new JLabel("Address");
		addressLbl.setBounds(190, 58, 55, 16);
		userRegistration.add(addressLbl);
		
		JTextField addressTextField = new JTextField();
		addressTextField.setBounds(190, 79, 162, 28);
		userRegistration.add(addressTextField);
		addressTextField.setColumns(10);
		
		JLabel dobField = new JLabel("Date of Birth (MM/DD/YYYY)");
		dobField.setBounds(190, 119, 162, 16);
		userRegistration.add(dobField);
		
		JTextField dobTextField = new JTextField();
		dobTextField.setBounds(190, 136, 162, 28);
		userRegistration.add(dobTextField);
		dobTextField.setColumns(10);
		
		JLabel ssnLbl = new JLabel("Last 4 of SSN");
		ssnLbl.setBounds(190, 187, 75, 16);
		userRegistration.add(ssnLbl);
		
		JTextField ssnTextField = new JTextField();
		ssnTextField.setBounds(190, 205, 162, 28);
		userRegistration.add(ssnTextField);
		ssnTextField.setColumns(10);
		
		JLabel emailLbl = new JLabel("Email");
		emailLbl.setBounds(190, 245, 55, 16);
		userRegistration.add(emailLbl);
		
		JTextField emailTextField = new JTextField();
		emailTextField.setBounds(190, 262, 162, 28);
		userRegistration.add(emailTextField);
		emailTextField.setColumns(10);
		
		JLabel zipLbl = new JLabel("Zip Code");
		zipLbl.setBounds(420, 60, 55, 16);
		userRegistration.add(zipLbl);
		
		JTextField zipTextField = new JTextField();
		zipTextField.setBounds(420, 79, 158, 28);
		userRegistration.add(zipTextField);
		zipTextField.setColumns(10);
		
		JButton continueBtn = new JButton("Continue");
		continueBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = firstNameTextField.getText();
				String lastName = lastNameTextField.getText();
				String address = addressTextField.getText();
				String dob = dobTextField.getText();
				String ssn = ssnTextField.getText();
				String zipCode = zipTextField.getText();
				String email = emailTextField.getText();
				
				if (validUserInfo(firstName, lastName, address, zipCode, ssn) && validDOB(dob) && emailValidation(email)) {
					user.setName(firstName + " " + lastName);
					user.setAddress(address + " " + zipCode);
					user.setDOB(dob);
					user.setEmail(email);
					user.setSSN(ssn);
					
					userRegistration.setVisible(false);
					userRegistration2.setVisible(true);
				}
			}
		});
		continueBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
		continueBtn.setBounds(420, 262, 90, 28);
		userRegistration.add(continueBtn);
		
		backAction(userRegistration, userMenu);
	}
	
	// Creates continued user registration panel
	void userRegistration2Panel() {
		userRegistration2 = new JPanel();
		frame.getContentPane().add(userRegistration2, "name_82498047962800");
		userRegistration2.setLayout(null);
		
		JLabel createLbl = new JLabel("Create your username and password");
		createLbl.setBounds(6, 35, 227, 16);
		userRegistration2.add(createLbl);
		
		JLabel signUpLbl = new JLabel("Sign Up Below");
		signUpLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
		signUpLbl.setBounds(6, 13, 114, 22);
		userRegistration2.add(signUpLbl);
		
		JTextField usernameTextField = new JTextField();
		usernameTextField.setBounds(74, 75, 156, 28);
		userRegistration2.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel usernameLbl = new JLabel("Username:");
		usernameLbl.setBounds(6, 81, 68, 16);
		userRegistration2.add(usernameLbl);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(74, 115, 156, 28);
		// Masks password entry
		passwordField.setEchoChar('\u25CF');
		userRegistration2.add(passwordField);
		
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setBounds(6, 121, 68, 16);
		userRegistration2.add(passwordLbl);
		
		JLabel question1Lbl = new JLabel("Question 1:");
		question1Lbl.setHorizontalAlignment(SwingConstants.LEFT);
		question1Lbl.setBounds(276, 68, 79, 16);
		userRegistration2.add(question1Lbl);
		
		JLabel question2Lbl = new JLabel("Question 2:");
		question2Lbl.setHorizontalAlignment(SwingConstants.LEFT);
		question2Lbl.setBounds(276, 146, 79, 16);
		userRegistration2.add(question2Lbl);
		
		JLabel question3Lbl = new JLabel("Question 3:");
		question3Lbl.setHorizontalAlignment(SwingConstants.LEFT);
		question3Lbl.setBounds(276, 228, 79, 16);
		userRegistration2.add(question3Lbl);
		
		// Creates question combo boxes
		JComboBox<String> q1ComboBox = new JComboBox<String>();
		q1ComboBox.setBounds(359, 63, 269, 26);
		userRegistration2.add(q1ComboBox);
		db.fillComboBox(q1ComboBox);
		
		JComboBox<String> q2ComboBox = new JComboBox<String>();
		q2ComboBox.setBounds(359, 141, 269, 26);
		userRegistration2.add(q2ComboBox);
		db.fillComboBox(q2ComboBox);
		
		JComboBox<String> q3ComboBox = new JComboBox<String>();
		q3ComboBox.setBounds(359, 223, 269, 26);
		userRegistration2.add(q3ComboBox);
		db.fillComboBox(q3ComboBox);
		
		JTextField answerTextField1 = new JTextField();
		answerTextField1.setBounds(359, 101, 269, 28);
		userRegistration2.add(answerTextField1);
		answerTextField1.setColumns(10);
		
		JTextField answerTextField2 = new JTextField();
		answerTextField2.setBounds(359, 179, 269, 28);
		userRegistration2.add(answerTextField2);
		answerTextField2.setColumns(10);
		
		JTextField answerTextField3 = new JTextField();
		answerTextField3.setColumns(10);
		answerTextField3.setBounds(359, 261, 269, 28);
		userRegistration2.add(answerTextField3);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q1 = q1ComboBox.getSelectedItem().toString();
				String q2 = q2ComboBox.getSelectedItem().toString();
				String q3 = q3ComboBox.getSelectedItem().toString();
				String ans1 = answerTextField1.getText();
				String ans2 = answerTextField2.getText();
				String ans3 = answerTextField3.getText();
				
				
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				
				if (q1.equals(q2) || q1.equals(q3) || q2.equals(q3)) {
					showMessage("You must select three different security questions.", "Invalid Security Questions", "error");
				}
				else if (usernameCheck(username, true) && passwordValidation(password)) {
					user.setQuestions(q1, q2, q3);
					user.setAnswers(ans1, ans2, ans3);
					user.setUsername(username);
					user.setPassword(password);
					
					db.submitUserInfo(user);
					
					showMessage("You have successfully registered", "Successful Registration", "success");
				}
			}
		});
		submitBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		submitBtn.setBounds(143, 155, 90, 28);
		userRegistration2.add(submitBtn);
		
		JLabel showLbl = new JLabel("");
		showLbl.setHorizontalAlignment(SwingConstants.CENTER);
		showLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				passwordField.setEchoChar((char) 0);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				passwordField.setEchoChar('\u25cf');
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("img/eye-icon.png")).getImage();
		showLbl.setIcon(new ImageIcon(img));
		showLbl.setBounds(230, 121, 34, 16);
		userRegistration2.add(showLbl);
		
		JLabel selectLbl = new JLabel("Select three security questions below");
		selectLbl.setBounds(359, 35, 219, 16);
		userRegistration2.add(selectLbl);
		
		passRequirementLabels(userRegistration2);
	}
	
	// Creates successful login panel
	void successfulLoginPanel() {
		successfulLogin = new JPanel();
		frame.getContentPane().add(successfulLogin, "name_166476075339800");
		successfulLogin.setLayout(null);
		
		JLabel successLbl = new JLabel("Your login was successful");
		successLbl.setFont(new Font("SansSerif", Font.BOLD, 15));
		successLbl.setBounds(224, 47, 190, 20);
		successfulLogin.add(successLbl);
		
		JLabel successImg = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("img/success-icon.png")).getImage();
		successImg.setIcon(new ImageIcon(img));
		successImg.setBounds(286, 79, 71, 64);
		successfulLogin.add(successImg);
		
		JLabel detailsLbl = new JLabel("<html><u>Details</u></html>");
		detailsLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		detailsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		detailsLbl.setBounds(286, 155, 55, 16);
		successfulLogin.add(detailsLbl);
		
		JLabel detailsLbl2 = new JLabel("The purpose of this system is to demonstrate user authentication");
		detailsLbl2.setBounds(131, 183, 356, 16);
		successfulLogin.add(detailsLbl2);
		
		JLabel detailsLbl3 = new JLabel("Programmer: Adonis Davis");
		detailsLbl3.setBounds(230, 199, 164, 16);
		successfulLogin.add(detailsLbl3);
	}
	
	// Creates user login panel
	void userLoginPanel() {
		userLogin = new JPanel();
		frame.getContentPane().add(userLogin, "name_81420001617900");
		userLogin.setLayout(null);
		
		JLabel loginLbl = new JLabel("Log in with your username and password");
		loginLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		loginLbl.setBounds(207, 96, 240, 16);
		userLogin.add(loginLbl);
		
		JTextField usernameTextField = new JTextField();
		usernameTextField.setBounds(237, 124, 210, 28);
		userLogin.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel usernameLbl = new JLabel("Username:");
		usernameLbl.setBounds(157, 130, 68, 16);
		userLogin.add(usernameLbl);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(237, 164, 210, 28);
		// Masks password entry
		passwordField.setEchoChar('\u25CF');
		userLogin.add(passwordField);
		
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setBounds(157, 170, 68, 16);
		userLogin.add(passwordLbl);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				
				if (usernameCheck(username, false) && accountValidation(username, password)) {
					userLogin.setVisible(false);
					successfulLogin.setVisible(true);
				}
			}
		});
		loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
		loginBtn.setBounds(325, 205, 122, 28);
		userLogin.add(loginBtn);
		
		JButton resetPasswordBtn = new JButton("Reset Password");
		resetPasswordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userLogin.setVisible(false);
				passwordReset.setVisible(true);
			}
		});
		resetPasswordBtn.setFont(new Font("SansSerif", Font.BOLD, 10));
		resetPasswordBtn.setBounds(325, 245, 122, 28);
		userLogin.add(resetPasswordBtn);
		
		JLabel showLbl = new JLabel("");
		showLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				passwordField.setEchoChar((char) 0);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				passwordField.setEchoChar('\u25cf');
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("img/eye-icon.png")).getImage();
		showLbl.setIcon(new ImageIcon(img));
		showLbl.setBounds(459, 170, 34, 16);
		userLogin.add(showLbl);
		
		backAction(userLogin, userMenu);
	}
	
	// Creates the password reset panel
	void passwordResetPanel() {
		passwordReset = new JPanel();
		frame.getContentPane().add(passwordReset, "name_170012678729900");
		passwordReset.setLayout(null);
		
		JLabel enterLbl = new JLabel("Enter your username to request a password reset");
		enterLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
		enterLbl.setBounds(165, 113, 287, 16);
		passwordReset.add(enterLbl);
		
		JLabel usernameLbl = new JLabel("Username:");
		usernameLbl.setBounds(165, 141, 75, 16);
		passwordReset.add(usernameLbl);
		
		JTextField usernameTextField = new JTextField();
		usernameTextField.setBounds(231, 135, 182, 28);
		passwordReset.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JButton Continue = new JButton("Continue");
		Continue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetUsername = usernameTextField.getText();
				
				securityQuestionsPanel();
				
				if (usernameCheck(resetUsername, false)) {
					passwordReset.setVisible(false);
					securityQuestions.setVisible(true);
				}
			}
		});
		Continue.setBounds(323, 175, 90, 28);
		passwordReset.add(Continue);
		
		backAction(passwordReset, userLogin);
	}
	
	// Creates the security questions panel
	void securityQuestionsPanel() {
		securityQuestions = new JPanel();
		frame.getContentPane().add(securityQuestions, "name_171195592334600");
		securityQuestions.setLayout(null);
		
		JLabel answerLbl = new JLabel("Answer the security question");
		answerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		answerLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
		answerLbl.setBounds(212, 103, 211, 16);
		securityQuestions.add(answerLbl);
	
		String question = generateQuestion(resetUsername);
		
		JComboBox<String> qComboBox = new JComboBox<String>();
		qComboBox.setBounds(154, 131, 346, 26);
		securityQuestions.add(qComboBox);
		qComboBox.addItem(question);
		
		JLabel questionLbl = new JLabel("Question:");
		questionLbl.setBounds(76, 136, 66, 16);
		securityQuestions.add(questionLbl);
		
		JTextField answerTextField = new JTextField();
		answerTextField.setBounds(154, 169, 346, 28);
		securityQuestions.add(answerTextField);
		answerTextField.setColumns(10);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String answer = answerTextField.getText();
				
				if (matchAnswer(resetUsername, answer)) {
					Date dNow = new Date();
					SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyy");
					String date = dt.format(dNow);
					
					admin = new Admin(resetUsername, date, 0, 1);
					db.submitRequests(admin);
					
					showMessage("Your request to reset your password has been sent", "Request Sent", "success");
				}
				else {
					showMessage("You have entered an incorrect answer to the security question", "Invalid Answer", "error");
				}
			}
		});
		submitBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		submitBtn.setBounds(410, 208, 90, 28);
		securityQuestions.add(submitBtn);
		
		backAction(securityQuestions, passwordReset);
	}
	
	// Creates change password panel
	void changePasswordPanel() {
		changePassword = new JPanel();
		frame.getContentPane().add(changePassword, "name_241887884596400");
		changePassword.setLayout(null);
		
		JLabel enterLbl = new JLabel("Enter your username and newly-selected password");
		enterLbl.setHorizontalAlignment(SwingConstants.CENTER);
		enterLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
		enterLbl.setBounds(140, 100, 345, 16);
		changePassword.add(enterLbl);
		
		JTextField usernameTextField = new JTextField();
		usernameTextField.setBounds(209, 128, 196, 28);
		changePassword.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		// Masks password entry
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(209, 168, 196, 28);
		passwordField.setEchoChar('\u25cf');
		changePassword.add(passwordField);
		
		JLabel usernameLbl = new JLabel("Username:");
		usernameLbl.setBounds(124, 134, 73, 16);
		changePassword.add(usernameLbl);
		
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setBounds(124, 174, 73, 16);
		changePassword.add(passwordLbl);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameTextField.getText();
				String password = new String(passwordField.getPassword());
				
				if (allowReset(username)) {
					db.updatePassword(username, password);
					db.updateAdminRequests(username, 0, 0);
					
					showMessage("Your password has been reset.", "Password Reset", "success");
				}
			}
		});
		submitBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		submitBtn.setBounds(315, 208, 90, 28);
		changePassword.add(submitBtn);
		
		JLabel showLbl = new JLabel("");
		showLbl.setHorizontalAlignment(SwingConstants.CENTER);
		showLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				passwordField.setEchoChar((char) 0);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				passwordField.setEchoChar('\u25cf');
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("img/eye-icon.png")).getImage();
		showLbl.setIcon(new ImageIcon(img));
		showLbl.setBounds(407, 174, 34, 16);
		changePassword.add(showLbl);
		
		passRequirementLabels(changePassword);
		
		backAction(changePassword, userMenu);
		
	}
	
	// Creates back button
	void backAction(JPanel panel1, JPanel panel2) {
		JLabel backLbl = new JLabel("");
		backLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel1.setVisible(false);
				panel2.setVisible(true);
			}
		});
		Image img2 = new ImageIcon(this.getClass().getResource("img/back-icon.png")).getImage();
		backLbl.setIcon(new ImageIcon(img2));
		backLbl.setBounds(6, 6, 55, 28);
		panel1.add(backLbl);
	}
	
	// Creates success or error messages
	void showMessage(String msg, String title, String msgType) {
		JOptionPane optionPane;
		
		if (msgType.equals("error"))
			optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
		else
			optionPane = new JOptionPane(msg, JOptionPane.PLAIN_MESSAGE);
		
		JDialog dialog = optionPane.createDialog(title);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
	
	// Displays security requirements for password entry
	void passRequirementLabels(JPanel panel) {
	    JLabel requirementsLbl = new JLabel("<html><u>Requirements</u></html>");
	    requirementsLbl.setFont(new Font("SansSerif", 1, 10));
	    requirementsLbl.setBounds(10, 220, 161, 16);
	    panel.add(requirementsLbl);
	    
	    JLabel reqLbl1 = new JLabel("Minimum of 12 characters");
	    reqLbl1.setFont(new Font("SansSerif", 0, 10));
	    reqLbl1.setBounds(6, 239, 161, 16);
	    panel.add(reqLbl1);
	    
	    JLabel reqLbl2 = new JLabel("At least one uppercase letter");
	    reqLbl2.setFont(new Font("SansSerif", 0, 10));
	    reqLbl2.setBounds(6, 257, 161, 16);
	    panel.add(reqLbl2);
	    
	    JLabel reqLbl3 = new JLabel("At least one lowercase letter");
	    reqLbl3.setFont(new Font("SansSerif", 0, 10));
	    reqLbl3.setBounds(6, 277, 161, 16);
	    panel.add(reqLbl3);
	    
	    JLabel reqLbl4 = new JLabel("At least one digit");
	    reqLbl4.setFont(new Font("SansSerif", 0, 10));
	    reqLbl4.setBounds(6, 292, 149, 16);
	    panel.add(reqLbl4);
	    
	    JLabel reqLbl5 = new JLabel("At least one special character");
	    reqLbl5.setFont(new Font("SansSerif", 0, 10));
	    reqLbl5.setBounds(6, 307, 182, 16);
	    panel.add(reqLbl5);
	  }
	
	//evaluates if string contains only valid name characters
	boolean validName(String entry) {
		boolean validInput = true;
		
		for (int i = 0; i < entry.length(); i++) {
			if (!Character.isAlphabetic(entry.charAt(i)) && entry.charAt(i) != '-') {
				validInput = false;
				break;
			}
		}
		
		return validInput;
	}
	
	//evaluates if string contains any only numerical characters
	boolean onlyDigits(String str) {
		boolean num = true;
		
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			
			if (!Character.isDigit(ch)) {
				num = false;
				break;
			}
		}
		
		return num;
	}
	
	// Ensures user enters valid information on user registration panel
	boolean validUserInfo(String firstName, String lastName, String address, String zip, String ssn) {
		if (firstName.equals("") || !validName(firstName)) {
			showMessage("You must enter a valid first name.", "Invalid First Name", "error");
			return false;
		}
		
		if (lastName.equals("") || !validName(lastName)) {
			showMessage("You must enter a valid last name.", "Invalid Last Name", "error");
			return false;
		}
		
		
		if (address.equals("") || !Character.isDigit(address.charAt(0))) {
			showMessage("You must enter a valid address.", "Invalid Address", "error");
			return false;
		}
		
		if (zip.equals("") || !onlyDigits(zip) || zip.length() != 5) {
			showMessage("You must enter a valid zip code.", "Invalid Zip Code", "error");
			return false;
		}
		
		if (ssn.equals("") || !onlyDigits(ssn) || ssn.length() != 4) {
			showMessage("You must enter the last 4 digits of your SSN.", "Invalid SSN", "error");
			return false;
		}
		
		return true;
	}
	
	// Evaluates if valid date of birth is entered
	boolean validDOB(String dob) {
		boolean valid = false;
		// Defines acceptable pattern for date of birth
		String regex = "^(0[1-9]|1[0-2])\\/(0[1-9]|[12][0-9]|3[01])\\/(19[2-9][0-9]|200[0-7])$";
		
		if (dob.matches(regex))
			valid = true;
		
		if (!valid)
			showMessage("Your date of birth is in an incorrect format.", "Invalid DOB", "error");
		
		return valid;
	}
	
	// Evaluates if valid email is entered
	boolean emailValidation(String email) {
		List<User> emails = db.getUserLoginDetails();
		boolean validEmail = false;
		
		//ensures an email address is entered
		if (!email.isEmpty())
			validEmail = true;
		
		//evaluates if chosen email is already present in the database
		for (User user: emails) {
			if (user.getEmail().equals(email)) {
				validEmail = false;
				break;
			}
		}
		
		if (!validEmail)
			showMessage("An account for this email already exists", "Invalid Email", "error");
		
		// Defines an acceptable pattern for an email address
		String regex = "^[a-zA-z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"
				+ "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		
		Pattern pattern = Pattern.compile(regex);
		
		if (!pattern.matcher(email).matches()) {
			validEmail = false;
			showMessage("You must enter a valid email address", "Invalid Email", "error");
		}
		
		return validEmail;
	}
	
	// Evaluates if chosen username is already present in the database
	boolean usernameCheck(String username, boolean register) {
		boolean userExists = false;
		boolean validUser = true;
		List<User> users = db.getUserLoginDetails();
		
		for (User user: users) {
			if (username.equals(user.getUsername())) {
				userExists = true;
				break;
			}
		}
		
		if (userExists && register) {
			validUser = false;
			showMessage("This username is already taken.", "Invalid Username", "error");
		}
		else if (!userExists && !register) {
			validUser = false;
			showMessage("This user does not exist.", "Invalid username", "error");
		}
			
		
		return validUser;
	}
	
	// Evaluates password chosen by user to ensure it meets security standards
	boolean passwordValidation(String password) {
		List<BannedPasswords> passes = db.getBannedPasswords();
		boolean validPass = false;
		
		// Defines acceptable pattern for password entry
		String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*?-]).{12,}$";
		
		Pattern pattern = Pattern.compile(regex);
		
		if (pattern.matcher(password).matches())
			validPass = true;
		
		for (BannedPasswords pass : passes) {
			if (password.contains(pass.getPassword())) {
				validPass = false;
				break;
			}
		}
		
		if (validPass == false)
			showMessage("This password does not meet security requirements", "Invalid password", "error");
		
		return validPass;
	}
	
	//checks if password matches entered username
	boolean accountValidation(String username, String password) {
		List<User> users = db.getUserLoginDetails();
		boolean validLogin = false;
		//user = new User();
		
		for (User user: users) {
			if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				validLogin = true;
				break;
			}
		}
		
		if (!validLogin)
			showMessage("You have entered an incorrect password.", "Invalid password", "error");
		
		return validLogin;
	}
	
	// Randomly generates a question from a list of security questions chosen by the user
	String generateQuestion(String username) {
		List<User> users = db.getSecurityQuestions();
		List<String> questions = new ArrayList<String>();
		
		//adds question to list based on username
		for (User user: users) {
			if (username.equals(user.getUsername())) {
				questions.add(user.getQuestion1());
				questions.add(user.getQuestion2());
				questions.add(user.getQuestion3());
				
				break;
			}
		}
		
		Random generator = new Random();		
		randomIndex = generator.nextInt(questions.size());
		
		return questions.get(randomIndex);			
	}
	
	// Determines if user's answer to security question matches database records
	boolean matchAnswer(String username, String answer) {
		List<User> users = db.getUserLoginDetails();
		List<String> answers = new ArrayList<String>();
		
		// Adds user's answers to a list
		for (User user : users) {
			if (username.equals(user.getUsername())) {
				answers.add(user.getAnswer1());
				answers.add(user.getAnswer2());
				answers.add(user.getAnswer3());
				
				break;
			}
		}
		
		if (answer.equals(answers.get(randomIndex)))
			return true;
		else
			return false;
	}
	
	// Evaluates if user's password reset requests has been approved
	boolean allowReset(String username) {
		List<Admin> requests = db.getPasswordRequests();
		boolean reset = false;
		
		for (Admin request : requests) {
			if (username.equals(request.getUsername()) && request.getApproval() == 1) {
				reset = true;
				break;
			}
		}
		
		if (!reset)
			showMessage("A password reset has not been approved for this account", "Invalid Reset", "error");
		
		return reset;
	}
}
