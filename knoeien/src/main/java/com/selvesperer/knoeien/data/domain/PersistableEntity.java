package com.selvesperer.knoeien.data.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Persistable;


@MappedSuperclass
public abstract class PersistableEntity implements Persistable<String> {
	
	private static final long serialVersionUID = -8762380193517415707L;
	
	@Id
	@GeneratedValue(generator = "selvesperer_ids")
	@GenericGenerator(name = "selvesperer_ids", strategy = "com.selvesperer.knoeien.utils.IdGenerator", parameters = { @Parameter(name = "uuid_gen_strategy_class",  value = "com.selvesperer.knoeien.utils.IdGenerator") })
	@Column(name = "id", nullable = false, length = 36, unique = true)
	private String id;
		
	@Override
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.id==null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersistableEntity other = (PersistableEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
