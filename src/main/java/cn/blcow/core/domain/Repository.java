package cn.blcow.core.domain;

import static cn.blcow.core.domain.SimpleQueryBuilder.namedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.util.CollectionUtils;

import cn.blcow.core.utils.Assert;

public class Repository implements IRepository {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 通过Id和类型查找对象
	 * 
	 * @param entityClass 实体类型
	 * @param id          主键
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object id) {
		return (id != null) ? getEntityManager().find(entityClass, id) : null;
	}

	/**
	 * 通过Id和类型查找对象，并提供锁机制
	 *
	 * @param entityClass 实体类型
	 * @param id          主键
	 * @param lockMode    锁模式
	 * @see javax.persistence.LockModeType
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object id, LockModeType lockMode) {
		return getEntityManager().find(entityClass, id, lockMode);
	}

	/**
	 * 通过NamedQuery和Map参数对象获取结果集
	 *
	 * @param querierName  查询名称
	 * @param parameterMap 参数Map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQuery(String queryName, Map<?, ?> param) {
		List<T> resultList = namedQuery(queryName).parameters(param).createQuery(getEntityManager()).getResultList();
		if (resultList == null)
			resultList = new ArrayList<>();
		return resultList;
	}

	/**
	 * 通过NamedQuery和参数数组对象获取结果集
	 *
	 * @param querierName 查询名称
	 * @param objects     参数数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQuery(String queryName, Object... param) {
		List<T> resultList = namedQuery(queryName).parameters(param).createQuery(getEntityManager()).getResultList();
		if (resultList == null)
			resultList = new ArrayList<>();
		return resultList;
	}

	/**
	 * 通过NamedQuery和Map参数对象获取结果集
	 *
	 * @param querierName  查询名称
	 * @param parameterMap 参数map
	 * @return
	 */
	public <T> T findObjectByNamedQuery(String queryName, Map<?, ?> param) {
		List<T> result = findByNamedQuery(queryName, param);
		if (!CollectionUtils.isEmpty(result)) {
			Assert.requireFalse(result.size() > 1,
					"The query ['" + queryName + "'] result set record number > 1,may be breach intentions");
			return result.get(0);
		}
		return null;
	}

	/**
	 * 通过NamedQuery和参数数组对象获取结果集
	 *
	 * @param querierName 查询名称
	 * @param objects     参数数组
	 * @return
	 */
	public <T> T findObjectByNamedQuery(String queryName, Object... param) {
		List<T> result = findByNamedQuery(queryName, param);
		if (!CollectionUtils.isEmpty(result)) {
			Assert.requireFalse(result.size() > 1,
					"The query ['" + queryName + "'] result set record number > 1,may be breach intentions");
			return result.get(0);
		}
		return null;
	}

	/**
	 * 刷出事务
	 */
	public void flush() {
		getEntityManager().flush();
	}

	/**
	 * 给对象加锁
	 * 
	 * @param object
	 * @param lockModeType
	 */
	public void lock(Object object, LockModeType lockModeType) {
		if (getEntityManager().contains(object)) {
			getEntityManager().lock(object, lockModeType);
		}
	}

	/**
	 * 给对象加锁
	 * 
	 * @param object
	 * @param lockModeType
	 * @param properties
	 */
	public void lock(Object object, LockModeType lockModeType, Map<String, Object> properties) {
		if (getEntityManager().contains(object)) {
			getEntityManager().lock(object, lockModeType, properties);
		}
	}

	/**
	 * 持久化对象
	 * 
	 * @param entity
	 */
	public void persist(Object entity) {
		getEntityManager().persist(entity);
	}

	/**
	 * 合并对象或修改对象
	 * 
	 * @param entity
	 */
	public <T> T merge(T entity) {
		return getEntityManager().merge(entity);
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 */
	public void remove(Object obj) {
		if (obj != null) {
			getEntityManager().remove(obj);
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
