package framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.ExcelReader;

public class ObjectMap {
	private List<Map> rows;
	private String omFilePath;
	private String filePath = omFilePath;

	// ..............................
	// Getting Data form CSV file
	public Map<String, Map> getObjectMap() {
		final Map<String, Map> out = new HashMap<String, Map>();
		// for (Object key : rows.)
		for (Map row : rows) {
			out.put((String) row.get("Name"), row);
		}
		return out;
	}
	// ..............................

	public ObjectMap(String omFilePath, String omSheetName) throws Exception {
		if (omFilePath.toLowerCase().contentEquals("same")) {
			omFilePath = filePath;
		}
		rows = ExcelReader.read(omFilePath, omSheetName);
	}

	public Map<String, Map> getPageMap(String pageName) {
		final Map<String, Map> out = new HashMap<String, Map>();
		// for (Object key : rows.)

		for (Map row : rows) {
			if (!row.get("PageName").equals(pageName))
				continue;
			out.put((String) row.get("Name"), row);
		}
		return out;
	}

	public List<Map> getWebCtrl() {
		return rows;
	}

	public Map<String, String> getAppInfo(String ctrlName) {
		Map<String, String> out = new HashMap<String, String>();
		final Map<String, Map> appInfoMap = getPageMap("_AppInfo_");
		out = (Map<String, String>) appInfoMap.get(ctrlName);
		return out;
	}
}
