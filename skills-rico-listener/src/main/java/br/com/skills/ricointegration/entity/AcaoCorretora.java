package br.com.skills.ricointegration.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AcaoCorretora extends SkillsEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Acao acao;
	private Corretora corretora;
	private List<PrecoMedio> precosMedios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Acao getAcao() {
		return acao;
	}

	public void setAcao(Acao acao) {
		this.acao = acao;
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

}
