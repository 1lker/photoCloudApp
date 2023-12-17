package exception;

import logging.Logger;

public class InsufficientAccessException extends Exception {
	public InsufficientAccessException() {
		super("Insufficient access privileges.");
		Logger.LogError(getLocalizedMessage());
	}
}
