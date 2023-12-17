package exception;

import logging.Logger;

public class UserNotFoundException extends PhotoCloudException {
	public UserNotFoundException(String nickname) {
		super("User not found: " + nickname);
		Logger.LogError(getLocalizedMessage());
	}
}
