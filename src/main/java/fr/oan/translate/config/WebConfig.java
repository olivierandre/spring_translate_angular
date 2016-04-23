package fr.oan.translate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import fr.oan.translate.config.settings.OriginSettings;

@Configuration
@EnableConfigurationProperties(OriginSettings.class)
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private OriginSettings originSettings;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(System.getenv(originSettings.getUrl())).allowedMethods("GET", "POST")
				.allowedHeaders("Origin", "x-requested-with", "X-Auth-Token", "I18n", "Content-Type").maxAge(3600);

		registry.addMapping("/api/secure/**").allowedOrigins(System.getenv(originSettings.getUrl()))
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("Origin", "x-requested-with", "X-Auth-Token", "I18n", "Content-Type")
				.allowCredentials(false).maxAge(3600);
	}
}