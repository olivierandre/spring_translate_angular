package fr.oan.translate.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import fr.oan.translate.config.settings.LanguageSettings;

@Configuration
@EnableConfigurationProperties(LanguageSettings.class)
public class TranslateService {

	@Autowired
	private LanguageSettings languageSettings;

	public Locale[] getLanguageProperties() {

		String primary = languageSettings.getPrimary();
		String secondary = languageSettings.getSecondary();

		Locale[] supportedLocales = { new Locale(primary),
				new Locale(secondary) };

		return supportedLocales;
	}

	public Map<String, String> getMessages(String lang) {
		Locale locale = new Locale(lang);
		Map<String, String> listTranslates = new HashMap<>();

		ResourceBundle labels = ResourceBundle.getBundle("translate", locale);
		Enumeration<?> bundleKeys = labels.getKeys();

		while (bundleKeys.hasMoreElements()) {
			String key = (String) bundleKeys.nextElement();
			String value = labels.getString(key);
			listTranslates.put(key, value);
		}

		return listTranslates;

	}

	public Map<String, String> getLanguages() {
		Locale[] supportedLocales = getLanguageProperties();
		Map<String, String> listTranslates = new HashMap<>();

		for (int i = 0; i < supportedLocales.length; i++) {
			ResourceBundle labels = ResourceBundle.getBundle("translate",
					supportedLocales[i]);
			Enumeration<?> bundleKeys = labels.getKeys();

			while (bundleKeys.hasMoreElements()) {
				String key = (String) bundleKeys.nextElement();
				String value = labels.getString(key);
				listTranslates.put(key, value);
			}

			// listTranslates.add(locale);
		}

		return listTranslates;

	}
}
