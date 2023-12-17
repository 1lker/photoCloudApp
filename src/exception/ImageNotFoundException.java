package exception;

import logging.Logger;
/**
 * Exception thrown when an image is not found.
 */
public class ImageNotFoundException extends PhotoCloudException {
    /**
     * Constructs a new ImageNotFoundException with the specified image name.
     * The error message is logged using the Logger.
     *
     * @param imageName The name of the image that was not found.
     */
	public ImageNotFoundException(String imageName) {
		super("Image not found: " + imageName);
		Logger.LogError(getLocalizedMessage());
	}
}
