package exception;

import logging.Logger;

public class PhotoNotFoundException extends PhotoCloudException{
	public PhotoNotFoundException(String photoId) {
		super("Photo not found: " + photoId);
		Logger.LogError(getLocalizedMessage());
	}
}
