package fr.oan.translate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fr.oan.translate.dao.LevelDao;
import fr.oan.translate.domain.Level;

@Service
public class LevelService {

	@Autowired
	LevelDao levelDao;

	public List<Level> findAllLevel() {
		return levelDao.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	public Level saveLevel(Level level) {
		return levelDao.insert(level);
	}

	public Level updateLevel(String id, Level level) throws Exception {
		Level findLevel = levelDao.findOne(id);

		if (findLevel == null) {
			throw new Exception();
		}

		return levelDao.save(level);
	}

	public void deleteLevel(String id) {
		levelDao.delete(id);
	}

}
