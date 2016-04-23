package fr.oan.translate.exception;

public class TranslateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TranslateExceptionCode code;
	private String messageToDisplay;
	private Throwable cause;

	public TranslateException() {
		super();
	}

	public TranslateException(TranslateExceptionCode code) {
		super();
		this.code = code;
	}

	public TranslateException(TranslateExceptionCode code, Throwable cause) {
		this.setCode(code);
		this.setCause(cause);
	}

	public TranslateException(TranslateExceptionCode code,
			String messageToDisplay) {
		this.code = code;
		this.messageToDisplay = messageToDisplay;
	}

	public TranslateExceptionCode getCode() {
		return code;
	}

	public void setCode(TranslateExceptionCode code) {
		this.code = code;
	}

	public String getMessageToDisplay() {
		return messageToDisplay;
	}

	public void setMessageToDisplay(String messageToDisplay) {
		this.messageToDisplay = messageToDisplay;
	}

	@Override
	public String getMessage() {
		return this.code.toString();
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

}
