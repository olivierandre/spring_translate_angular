package fr.oan.translate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import fr.oan.translate.config.settings.MailSettings;
import fr.oan.translate.service.MailService;

@Configuration
@EnableConfigurationProperties(MailSettings.class)
public class MailConfiguration {

	@Autowired
	MailService mailService;

	@Autowired
	MailSettings mailSettings;

	@Bean
	public String initMailConfig() throws SendGridException {
		// SendGrid sendGrid = new SendGrid(mailSettings.getApiKey());
		SendGrid sendGrid = new SendGrid(mailSettings.getApiKey());

		mailService.init(sendGrid);
		mailService.send("olivier.andre77@gmail.com", "yo");

		System.out.println("**** Initi Bean ****");

		return "OK";
	}
}
