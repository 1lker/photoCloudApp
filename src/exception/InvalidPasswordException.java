package exception;

import logging.Logger;

public class InvalidPasswordException extends PhotoCloudException {
	public InvalidPasswordException() {
		super("Invalid password.");
		Logger.LogError(getLocalizedMessage());
	}
}
