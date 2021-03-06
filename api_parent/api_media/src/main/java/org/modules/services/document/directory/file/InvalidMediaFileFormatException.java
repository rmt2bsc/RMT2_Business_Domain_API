package org.modules.services.document.directory.file;

import com.InvalidDataException;

/**
 * An exception thrown when the format of the source data file's filename is
 * incorrect.
 * 
 * @author appdev
 * 
 */
public class InvalidMediaFileFormatException extends InvalidDataException {

    private static final long serialVersionUID = -2249933179263928838L;

    /**
     * 
     */
    public InvalidMediaFileFormatException() {
        return;
    }

    /**
     * @param msg
     */
    public InvalidMediaFileFormatException(String msg) {
        super(msg);
        return;
    }

    /**
     * @param e
     */
    public InvalidMediaFileFormatException(Exception e) {
        super(e);
        return;
    }

    /**
     * @param msg
     * @param cause
     */
    public InvalidMediaFileFormatException(String msg, Throwable cause) {
        super(msg, cause);
        return;
    }

}
