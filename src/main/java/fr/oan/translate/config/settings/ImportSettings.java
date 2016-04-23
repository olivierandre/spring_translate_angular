package fr.oan.translate.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "import")
public class ImportSettings {

	public int categoryColumns;

	public int wordColumns;

	public String word;

	public String category;

	public int getCategoryColumns() {
		return categoryColumns;
	}

	public void setCategoryColumns(int categoryColumns) {
		this.categoryColumns = categoryColumns;
	}

	public int getWordColumns() {
		return wordColumns;
	}

	public void setWordColumns(int wordColumns) {
		this.wordColumns = wordColumns;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
