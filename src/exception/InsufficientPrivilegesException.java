package exception;

import logging.Logger;

public class InsufficientPrivilegesException extends PhotoCloudException{
	public InsufficientPrivilegesException(String action) {
        super("Insufficient privileges to perform action: " + action);
        Logger.LogError(getLocalizedMessage());
	}
}
