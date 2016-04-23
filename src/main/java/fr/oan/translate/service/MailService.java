package fr.oan.translate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

import fr.oan.translate.config.settings.MailSettings;
import fr.oan.translate.exception.TranslateException;
import fr.oan.translate.exception.TranslateExceptionCode;

@Service
@EnableConfigurationProperties(MailSettings.class)
public class MailService {

	@Autowired
	private MailSettings mailSettings;

	private SendGrid sendGrid;

	public void init(SendGrid sendGrid) {
		this.sendGrid = sendGrid;
	}

	public void send(String emailAddress, String subject) throws TranslateException {
		try {
			Email email = new Email();
			email.setFrom(mailSettings.getFrom());
			email.setFromName(mailSettings.getFromName());
			email.addTo(emailAddress);
			email.setSubject(subject);
			email.setHtml("Changement de mot de passe");
			Response response = sendGrid.send(email);

			if (!response.getStatus()) {
				throw new SendGridException(new Exception("Erreur mail"));
			}

		} catch (SendGridException e) {
			throw new TranslateException(TranslateExceptionCode.MAIL_FAILED_SEND);
		}

	}
}
