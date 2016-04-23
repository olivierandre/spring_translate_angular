package fr.oan.translate.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.oan.translate.dao.SettingDao;
import fr.oan.translate.domain.GameDuration;
import fr.oan.translate.domain.Setting;

@Service
public class SettingService {

	@Autowired
	SettingDao settingDao;

	public Setting getSetting() {
		return settingDao.findOne("1");
	}

	public void initSetting(Setting setting) {
		settingDao.insert(setting);
	}

	public Setting saveSettingDuration(List<GameDuration> durationToInsert, List<GameDuration> durationToUpdate,
			List<GameDuration> durationToDelete, Boolean launch) {

		Setting setting = this.getSetting();
		List<GameDuration> gamesDuration = Collections.synchronizedList(setting.getGameDuration());

		if (!durationToInsert.isEmpty()) {
			for (GameDuration duration : durationToInsert) {
				gamesDuration.add(duration);
			}
		}

		if (!durationToUpdate.isEmpty()) {
			for (GameDuration dur : gamesDuration) {
				for (GameDuration duration : durationToUpdate) {
					if (dur.getId().equals(duration.getId())) {
						dur.setEnable(duration.isEnable());
						dur.setTime(duration.getTime());
					}
				}
			}

		}

		if (!durationToDelete.isEmpty()) {
			for (GameDuration dur : new ArrayList<GameDuration>(gamesDuration)) {
				for (GameDuration duration : durationToDelete) {
					if (dur.getId().equals(duration.getId())) {
						gamesDuration.remove(dur);
					}
				}
			}
		}

		setting.setGameDuration(gamesDuration);

		// MAJ du lancement du site
		setting.setLaunch(launch);

		return settingDao.save(setting);
	}

	public void deleteSetting() {
		settingDao.deleteAll();
	}
}
