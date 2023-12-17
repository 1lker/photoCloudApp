package user;

import java.util.List;

import logging.Logger;

import java.util.*;

public class LoginManager {
	private static LoginManager loginManagerInstance;
	private User currentUser;
	private List<User> users;
	
	private LoginManager() {
		this.users = new ArrayList<>();
	}
	
	
	////////// BU KISMI ANLATACAĞIM DAHA SONRA
	public static LoginManager getInstance() {
		if ( loginManagerInstance  == null ) {
			loginManagerInstance = new LoginManager();
		}
		
		return loginManagerInstance;
	}
	
	// Who is the current user 
	public User getCurrentUser() {
		return currentUser;
	}
	
	public boolean login(String nickname, String password) {
		// Check if the user is already logged in:
		if (currentUser != null) {
			Logger.LogInfo("Already logged in as " + currentUser.getNickname());
			return false;
		}
		
		// Find the user with the matching nickname:
		User user = findUserByNickname(nickname);
		
		// Check if the user exists and the password is correct
		if (user != null && user.getPassword().equals(password)) {
			currentUser = user;
			Logger.LogInfo("Logged in as " + user.getNickname());
			return true;
		}
		
		// Log it to error file
		Logger.LogError("Invalid login credentials. ");
		return false;
	}
	
	
	public void logout() {
		if (currentUser != null) {
			Logger.LogInfo("Logged out.");
			currentUser = null;
		} else {
			Logger.LogError("Not logged in.");
		}
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	private User findUserByNickname(String nickname) {
		for (User user : users) {
			if (user.getNickname().equals(nickname) ) {
				return user;
			}
		}
		
		return null;
	}
	
	
}
