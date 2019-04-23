package cn.blcow.core.query.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.jpa.QueryHints;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import cn.blcow.core.query.IQueryParser;

public class JpaSqlQueryService extends AbstractXmlQueryService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IQueryParser parser;

	protected Query createQuery(String sql, Map<String, Object> param) {
		Query query = entityManager.createNativeQuery(sql).setHint(QueryHints.HINT_READONLY, true);
		query.getParameters().forEach(p -> {
			query.setParameter(p.getName(), param.get(p.getName()));
		});
		setMapResultType(query);
		return query;
	}

	@Override
	protected String getQueryString(String queryName, Map<String, Object> param) {
		return parser.getQueryString(queryName, param);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <R> List<R> doQuery(String queryStr, Map<String, Object> param) {
		Query query = this.createQuery(queryStr, param);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <R> List<R> doPagingQuery(String queryStr, Map<String, Object> param, Integer start, Integer limit) {
		Query query = this.createQuery(queryStr, param);
		query.setFirstResult(start).setMaxResults(limit).setHint(QueryHints.HINT_FETCH_SIZE, limit);
		return query.getResultList();
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	private void setMapResultType(Query query) {
		org.hibernate.query.Query unwrapQuery = query.unwrap(org.hibernate.query.Query.class);
		unwrapQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	}

}
