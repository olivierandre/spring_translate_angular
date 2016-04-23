package fr.oan.translate.config;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import fr.oan.translate.config.settings.InitTranslateSettings;
import fr.oan.translate.domain.Category;
import fr.oan.translate.domain.GameDuration;
import fr.oan.translate.domain.Lang;
import fr.oan.translate.domain.Setting;
import fr.oan.translate.domain.User;
import fr.oan.translate.service.CategoryService;
import fr.oan.translate.service.LangService;
import fr.oan.translate.service.ScoreService;
import fr.oan.translate.service.SettingService;
import fr.oan.translate.service.UserService;
import fr.oan.translate.service.WordService;

@Configuration
@EnableConfigurationProperties(InitTranslateSettings.class)
public class InitTranslateConfiguration {

	@Autowired
	private InitTranslateSettings initSettings;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private UserService userService;

	@Autowired
	private WordService wordService;

	@Autowired
	private LangService langService;

	@Bean
	public String initTranslateConfig() throws JsonParseException, JsonMappingException, IOException {

		Long sizeCategory = categoryService.countCategory();

		if (langService.findAllLangs().isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			List<Lang> langs = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream("lang.json"),
					TypeFactory.defaultInstance().constructCollectionType(List.class, Lang.class));

			langService.saveLangs(langs);
		}

		if (initSettings.isWord()) {
			wordService.deleteAllWords();
		}

		long numberOfWords = wordService.count();
		boolean hasWords = numberOfWords == 0L ? false : true;

		if (sizeCategory == 0L || initSettings.isCategory()) {
			List<Category> categoriesToDelete = categoryService.findAll();

			Category category = new Category();
			category.setLabels(CategorySingleton.getInstance().getLabels());

			categoryService.deleteAllCategories(categoriesToDelete);
			categoryService.saveCategory(category);

		}

		if (initSettings.isScore()) {
			scoreService.deleteAllScores();
		}

		Setting setting = settingService.getSetting();

		if (setting != null)
			setting.setLaunch(hasWords);

		else if (setting == null || initSettings.isSetting()) {
			settingService.deleteSetting();
			GameDuration gameDuration = new GameDuration();
			gameDuration.setId("1");
			gameDuration.setTime(60);
			gameDuration.setEnable(true);

			setting = new Setting();
			setting.setGameDuration(Collections.singletonList(gameDuration));
			setting.setId("1");
			setting.setLaunch(hasWords);

			settingService.initSetting(setting);
		}

		// Vérifie si l'utilisateur Admin existe
		User user = userService.findUserByUserName("admin");
		if (initSettings.isUser() || user == null) {
			if (user != null)
				userService.deleteAllUsers();
			try {
				user = userService.createDefaultUser();
				userService.createUser(user);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return "OK";
	}
}
