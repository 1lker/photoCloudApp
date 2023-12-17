package exception;

import logging.Logger;
/**
 * Exception thrown when a filter is not supported.
 */
public class FilterNotSupportedException extends PhotoCloudException{
    /**
     * Constructs a new FilterNotSupportedException with the specified filter name.
     * The error message is logged using the Logger.
     *
     * @param filterName The name of the unsupported filter.
     */
	public FilterNotSupportedException(String filterName) {
		super("Filter not supported: " + filterName);
		Logger.LogError(getLocalizedMessage());
	}
}
