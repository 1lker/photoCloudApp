package exception;

import logging.Logger;

public class InvalidEmailFormatException extends PhotoCloudException {
    public InvalidEmailFormatException(String email) {
        super("Invalid email format: " + email);
        Logger.LogError(getLocalizedMessage());
    }
}
