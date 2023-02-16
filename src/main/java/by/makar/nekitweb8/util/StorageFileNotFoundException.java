package by.makar.nekitweb8.util;

public class StorageFileNotFoundException extends Exception{
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
