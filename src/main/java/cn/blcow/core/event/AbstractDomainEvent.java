package cn.blcow.core.event;

import java.time.Instant;

import org.springframework.context.ApplicationEvent;

import cn.blcow.core.domain.AbstractEntity;

public abstract class AbstractDomainEvent<T extends AbstractEntity> extends ApplicationEvent {

	private static final long serialVersionUID = -3449494106151398932L;

	private String id;

	private Instant createTime;

	private String sourceEntity;

	private boolean dispath;

	public AbstractDomainEvent(T entity) {
		super(entity);
		setId(entity.getId());
		setCreateTime(Instant.now());
		setSourceEntity(entity.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public T getSource() {
		return (T) super.getSource();
	}

	public void dispath() {
		setDispath(true);
	}

	public String getId() {
		return id;
	}

	public Instant getCreateTime() {
		return createTime;
	}

	public String getSourceEntity() {
		return sourceEntity;
	}

	public boolean isDispath() {
		return dispath;
	}

	private void setId(String id) {
		this.id = id;
	}

	private void setCreateTime(Instant createTime) {
		this.createTime = createTime;
	}

	private void setSourceEntity(String sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	private void setDispath(boolean dispath) {
		this.dispath = dispath;
	}

}
