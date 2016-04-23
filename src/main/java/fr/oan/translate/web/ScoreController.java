package fr.oan.translate.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.domain.Score;
import fr.oan.translate.service.ScoreService;

@RestController
@RequestMapping(value = "api")
public class ScoreController {

	@Autowired
	ScoreService scoreService;

	@RequestMapping(value = "/score", method = RequestMethod.GET, produces = "application/json")
	public List<Score> getAllScores() {
		List<Score> scores = scoreService.findAllScores();
		return scores;
	}

	@RequestMapping(value = "/score", method = RequestMethod.POST, produces = "application/json")
	public void saveScore(@RequestBody Score score) {
		score.setDate(new Date());
		scoreService.saveScore(score);
	}
}
