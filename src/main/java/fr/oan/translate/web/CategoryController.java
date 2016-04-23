package fr.oan.translate.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.domain.Category;
import fr.oan.translate.service.CategoryService;

@RestController
@RequestMapping(value = "api")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/category", method = RequestMethod.GET, produces = "application/json")
	public List<Category> getCategories() {
		return categoryService.findAll();
	}

	@RequestMapping(value = "/category-words", method = RequestMethod.GET, produces = "application/json")
	public List<Category> getWordsAffectByCategory() {
		return categoryService.getCategoriesAffectToWord();
	}

	@RequestMapping(value = "/secure/category", method = RequestMethod.POST, produces = "application/json")
	public Category createCategory(@RequestBody Category category) {
		categoryService.saveCategory(category);
		return category;
	}

	@RequestMapping(value = "/secure/category/{id}", method = RequestMethod.PUT, produces = "application/json")
	public Category updateCategory(@RequestBody Category category,
			@PathVariable String id) {
		categoryService.updateCategory(category);
		return category;
	}

	@RequestMapping(value = "/secure/category/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteCategory(@PathVariable String id) {
		categoryService.deleteCategory(id);
	}
}
