package fr.oan.translate.web.request;

import java.util.List;

import fr.oan.translate.domain.GameDuration;

public class SettingsRequest {

	private List<GameDuration> durationToInsert;

	private List<GameDuration> durationToUpdate;

	private List<GameDuration> durationToDelete;

	private boolean launch;

	public List<GameDuration> getDurationToInsert() {
		return durationToInsert;
	}

	public void setDurationToInsert(List<GameDuration> durationToInsert) {
		this.durationToInsert = durationToInsert;
	}

	public List<GameDuration> getDurationToUpdate() {
		return durationToUpdate;
	}

	public void setDurationToUpdate(List<GameDuration> durationToUpdate) {
		this.durationToUpdate = durationToUpdate;
	}

	public List<GameDuration> getDurationToDelete() {
		return durationToDelete;
	}

	public void setDurationToDelete(List<GameDuration> durationToDelete) {
		this.durationToDelete = durationToDelete;
	}

	public boolean isLaunch() {
		return launch;
	}

	public void setLaunch(boolean launch) {
		this.launch = launch;
	}

}
