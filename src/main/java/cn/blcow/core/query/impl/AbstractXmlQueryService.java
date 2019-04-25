package cn.blcow.core.query.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import cn.blcow.core.query.IQueryService;
import cn.blcow.core.query.PagingResult;
import cn.blcow.core.utils.Assert;

public abstract class AbstractXmlQueryService implements IQueryService {

	@Override
	public List<Map<String, Object>> noPaging(String queryName, Map<String, Object> param) {
		String queryStr = getQueryString(queryName, param);
		return doQuery(queryStr, param);
	}

	@Override
	public PagingResult paging(String queryName, Map<String, Object> param) {
		Integer start = (Integer) param.get(START_PARAM), limit = (Integer) param.get(LIMIT_PARAM);
		Assert.requireTrue(start != null && start >= 0 && limit != null && limit > 0,
				"paging query: [" + queryName + "] argument start must >= 0 and limit must > 0");
		String queryStr = getQueryString(queryName, param);
		String totalQueryStr = getTotalQueryString(queryStr);
		List<Map<String, Object>> data = doPagingQuery(queryStr, param, start, limit);
		List<Map<String, Object>> totalData = doQuery(totalQueryStr, param);
		Integer total = ((BigInteger) ((Map<?, ?>) totalData.get(0)).get(TOTAL)).intValue();
		return new PagingResult(total, data);
	}

	@Override
	public Map<String, Object> single(String queryName, Map<String, Object> param) {
		String queryStr = getQueryString(queryName, param);
		List<Map<String, Object>> result = doQuery(queryStr, param);
		return result.size() > 0 ? result.get(0) : null;
	}

	protected abstract <R> List<R> doQuery(String queryStr, Map<String, Object> param);

	protected abstract <R> List<R> doPagingQuery(String queryStr, Map<String, Object> param, Integer start, Integer limit);

	protected abstract String getQueryString(String queryName, Map<String, Object> param);

	protected String getTotalQueryString(String queryString) {
		// FIXME mybatis 的from 空格问题
		queryString = "select count(*) as total " + queryString.substring(shallowIndexOfWord(queryString, "from", 0));
		queryString = queryString.replaceAll("(?i)\\sorder(\\s)+by.+", " "); // 若存在order by则去除
		return queryString;
	}

	private final int shallowIndexOfWord(final String sb, final String search, int fromIndex) {
		final int index = shallowIndexOf(sb, ' ' + search + ' ', fromIndex);
		return index != -1 ? (index + 1) : -1; // In case of match adding one
		// because of space placed in
		// front of search term.
	}

	private final int shallowIndexOf(String sb, String search, int fromIndex) {
		final String lowercase = sb.toLowerCase(); // case-insensitive match
		final int len = lowercase.length();
		final int searchlen = search.length();
		int pos = -1, depth = 0, cur = fromIndex;
		do {
			pos = lowercase.indexOf(search, cur);
			if (pos != -1) {
				for (int iter = cur; iter < pos; iter++) {
					char c = sb.charAt(iter);
					if (c == '(') {
						depth = depth + 1;
					} else if (c == ')') {
						depth = depth - 1;
					}
				}
				cur = pos + searchlen;
			}
		} while (cur < len && depth != 0 && pos != -1);
		return depth == 0 ? pos : -1;
	}
}
