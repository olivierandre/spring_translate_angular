package fr.oan.translate.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.service.TranslateService;

@RestController
@RequestMapping(value = "api")
public class TranslateController {

	@Resource
	private TranslateService translateService;

	@RequestMapping(value = "/messages/{lang}", method = RequestMethod.GET, produces = "application/json")
	public Map<String, String> getMessages(@PathVariable String lang) {
		return translateService.getMessages(lang);
	}

}
