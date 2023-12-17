package exception;

import logging.Logger;

public class InvalidTierException extends PhotoCloudException{
	public InvalidTierException(String message) {
        super("Invalid tier: " + message);
        Logger.LogError(getLocalizedMessage());
	}
}
