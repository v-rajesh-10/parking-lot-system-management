package com.parking.lot.system.management.exception;

/***
 * Custom Exception managing the possible error codes.
 * @author Rajesh Venkatesan
 */
public class ParkingException extends Exception {

    /**
     * The enumeration capturing the reason for failure.
     */
    public enum ReasonEnum
    {
        UNKNOWN,
        SLOT_NOT_CREATED,
        SLOT_NOT_AVAILABLE,
    }

    private ReasonEnum reasonCode = ReasonEnum.UNKNOWN;

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param reasonCode reasonCode the reason code represented by the {@link ReasonEnum}.
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParkingException(ReasonEnum reasonCode, String message) {
        super(message);
        this.reasonCode = reasonCode;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param reasonCode the reason code represented by the {@link ReasonEnum}.
     */
    public ParkingException(ReasonEnum reasonCode) {
        super();
        this.reasonCode = reasonCode;
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param reasonCode the reason code represented by the {@link ReasonEnum}.
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ParkingException(ReasonEnum reasonCode, Throwable cause) {
        super(cause);
        this.reasonCode = reasonCode;
    }

    /**
     * Fetch the reason code of the exception.
     * @return the reason code represented by the {@link ReasonEnum}.
     */
    public ReasonEnum getReasonCode() {
        return reasonCode;
    }
}
