package fr.oan.translate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.oan.translate.dao.ScoreDao;
import fr.oan.translate.domain.Score;

@Service
public class ScoreService {

	@Autowired
	ScoreDao scoreDao;

	public List<Score> findAllScores() {
		return scoreDao.findAll();
	}

	public void saveScore(Score score) {
		scoreDao.insert(score);
	}

	public void deleteAllScores() {
		List<Score> scores = scoreDao.findAll();

		for (Score score : scores) {
			scoreDao.delete(score);
		}
	}
}
