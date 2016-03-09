package so.pickme.common.exception;

public class XhrcException extends Exception {
	private static final long serialVersionUID = 1L;

	private String errorCode;

	public XhrcException() {
		super();
	}

	public XhrcException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public XhrcException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public XhrcException(String message, Throwable cause) {
		super(message, cause);
	}

	public XhrcException(String message) {
		super(message);
	}

	public XhrcException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * 
	 * @return 
	 */
	public String getCausedByMessage() {
		Throwable nestedException = getCause();
		return new StringBuilder().append(getMessage()).append(" nested exception is ")
				.append(nestedException.getClass().getName()).append(":").append(nestedException.getMessage())
				.toString();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
