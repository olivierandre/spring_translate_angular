package fr.oan.translate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;

import fr.oan.translate.config.CategorySingleton;
import fr.oan.translate.dao.CategoryDao;
import fr.oan.translate.dao.WordDao;
import fr.oan.translate.dao.impl.CategoryDaoImpl;
import fr.oan.translate.domain.Category;
import fr.oan.translate.domain.Word;

@Service
public class CategoryService {

	@Autowired
	CategoryDaoImpl categoryDaoImpl;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	WordDao wordDao;

	@Resource
	MongoTemplate mongoTemplate;

	public List<Category> findAll() {
		// return categoryDaoImpl.getList();
		return categoryDao.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	public List<Map<String, String>> findAllOnlyLabels() {
		return categoryDao.findByTheLabels();
	}

	public List<Category> getCategoriesAffectToWord() {
		String mapFunction = "function() { emit(this.category, 1) }";
		String reduceFunction = "function(key, values) { var sum = 0; for (var i = 0; i < values.length; i++) { sum += values[i]; } return sum;}";

		DBCollection word = mongoTemplate.getCollection("word");
		MapReduceCommand cmd = new MapReduceCommand(word, mapFunction, reduceFunction, null,
				MapReduceCommand.OutputType.INLINE, null);

		MapReduceOutput out = word.mapReduce(cmd);

		List<Category> categories = new ArrayList<>();

		for (DBObject o : out.results()) {
			if (o.get("_id") != null) {
				String id = o.get("_id").toString();
				categories.add(categoryDao.findOne(id));
			}

		}

		return categories;
	}

	public Long countCategory() {
		return categoryDao.count();
	}

	public Category saveCategory(Category category) {
		categoryDao.insert(category);
		return category;
	}

	public List<Category> saveCategories(List<Category> categories) {
		categoryDao.insert(categories);
		return categories;
	}

	public Category updateCategory(Category category) {
		categoryDao.save(category);
		return category;
	}

	public void deleteAllCategories(List<Category> categories) {
		List<Word> words = wordDao.findAll();

		for (Word word : words) {
			word.setCategory(null);
		}

		wordDao.save(words);

		categoryDao.delete(categories);
	}

	public void deleteCategory(String id) {
		List<Word> words = wordDao.findByCategory(id);

		for (Word word : words) {
			word.setCategory(null);
		}

		wordDao.save(words);

		categoryDao.delete(id);

	}

	public Category findAllCategory() {
		return categoryDao.findByLabels(CategorySingleton.getInstance().getLabels());
	}

}
