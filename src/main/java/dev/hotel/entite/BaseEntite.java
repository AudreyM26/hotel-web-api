package dev.hotel.entite;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntite {

	@Id
	@Column(name = "ID")
	@org.hibernate.annotations.Type(type = "uuid-char")
	private UUID uuid;

	public BaseEntite() {
		this.uuid = UUID.randomUUID();
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
