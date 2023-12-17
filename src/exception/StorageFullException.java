package exception;

import logging.Logger;

public class StorageFullException extends PhotoCloudException {
    public StorageFullException() {
        super("Storage capacity is full.");
        Logger.LogError(getLocalizedMessage());
    }
}
