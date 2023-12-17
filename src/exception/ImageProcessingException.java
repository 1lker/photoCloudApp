package exception;

import logging.Logger;

public class ImageProcessingException extends Exception {
	public ImageProcessingException(String message) {
		super("Error processing image: " + message);
		Logger.LogError(getLocalizedMessage());
	}
}
