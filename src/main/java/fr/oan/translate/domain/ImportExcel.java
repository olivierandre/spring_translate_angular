package fr.oan.translate.domain;

import java.util.List;
import java.util.Map;

public class ImportExcel {

	private List<Map<String, String>> success;

	private List<Map<String, String>> fail;

	private int countSuccess;

	private int countFailed;

	public ImportExcel(List<Map<String, String>> success, List<Map<String, String>> fail, int countSuccess,
			int countFailed) {
		this.success = success;
		this.fail = fail;
		this.countSuccess = countSuccess;
		this.countFailed = countFailed;
	}

	public ImportExcel() {
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, String>> getSuccess() {
		return success;
	}

	public void setSuccess(List<Map<String, String>> success) {
		this.success = success;
	}

	public List<Map<String, String>> getFail() {
		return fail;
	}

	public void setFail(List<Map<String, String>> fail) {
		this.fail = fail;
	}

	public int getCountSuccess() {
		return countSuccess;
	}

	public void setCountSuccess(int countSuccess) {
		this.countSuccess = countSuccess;
	}

	public int getCountFailed() {
		return countFailed;
	}

	public void setCountFailed(int countFailed) {
		this.countFailed = countFailed;
	}

}
