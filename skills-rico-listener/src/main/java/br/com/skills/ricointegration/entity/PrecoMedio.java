package br.com.skills.ricointegration.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PrecoMedio extends SkillsEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double compra;

	private Double venda;

	@ManyToOne(fetch = FetchType.LAZY)
	private PapelCorretora papelCorretora;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAconpanhamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCompra() {
		return compra;
	}

	public void setCompra(Double compra) {
		this.compra = compra;
	}

	public Double getVenda() {
		return venda;
	}

	public void setVenda(Double venda) {
		this.venda = venda;
	}

	public PapelCorretora getPapelCorretora() {
		return papelCorretora;
	}

	public void setPapelCorretora(PapelCorretora papelCorretora) {
		this.papelCorretora = papelCorretora;
	}

	public Date getDataAconpanhamento() {
		return dataAconpanhamento;
	}

	public void setDataAconpanhamento(Date dataAconpanhamento) {
		this.dataAconpanhamento = dataAconpanhamento;
	}

}
