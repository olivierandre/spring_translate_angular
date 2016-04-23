package fr.oan.translate.utils;

public class JsonTools {

	public static String getJsonResponse(String key, String content) {
		return "{\"" + key + "\":\"" + content + "\"}";

		/*
		 * return "{\"response\":\"You failed to upload " +
		 * file.getOriginalFilename() + "  because the file was empty.\"}";
		 */
	}
}
