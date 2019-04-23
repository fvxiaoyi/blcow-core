package cn.blcow.core.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 
 * @author suyb
 *
 */
public class PagingResult {
	private int total;

	private List<Map<String, Object>> data;

	public PagingResult(int total, List<Map<String, Object>> data) {
		this.total = total;
		this.data = data;
	}

	public PagingResult() {
	}

	public Map<String, Object> dump() {
		Map<String, Object> m = new HashMap<>();
		m.put("total", total);
		m.put("data", data);
		return m;
	}

	public int getTotal() {
		return total;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public boolean isEmpty() {
		return CollectionUtils.isEmpty(data);
	}
}
