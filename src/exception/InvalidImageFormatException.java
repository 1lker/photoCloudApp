package exception;

import logging.Logger;

public class InvalidImageFormatException extends PhotoCloudException {
	public InvalidImageFormatException(String imageName) {
		super("Invalid image format: " + imageName);
		Logger.LogError(getLocalizedMessage());
	}
}
