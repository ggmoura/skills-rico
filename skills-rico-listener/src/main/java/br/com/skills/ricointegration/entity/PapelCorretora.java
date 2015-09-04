package br.com.skills.ricointegration.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PapelCorretora extends SkillsEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Papel papel;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Corretora corretora;

	@OneToMany(mappedBy = "papelCorretora", cascade = CascadeType.PERSIST)
	private List<PrecoMedio> precosMedios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Papel getPapel() {
		return papel;
	}

	public void setPapel(Papel papel) {
		this.papel = papel;
	}

	public Corretora getCorretora() {
		return corretora;
	}

	public void setCorretora(Corretora corretora) {
		this.corretora = corretora;
	}

	public List<PrecoMedio> getPrecosMedios() {
		return precosMedios;
	}

	public void setPrecosMedios(List<PrecoMedio> precosMedios) {
		this.precosMedios = precosMedios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((corretora == null) ? 0 : corretora.hashCode());
		result = prime * result + ((papel == null) ? 0 : papel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PapelCorretora other = (PapelCorretora) obj;
		if (corretora == null) {
			if (other.corretora != null)
				return false;
		} else if (!corretora.equals(other.corretora))
			return false;
		if (papel == null) {
			if (other.papel != null)
				return false;
		} else if (!papel.equals(other.papel))
			return false;
		return true;
	}

}
