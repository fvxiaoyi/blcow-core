/*
 * Copyright (c) 2013, GIXON and/or its affiliates. All rights reserved.
 * GIXON PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.blcow.core.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 简单查询对象构造器
 *
 * @author bingo 2013年4月23日
 * @since 1.0
 */
public abstract class SimpleQueryBuilder {

	/**
	 *
	 * 参数创建器
	 *
	 * @author bingo 2013年4月27日
	 * @since 1.0
	 */
	protected abstract static class ParameterBuilder {
		public abstract void populateQuery(EntityManager entityManager, Query query);
	}

	/**
	 * 创建一个命名查询
	 *
	 * @param namedQuery
	 *            命名查询
	 * @return 查询构建器
	 */
	public static SimpleQueryBuilder namedQuery(final String namedQuery) {
		return new SimpleQueryBuilder() {
			@Override
			public String toString() {
				return "Named: " + namedQuery + getParameterDescription();
			}

			@Override
			protected Query makeQueryObject(EntityManager entityManager) {
				return entityManager.createNamedQuery(namedQuery);
			}
		};
	}

	/**
	 * 创建一个T-SQL标准命名查询
	 *
	 * @param nativeQuery
	 *            SQL查询语句
	 * @return 查询构建器
	 */
	public static SimpleQueryBuilder nativeQuery(final String nativeQuery) {
		return new SimpleQueryBuilder() {
			@Override
			public String toString() {
				return "NativeQuery: " + nativeQuery + getParameterDescription();
			}

			@Override
			protected Query makeQueryObject(EntityManager entityManager) {
				return entityManager.createNativeQuery(nativeQuery);
			}
		};
	}

	/**
	 * 创建一个JPQL Query
	 *
	 * @param query
	 *            JPQL 查询语句
	 * @return 查询构建器
	 */
	public static SimpleQueryBuilder query(final String query) {
		return new SimpleQueryBuilder() {
			@Override
			public String toString() {
				return "Query: " + query + " params: " + getParameterDescription();
			}

			@Override
			protected Query makeQueryObject(EntityManager entityManager) {
				return entityManager.createQuery(query);
			}
		};
	}

	ParameterBuilder parameterBuilder;

	public Query createQuery(EntityManager entityManager) {
		Query query = makeQueryObject(entityManager);
		populateQuery(entityManager, query);
		return query;
	}

	/**
	 * 设置一个按照顺序排序的查询参数集合
	 *
	 * @param parameters
	 *            查询参数集合
	 * @return 查询构建器
	 */
	public SimpleQueryBuilder parameters(Collection<?> parameters) {
		return parameters(parameters == null ? null : parameters.toArray());
	}

	/**
	 * 设置查询参数
	 *
	 * @param parameterMap
	 *            查询参数，key-value结构
	 * @return 查询构建器
	 */
	public SimpleQueryBuilder parameters(final Map<?, ?> parameterMap) {
		checkNoParametersConfigured();
		parameterBuilder = new ParameterBuilder() {
			@Override
			@SuppressWarnings("rawtypes")
			public void populateQuery(EntityManager entityManager, Query query) {
				if (parameterMap != null) {
					for (Entry entry : parameterMap.entrySet()) {
						query.setParameter(entry.getKey().toString(), entry.getValue());
					}
				}
			}

			@Override
			public String toString() {
				return "Parameters: " + parameterMap;
			}
		};
		return this;
	}

	/**
	 * 设置查询参数
	 *
	 * @param parameters
	 *            查询参数
	 * @return 查询构建器
	 */
	public SimpleQueryBuilder parameters(final Object... parameters) {
		checkNoParametersConfigured();
		parameterBuilder = new ParameterBuilder() {
			@Override
			public void populateQuery(EntityManager entityManager, Query query) {
				if (parameters != null) {
					int counter = 0;
					for (Object parameter : parameters) {
						query.setParameter(counter++, parameter);
					}
				}
			}

			@Override
			public String toString() {
				return "Parameters: " + Arrays.toString(parameters);
			}
		};
		return this;
	}

	protected void checkNoParametersConfigured() {
		if (parameterBuilder != null) {
			throw new IllegalArgumentException("Cannot add parameters to a QueryBuilder which already has parameters configured");
		}
	}

	protected String getParameterDescription() {
		if (parameterBuilder == null) {
			return "";
		} else {
			return " " + parameterBuilder.toString();
		}
	}

	protected abstract Query makeQueryObject(EntityManager entityManager);

	protected void populateQuery(EntityManager entityManager, Query query) {
		if (parameterBuilder != null) {
			parameterBuilder.populateQuery(entityManager, query);
		}
	}

}
