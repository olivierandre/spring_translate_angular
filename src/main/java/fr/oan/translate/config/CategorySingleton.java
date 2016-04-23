package fr.oan.translate.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import fr.oan.translate.domain.Category;

public class CategorySingleton {

	private static final Map<String, String> labels = new HashMap<>();

	/**
	 * Constructeur privé
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	private CategorySingleton() {

		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Category> categories = mapper.readValue(
					this.getClass().getClassLoader().getResourceAsStream("category.json"),
					TypeFactory.defaultInstance().constructCollectionType(List.class, Category.class));
			labels.putAll(categories.get(0).getLabels());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** Holder */
	private static class CategorySingletonHolder {
		/** Instance unique non préinitialisée */
		private final static CategorySingleton instance = new CategorySingleton();
	}

	/** Point d'accès pour l'instance unique du singleton */
	public static CategorySingleton getInstance() {
		return CategorySingletonHolder.instance;
	}

	public Map<String, String> getLabels() {
		return labels;
	}
}
