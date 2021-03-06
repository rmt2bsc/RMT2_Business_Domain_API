package org.modules.timesheet;

import com.InvalidDataException;

/**
 * Handles exceptions regarding timesheet transmission data validation errors
 * 
 * @author Roy Terrell
 * 
 */
public class TimesheetTransmissionValidationException extends InvalidDataException {
    private static final long serialVersionUID = -8851874846044566159L;

    public TimesheetTransmissionValidationException() {
        super();
    }

    public TimesheetTransmissionValidationException(String msg) {
        super(msg);
    }

    public TimesheetTransmissionValidationException(Exception e) {
        super(e);
    }

    public TimesheetTransmissionValidationException(String msg, Throwable e) {
        super(msg, e);
    }
}
