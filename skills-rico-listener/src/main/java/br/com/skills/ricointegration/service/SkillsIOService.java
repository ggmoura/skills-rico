package br.com.skills.ricointegration.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.mysql.fabric.xmlrpc.base.Data;

import br.com.skills.ricointegration.entity.Corretora;
import br.com.skills.ricointegration.entity.Papel;
import br.com.skills.ricointegration.entity.PapelCorretora;
import br.com.skills.ricointegration.entity.PrecoMedio;
import br.com.skills.ricointegration.vo.CorretoraVO;
import br.com.skills.ricointegration.vo.PapelVO;

public class SkillsIOService implements Runnable {

	private EntityManager entityManager;

	public SkillsIOService() {
		entityManager = Persistence.createEntityManagerFactory("skills-rico").createEntityManager();
	}
	
	private List<String> papeis;

	public List<String> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<String> papeis) {
		this.papeis = papeis;
	}

	public void run() {
		for (String papel : papeis) {
			criarAcao(papel);
		}
	}

	private void criarAcao(String jsonStr) {
		PapelVO papelVO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.readValue(jsonStr, PapelVO.class);
			papelVO = mapper.readValue(jsonStr, PapelVO.class);
			if (papelVO.getC() != null && !papelVO.getC().isEmpty()) {
				Papel p = recuperarPapel(papelVO.getP());
				Corretora c = null;
				PapelCorretora pc = null;
				PrecoMedio precoMedio = null;
				for (CorretoraVO corretoraVO : papelVO.getC()) {
					c = recuperarCorretora(corretoraVO.getId(), corretoraVO.getNome());
					pc = recuperarPapelCorretora(p, c);
					pc.setPapel(p);
					pc.setPrecosMedios(new ArrayList<PrecoMedio>());
					precoMedio = new PrecoMedio();
					precoMedio.setPapelCorretora(pc);
					precoMedio.setCompra(corretoraVO.getAbp());
					precoMedio.setVenda(corretoraVO.getAsp());
					precoMedio.setDataAconpanhamento(papelVO.getUt());
					entityManager.getTransaction().begin();
					entityManager.persist(precoMedio);
					entityManager.getTransaction().commit();
					pc.getPrecosMedios().add(precoMedio);
					p.getCorretoras().add(pc);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private PapelCorretora recuperarPapelCorretora(Papel p, Corretora c) {
		Query q = entityManager
				.createQuery("select pc from PapelCorretora pc where pc.papel = :papel and pc.corretora = :corretora");
		q.setParameter("papel", p);
		q.setParameter("corretora", c);
		PapelCorretora pc = null;
		try {
			pc = (PapelCorretora) q.getSingleResult();
		} catch (NoResultException e) {
			pc = new PapelCorretora();
			pc.setPapel(p);
			pc.setCorretora(c);
			entityManager.getTransaction().begin();
			entityManager.persist(pc);
			entityManager.getTransaction().commit();
		}
		return pc;
	}

	private Papel recuperarPapel(String code) {
		Query q = entityManager.createQuery("select p from Papel p where p.code = :code");
		q.setParameter("code", code);
		Papel p = null;
		try {
			p = (Papel) q.getSingleResult();
		} catch (NoResultException e) {
			p = new Papel();
			p.setCode(code);
			p.setCorretoras(new ArrayList<PapelCorretora>());
			entityManager.getTransaction().begin();
			entityManager.persist(p);
			entityManager.getTransaction().commit();
		}
		return p;
	}

	private Corretora recuperarCorretora(String codigo, String nome) {
		Query q = entityManager.createQuery("select c from Corretora c where c.codigo = :codigo");
		q.setParameter("codigo", codigo);
		Corretora c = null;
		try {
			c = (Corretora) q.getSingleResult();
		} catch (NoResultException e) {
			c = new Corretora();
			c.setCodigo(codigo);
			c.setNome(nome);
			entityManager.getTransaction().begin();
			entityManager.persist(c);
			entityManager.getTransaction().commit();
		}
		return c;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public List<Papel> recuperarPapeis(Data dataImportacao) {
		Query q = entityManager.createQuery("select new PrecoMedio(pm.compra, pm.venda, pm.papelCorretora.corretora.codigo, pm.papelCorretora.papel.code, pm.dataAconpanhamento) from PrecoMedio pm");
		List<PapelCorretora> pc = q.getResultList();
		return null;
	}
	
	
}