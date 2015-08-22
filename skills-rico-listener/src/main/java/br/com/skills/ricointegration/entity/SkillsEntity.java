package br.com.skills.ricointegration.entity;

import java.io.Serializable;

public abstract class SkillsEntity<ID> implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract ID getId();

	public abstract void setId(ID id);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Boolean ehIgual = Boolean.TRUE;
		if (this == obj) {
			ehIgual = Boolean.TRUE;
		} else if (obj == null) {
			ehIgual = Boolean.FALSE;
		} else if (getClass() != obj.getClass()) {
			ehIgual = Boolean.FALSE;
		} else {
			SkillsEntity<?> other = (SkillsEntity<?>) obj;
			if (getId() == null && other.getId() != null) {
				ehIgual = Boolean.FALSE;
			} else if (!getId().equals(other.getId())) {
				ehIgual = Boolean.FALSE;
			}
		}
		return ehIgual;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [id=" + getId() + "]";
	}

}
