package fr.oan.translate.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.domain.Setting;
import fr.oan.translate.service.SettingService;
import fr.oan.translate.web.request.SettingsRequest;

@RestController
@RequestMapping(value = "api")
public class SettingController {

	@Autowired
	SettingService settingService;

	@RequestMapping(value = "/setting", method = RequestMethod.GET, produces = "application/json")
	public Setting getSettings() {
		return settingService.getSetting();
	}

	@RequestMapping(value = "/setting", method = RequestMethod.POST, produces = "application/json")
	public Setting saveSettings(@RequestBody SettingsRequest settingsRequest) {

		return settingService.saveSettingDuration(settingsRequest.getDurationToInsert(),
				settingsRequest.getDurationToUpdate(), settingsRequest.getDurationToDelete(),
				settingsRequest.isLaunch());
	}
}
