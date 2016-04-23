package fr.oan.translate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.domain.Level;
import fr.oan.translate.service.LevelService;

@RestController
@RequestMapping(value = "api")
public class LevelController {

	@Autowired
	LevelService levelService;

	@RequestMapping(value = "/level", method = RequestMethod.GET, produces = "application/json")
	public List<Level> getLevels() {
		List<Level> levels = levelService.findAllLevel();
		return levels;
	}

	@RequestMapping(value = "/secure/level", method = RequestMethod.POST, produces = "application/json")
	public Level saveLevel(@RequestBody Level level) {
		return levelService.saveLevel(level);
	}

	@RequestMapping(value = "/secure/level/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteLevel(@PathVariable String id) {
		levelService.deleteLevel(id);
	}

	@RequestMapping(value = "/secure/level/{id}", method = RequestMethod.PUT, produces = "application/json")
	public void updateLevel(@PathVariable String id, @RequestBody Level level) {
		try {
			levelService.updateLevel(id, level);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
