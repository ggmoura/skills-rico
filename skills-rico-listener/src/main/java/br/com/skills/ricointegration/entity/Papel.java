package br.com.skills.ricointegration.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Papel extends SkillsEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String name;

	@OneToMany(mappedBy = "papel", cascade = CascadeType.PERSIST)
	private List<PapelCorretora> corretoras;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PapelCorretora> getCorretoras() {
		return corretoras;
	}

	public void setCorretoras(List<PapelCorretora> corretoras) {
		this.corretoras = corretoras;
	}

}
