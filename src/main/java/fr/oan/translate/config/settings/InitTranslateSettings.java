package fr.oan.translate.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "init")
public class InitTranslateSettings {
	private boolean category;

	private boolean word;

	private boolean score;

	private boolean setting;

	private boolean user;

	public boolean isSetting() {
		return setting;
	}

	public void setSetting(boolean setting) {
		this.setting = setting;
	}

	public boolean isCategory() {
		return category;
	}

	public void setCategory(boolean category) {
		this.category = category;
	}

	public boolean isWord() {
		return word;
	}

	public void setWord(boolean word) {
		this.word = word;
	}

	public boolean isScore() {
		return score;
	}

	public void setScore(boolean score) {
		this.score = score;
	}

	public boolean isUser() {
		return user;
	}

	public void setUser(boolean user) {
		this.user = user;
	}

}
