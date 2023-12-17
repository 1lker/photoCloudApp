package exception;

import logging.Logger;

public class InvalidEmailException extends PhotoCloudException {
	public InvalidEmailException(String email) {
		super("Invalid email format: " + email);
		Logger.LogError(getLocalizedMessage());
	}
}
