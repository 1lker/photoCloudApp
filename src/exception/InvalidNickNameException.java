package exception;

import logging.Logger;

public class InvalidNickNameException extends PhotoCloudException {
	public InvalidNickNameException(String nickname) {
		super("Invalid nickname: " + nickname);
		Logger.LogError(getLocalizedMessage());
	}
}
