package exception;

import logging.Logger;

public class UnauthorizedAccessException extends PhotoCloudException{
	public UnauthorizedAccessException() {
        super("Unauthorized access.");
        Logger.LogError(getLocalizedMessage());
	}
}
