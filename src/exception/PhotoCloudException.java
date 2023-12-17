package exception;

import logging.Logger;

public class PhotoCloudException extends Exception {
	public PhotoCloudException(String message) {
		super(message);
		Logger.LogError(getLocalizedMessage());
	}
}
