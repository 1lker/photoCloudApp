package exception;

import logging.Logger;

public class InvalidLoginException extends Exception {
	public InvalidLoginException() {
		super("Invalid login credentials.");
		Logger.LogError(getLocalizedMessage());
	}
}
