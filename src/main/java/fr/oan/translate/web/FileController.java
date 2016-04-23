package fr.oan.translate.web;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.oan.translate.config.settings.ImportSettings;
import fr.oan.translate.domain.ImportExcel;
import fr.oan.translate.exception.TranslateException;
import fr.oan.translate.exception.TranslateExceptionCode;
import fr.oan.translate.exception.TranslateExceptionInfo;
import fr.oan.translate.service.ImportService;

@RestController
@RequestMapping(value = "api/secure/upload/")
@EnableConfigurationProperties(ImportSettings.class)
public class FileController {

	@Autowired
	ImportService importService;

	@Autowired
	ImportSettings importSettings;

	private Sheet getSheet(MultipartFile file) throws Exception {
		InputStream inputStream = file.getInputStream();
		Workbook workbook = WorkbookFactory.create(inputStream);
		inputStream.close();
		Sheet sheet = null;
		try {
			sheet = workbook.getSheetAt(0);
		} catch (IllegalArgumentException e) {
			throw new TranslateException(TranslateExceptionCode.BAD_FILE_IMPORT);
		}

		return sheet;
	}

	@RequestMapping(value = "/{type}", method = RequestMethod.POST, produces = "application/json")
	public ImportExcel uploadWords(@RequestParam("file") MultipartFile file,
			@PathVariable String type) {

		if (!file.isEmpty()) {

			try {
				Sheet sheet = this.getSheet(file);
				if (type.equals(importSettings.getCategory())) {
					return importService.importData(sheet,
							importSettings.getCategoryColumns(), type);
				} else if (type.equals(importSettings.getWord())) {
					return importService.importData(sheet,
							importSettings.getWordColumns(), type);
				}
			} catch (Exception ex) {
				throw new IllegalArgumentException("Erreur document : "
						+ ex.getMessage());
			}

		}
		throw new TranslateException(TranslateExceptionCode.UNKNOWN_TYPE_IMPORT);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ TranslateException.class,
			IllegalArgumentException.class })
	@ResponseBody
	public TranslateExceptionInfo translateException(HttpServletRequest req,
			Exception ex) {
		return new TranslateExceptionInfo(req.getRequestURI(), ex);
	}
}
