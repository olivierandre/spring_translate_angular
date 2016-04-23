package fr.oan.translate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.domain.Word;
import fr.oan.translate.service.WordService;

@RestController
@RequestMapping(value = "api")
public class WordController {

	@Autowired
	WordService wordService;

	@RequestMapping(value = "/secure/word", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Word createWord(@RequestBody Word word) {
		return wordService.saveWord(word);
	}

	@RequestMapping(value = "/word/category/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public List<Word> getWordsByCategoryId(@PathVariable String categoryId) {
		return wordService.getWordsByCategory(categoryId);
	}

	@RequestMapping(value = "/word", method = RequestMethod.GET, produces = "application/json")
	public List<Word> getWords() {
		return wordService.findAll();
	}

	@RequestMapping(value = "/secure/word/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteWord(@PathVariable String id) {
		wordService.deleteWord(id);
	}

	@RequestMapping(value = "/secure/word/update/{id}", method = RequestMethod.PUT, produces = "application/json")
	public Word updateWord(@RequestBody Word word, @PathVariable String id) {
		wordService.updateWord(word);
		return word;
	}

}
