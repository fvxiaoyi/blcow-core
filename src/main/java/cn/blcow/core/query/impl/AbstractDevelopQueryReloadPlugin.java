package cn.blcow.core.query.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.core.io.Resource;

import cn.blcow.core.query.IQueryParser;

public abstract class AbstractDevelopQueryReloadPlugin implements IQueryParser {

	protected Map<String, ResourceHolder> resourceHolders = null;

	@Override
	public String getQueryString(String queryName, Object params) {
		try {
			reload(queryName, () -> queryName.substring(0, queryName.indexOf(".")).toLowerCase());
		} catch (Exception e) {
			throw new RuntimeException("Query statment: [" + queryName + "] reload error");
		}
		return executeInternal(queryName, params);
	}

	protected void reload(String queryName, Supplier<String> queryNameConvert) throws IOException {
		if (isDevelop()) {
			if (queryNameConvert != null) {
				queryName = queryNameConvert.get();
			}
			ResourceHolder res = resourceHolders.get(queryName);
			long lt = res.resource.lastModified();
			if (lt != res.lastModified) {
				doReload(queryName);
			}
		}
	}

	protected void addResourceHolder(String queryName, Resource res) throws IOException {
		if (this.resourceHolders == null) {
			this.resourceHolders = new HashMap<>();
		}
		this.resourceHolders.put(queryName, new ResourceHolder(res));
	}

	protected abstract boolean isDevelop();

	protected abstract void doReload(String queryName);

	protected abstract String executeInternal(String queryName, Object params);

	class ResourceHolder {
		public Resource resource;
		public long lastModified;

		public ResourceHolder(Resource resource) throws IOException {
			this.resource = resource;
			this.lastModified = this.resource.lastModified();
		}
	}
}
