package fr.oan.translate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.oan.translate.domain.Category;

public interface CategoryDao extends MongoRepository<Category, Serializable> {
	public Category findByLabels(Map<String, String> labels);

	@Query(value = "{ }", fields = "{ '_id' : 0, 'labels' : 1}")
	public List<Map<String, String>> findByTheLabels();

	public long count();

}
