package fr.oan.translate.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import fr.oan.translate.dao.LangDao;
import fr.oan.translate.domain.Lang;

@Service
public class LangService {

	@Resource
	LangDao langDao;

	public List<Lang> findAllLangs() {
		return langDao.findAll();
	}

	public void saveLang(Lang lang) {
		langDao.save(lang);
	}

	public void saveLangs(List<Lang> langs) {
		langDao.save(langs);
	}

	public void deleteLang(String id) {
		langDao.delete(id);
	}

	public void deleteAllLangs() {
		langDao.deleteAll();
	}
}
