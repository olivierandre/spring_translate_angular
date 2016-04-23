package fr.oan.translate.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "score")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Score {

	@Id
	private String id;

	private String playerName;

	private int wordsToFind;

	private int goodAnswer;

	private int badAnswer;

	private int jumpAnswer;

	private int gameDuration;

	private String categoryId;

	private Date date;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getWordsToFind() {
		return wordsToFind;
	}

	public void setWordsToFind(int wordsToFind) {
		this.wordsToFind = wordsToFind;
	}

	public int getGoodAnswer() {
		return goodAnswer;
	}

	public void setGoodAnswer(int goodAnswer) {
		this.goodAnswer = goodAnswer;
	}

	public int getBadAnswer() {
		return badAnswer;
	}

	public void setBadAnswer(int badAnswer) {
		this.badAnswer = badAnswer;
	}

	public int getJumpAnswer() {
		return jumpAnswer;
	}

	public void setJumpAnswer(int jumpAnswer) {
		this.jumpAnswer = jumpAnswer;
	}

	public int getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(int gameDuration) {
		this.gameDuration = gameDuration;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

}
