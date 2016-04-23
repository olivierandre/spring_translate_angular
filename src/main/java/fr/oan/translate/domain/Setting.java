package fr.oan.translate.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "setting")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Setting {

	@Id
	private String id;

	private List<GameDuration> gameDuration;

	private boolean launch;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GameDuration> getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(List<GameDuration> gameDuration) {
		this.gameDuration = gameDuration;
	}

	public boolean isLaunch() {
		return launch;
	}

	public void setLaunch(boolean launch) {
		this.launch = launch;
	}

}
