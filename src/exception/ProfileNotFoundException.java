package exception;

import logging.Logger;

public class ProfileNotFoundException extends PhotoCloudException{
	public ProfileNotFoundException(String nickname) {
		super("Profile not found for user: " + nickname);
		Logger.LogError(getLocalizedMessage());
	}
}
