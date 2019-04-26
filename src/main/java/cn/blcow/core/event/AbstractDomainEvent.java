package cn.blcow.core.event;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.ApplicationEvent;

import cn.blcow.core.domain.AbstractEntity;

@Entity
@Table(name = "t_domain_event")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length = 50)
@DiscriminatorOptions(force = true)
public abstract class AbstractDomainEvent<T extends AbstractEntity> extends ApplicationEvent {

	private static final long serialVersionUID = -3449494106151398932L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 36)
	private String id;

	@Column(length = 36)
	private String transactionId;

	private String entityId;

	@Column(length = 36)
	private String sourceEntity;

	private boolean dispatch;

	private Instant createTime;

	public AbstractDomainEvent(T entity) {
		super(entity);
		setEntityId(entity.getId());
		setCreateTime(Instant.now());
		setSourceEntity(entity.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public T getSource() {
		return (T) super.getSource();
	}

	public String getEntityId() {
		return entityId;
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

	public boolean isDispatch() {
		return dispatch;
	}

	public String getTransactionId() {
		return transactionId;
	}

	protected void setDispatch(boolean dispatch) {
		this.dispatch = dispatch;
	}

	protected void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@SuppressWarnings("unused")
	private void setId(String id) {
		this.id = id;
	}

	private void setCreateTime(Instant createTime) {
		this.createTime = createTime;
	}

	private void setSourceEntity(String sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	private void setEntityId(String entityId) {
		this.entityId = entityId;
	}

}
