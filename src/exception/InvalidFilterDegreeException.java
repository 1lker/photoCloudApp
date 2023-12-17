package exception;

import logging.Logger;

public class InvalidFilterDegreeException extends Exception{
	public InvalidFilterDegreeException() {
		super("Invalid filter degree value.");
		Logger.LogError(getLocalizedMessage());
	}
}
