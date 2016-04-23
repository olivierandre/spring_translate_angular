package fr.oan.translate.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fr.oan.translate.dao.WordDao;
import fr.oan.translate.dao.impl.WordDaoImpl;
import fr.oan.translate.domain.Word;

@Service
public class WordService {

	@Resource
	WordDao wordDao;

	@Resource
	WordDaoImpl wordDaoImpl;

	public Word saveWord(Word word) {
		return wordDao.insert(word);
	}

	public List<Word> saveWords(List<Word> words) {
		return wordDao.insert(words);
	}

	public List<Word> findAll() {
		// return new ArrayList<AbstractWord>(wordDaoImpl.getList());
		return wordDao.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	public void deleteWord(String id) {
		wordDao.delete(id);
	}

	public Word updateWord(Word word) {
		wordDao.save(word);
		return word;
	}

	public List<Word> getWordsByCategory(String id) {
		return wordDao.findByCategory(id);
	}

	public long count() {
		return wordDao.count();
	}

	public void deleteAllWords() {
		wordDao.deleteAll();
	}

}
