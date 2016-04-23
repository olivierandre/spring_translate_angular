package fr.oan.translate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.domain.Lang;
import fr.oan.translate.service.LangService;

@RestController
@RequestMapping(value = "api")
public class LangController {

	@Autowired
	LangService langService;

	@RequestMapping(value = "/langs", method = RequestMethod.GET, produces = "application/json")
	public List<Lang> getLangs() {
		List<Lang> langs = langService.findAllLangs();
		return langs;
	}

	@RequestMapping(value = "/secure/langs", method = RequestMethod.POST, produces = "application/json")
	public void createLang(@RequestBody Lang lang) {
		langService.saveLang(lang);
	}

	@RequestMapping(value = "/secure/langs/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteLang(@PathVariable String id) {
		langService.deleteLang(id);
	}
}
