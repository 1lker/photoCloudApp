package exception;

import logging.Logger;

public class InsufficientStorageException extends PhotoCloudException{
	public InsufficientStorageException() {
		super("Insufficient storage space.");
		Logger.LogError(getLocalizedMessage());
	}
}
