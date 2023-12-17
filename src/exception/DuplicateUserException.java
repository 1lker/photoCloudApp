package exception;

import logging.Logger;
/**
 * Exception thrown when a duplicate user is encountered.
 */
public class DuplicateUserException extends Exception {
    /**
     * Constructs a new DuplicateUserException with a default error message.
     * The error message is logged using the Logger.
     */
	public DuplicateUserException() {
		super("A user with the same nickname already exists.");
		Logger.LogError(getLocalizedMessage());
	}
}
