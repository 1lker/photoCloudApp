package exception;

import logging.Logger;

public class InvalidUserException extends PhotoCloudException{
	 	public InvalidUserException(String message) {
	 		super("Invalid user: " + message);
	 		Logger.LogError(getLocalizedMessage());
	 	}
}
