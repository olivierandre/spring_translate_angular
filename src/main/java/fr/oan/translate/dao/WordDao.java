package fr.oan.translate.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.oan.translate.domain.Word;

public interface WordDao extends MongoRepository<Word, Serializable> {

	public List<Word> findByCategory(String id);

	public List<Word> findByCategoryAndLevel(String idCategory, String idLevel);

}
