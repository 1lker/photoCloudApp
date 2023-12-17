package exception;

import logging.Logger;

public class InvalidAgeException extends PhotoCloudException{
	public InvalidAgeException() {
        super("Invalid age: ");
        Logger.LogError(getLocalizedMessage());
	}
}
