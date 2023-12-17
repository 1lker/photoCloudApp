package exception;

import logging.Logger;

public class InvalidPhotoException extends PhotoCloudException{
	public InvalidPhotoException(String message) {
        super("Invalid photo: " + message);
        Logger.LogError(getLocalizedMessage());
	}
}
