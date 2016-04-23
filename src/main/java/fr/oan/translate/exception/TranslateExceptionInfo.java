package fr.oan.translate.exception;

public class TranslateExceptionInfo {
	public final String url;
	public final String message;

	public TranslateExceptionInfo(String url, Exception message) {
		this.url = url;
		this.message = message.getMessage();
	}
}
