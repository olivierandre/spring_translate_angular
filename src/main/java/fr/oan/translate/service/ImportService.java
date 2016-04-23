package fr.oan.translate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.oan.translate.domain.Category;
import fr.oan.translate.domain.ImportExcel;
import fr.oan.translate.domain.Lang;
import fr.oan.translate.domain.Word;

@Service
public class ImportService {

	@Autowired
	CategoryService categoryService;

	@Autowired
	WordService wordService;

	@Autowired
	LangService langService;

	private String categoryId = null;

	private final static String LABEL = "Label_";
	private final static String CATEGORIES = "categories";
	private final static String WORDS = "words";

	public ImportExcel importData(Sheet sheet, int numberOfColumns, String type) {
		List<Lang> langs = langService.findAllLangs();
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		Row firstRowCategorySheet = sheet.getRow(firstRow);

		if (numberOfColumns == langs.size()) {
			List<String> header = new ArrayList<>();

			// Récupération des colonnes titres
			for (int i = 0; i < numberOfColumns; i++) {
				header.add(firstRowCategorySheet.getCell(i).getStringCellValue().toLowerCase());
			}

			// On vérifie que les headers correspondent aux langues existantes
			List<String> keyLabel = checkHeader(langs, header);
			List<Map<String, String>> labels = new ArrayList<>();

			// On récupére la liste de categories
			if (type.equals(CATEGORIES)) {
				List<Category> tempCategories = categoryService.findAll();

				for (Category category : tempCategories) {
					labels.add(category.getLabels());
				}

			} else if (type.equals(WORDS)) {
				List<Word> tempWords = wordService.findAll();

				for (Word word : tempWords) {
					labels.add(word.getLabels());
				}

			} else {
				return null;
			}

			return getSuccessAndFail(labels, type, lastRow, numberOfColumns, keyLabel, sheet);

		} else {
			throw new IllegalArgumentException("Nombre de colonnes incorrect");
		}

	}

	private List<String> checkHeader(List<Lang> langs, List<String> header) {
		List<String> keyLabel = new ArrayList<>();
		for (Lang lang : langs) {
			String langLabel = LABEL.concat(lang.getCode()).toLowerCase();
			if (!header.contains(langLabel)) {
				throw new IllegalArgumentException("Colonne inconnue");
			} else {
				keyLabel.add(lang.getCode());
			}
		}
		return keyLabel;
	}

	private ImportExcel getSuccessAndFail(List<Map<String, String>> labels, String type, int lastRow,
			int numberOfColumns, List<String> keyLabel, Sheet sheet) {

		List<Map<String, String>> success = new ArrayList<>();
		List<Map<String, String>> fail = new ArrayList<>();
		List<Category> insertCategories = new ArrayList<>();
		List<Word> insertWords = new ArrayList<>();
		int countSuccess = 0;
		int countFailed = 0;

		for (int i = 1; i <= lastRow; i++) {
			Map<String, String> tempMap = new HashMap<>();
			for (int idx = 0; idx < numberOfColumns; idx++) {

				String key = keyLabel.get(idx);
				String value = sheet.getRow(i).getCell(idx).getRichStringCellValue().getString();

				if (value.contains("Poisson")) {
					System.out.println("find");
				}

				if (value.isEmpty()) {
					// TODO : Gérer le fait qu'il n'y a plus de ligne ou alors
					// que le champ est vide
					// Si on trouve vide, on sort de la boucle car pas logique.
					break;
				}
				tempMap.put(key, value);

			}
			if (!tempMap.isEmpty()) {
				if (labels.contains(tempMap)) {
					fail.add(tempMap);
					countFailed += 1;
				} else {
					if (type.equals(CATEGORIES)) {
						insertCategories.add(createCategory(tempMap));
					} else if (type.equals(WORDS)) {
						insertWords.add(createWord(tempMap));
					}
					success.add(tempMap);
					countSuccess += 1;
				}

			}

		}

		if (type.equals(CATEGORIES)) {
			categoryService.saveCategories(insertCategories);
		} else if (type.equals(WORDS)) {
			wordService.saveWords(insertWords);
		}

		return new ImportExcel(success, fail, countSuccess, countFailed);
	}

	/*
	 * public ImportExcel importData(Sheet sheet, int numberOfColumns, String
	 * type) {
	 * 
	 * List<Lang> langs = langService.findAllLangs();
	 * 
	 * int firstRow = sheet.getFirstRowNum(); int lastRow =
	 * sheet.getLastRowNum(); Row firstRowCategorySheet =
	 * sheet.getRow(firstRow);
	 * 
	 * InterfaceDao<?> interfaceDao = DAO.get(type);
	 * 
	 * List<AbstractWord> abstractWords = interfaceDao.getList();
	 * 
	 * List<Map<String, String>> abstractWordList = new ArrayList<Map<String,
	 * String>>();
	 * 
	 * for (AbstractWord abstractWord : abstractWords) {
	 * abstractWordList.add(abstractWord.getLabels()); }
	 * 
	 * List<String> string = new ArrayList<>(); List<String> keySet = new
	 * ArrayList<>();
	 * 
	 * if (numberOfColumns == langs.size()) { for (int i = 0; i <
	 * numberOfColumns; i++) { string.add(firstRowCategorySheet.getCell(i)
	 * .getStringCellValue()); } for (Lang lang : langs) { String langLabel =
	 * label.concat(lang.getCode()); if (!string.contains(langLabel)) { throw
	 * new IllegalArgumentException("Colonne inconnue"); } else {
	 * keySet.add(lang.getCode()); }
	 * 
	 * }
	 * 
	 * List<Map<String, String>> dataMap = new ArrayList<>(); List<Map<String,
	 * String>> fail = new ArrayList<>(); List<Category> categories = new
	 * ArrayList<>(); for (int i = 1; i <= lastRow; i++) { Map<String, String>
	 * tempMap = new HashMap<>();
	 * 
	 * for (int idx = 0; idx < numberOfColumns; idx++) { String key =
	 * keySet.get(idx); String value = sheet.getRow(i).getCell(idx)
	 * .getRichStringCellValue().getString();
	 * 
	 * tempMap.put(key, value);
	 * 
	 * }
	 * 
	 * if (abstractWordList.contains(tempMap)) { fail.add(tempMap); } else { if
	 * (type.equals("categories")) { categories.add(createCategory(tempMap)); }
	 * dataMap.add(tempMap); }
	 * 
	 * }
	 * 
	 * if (type.equals("categories")) {
	 * categoryService.saveCategories(categories); }
	 * 
	 * return new ImportExcel(dataMap, fail);
	 * 
	 * } else { throw new IllegalArgumentException(
	 * "Nombre de colonnes incorrect"); }
	 * 
	 * }
	 */

	private Category createCategory(Map<String, String> labels) {
		Category category = new Category();
		category.setLabels(labels);
		return category;
	}

	private Word createWord(Map<String, String> labels) {
		if (categoryId == null) {
			categoryId = categoryService.findAllCategory().getId();
		}

		Word word = new Word();
		word.setLabels(labels);
		word.setCategory(categoryId);
		return word;
	}
}
