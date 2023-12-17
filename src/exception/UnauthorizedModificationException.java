package exception;

import logging.Logger;

public class UnauthorizedModificationException extends PhotoCloudException{
	public UnauthorizedModificationException() {
		super("Unauthorized modification.");
		Logger.LogError(getLocalizedMessage());
	}
}
