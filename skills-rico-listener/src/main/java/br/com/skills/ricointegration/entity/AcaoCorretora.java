package br.com.skills.ricointegration.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AcaoCorretora extends SkillsEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonBackReference
	private Acao acao;

	@JsonBackReference
	private Corretora corretora;

	@JsonManagedReference
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
