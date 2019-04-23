package cn.blcow.core.query;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author suyb
 *
 */
public interface IQueryService {

	public String START_PARAM = "start";

	public String LIMIT_PARAM = "limit";

	public String TOTAL = "total";

	public List<Map<String, Object>> noPaging(String name, Map<String, Object> param);

	public PagingResult paging(String name, Map<String, Object> param);

	public Map<String, Object> single(String name, Map<String, Object> param);
}
