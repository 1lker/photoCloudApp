package exception;

import logging.Logger;

public class InvalidFilterException extends PhotoCloudException{
	public InvalidFilterException(String filterName) {
		super("Invalid filter: " + filterName);
		Logger.LogError(getLocalizedMessage());
	}
}
