package cn.blcow.core.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8822488203707285308L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "createTime")
	private Instant createTime = Instant.now();

	protected AbstractEntity() {
		super();
	}

	public String getId() {
		return id;
	}

	public Instant getCreateTime() {
		return createTime;
	}

	protected void setId(String id) {
		this.id = id;
	}

	protected void setCreateTime(Instant createTime) {
		this.createTime = createTime;
	}

}
