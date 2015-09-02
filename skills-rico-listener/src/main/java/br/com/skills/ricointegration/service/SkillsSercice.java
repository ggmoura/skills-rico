package br.com.skills.ricointegration.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.skills.ricointegration.entity.Papel;
import br.com.skills.ricointegration.entity.PapelCorretora;
import br.com.skills.ricointegration.entity.Corretora;
import br.com.skills.ricointegration.entity.PrecoMedio;
import br.com.skills.ricointegration.vo.PapelVO;
import br.com.skills.ricointegration.vo.CorretoraVO;
import br.com.skills.ricointegration.vo.Entrie;
import br.com.skills.ricointegration.vo.Resume;

@Path("/skills")
public class SkillsSercice {

	
	//@PersistenceUnit(unitName = "test")
	//private EntityManager manager;
	
	
	@GET
	@Path("/acoes")
	@Produces("application/json")
	public PapelVO getAcoes() {
		
		PapelVO acao = new PapelVO();
		
		acao.setP("ABCB4");
		acao.setUt(new Date());
		acao.setC(new ArrayList<CorretoraVO>());
		CorretoraVO corretora = null;
		for (int i = 0; i < 10; i++) {
			corretora = new CorretoraVO();
			corretora.setId("000000"+ i);
			corretora.setNome("Corretora " + (i + 1));
			corretora.setAbp(Double.valueOf((i + 1) * i));
			corretora.setAsp(Double.valueOf((i + 1) * i + 1));
			acao.getC().add(corretora);
		}
		
		return acao;
		
	}
	
	@GET
	@Path("/corretoras")
	@Produces("application/json")
	public Corretora getCorretora() {
		
		Corretora c = new Corretora();
		c.setCodigo("00000001");
		c.setId(1L);
		c.setNome("Corretora");
		
		Papel a = new Papel();
		a.setCode("Cod Acao");
		a.setId(2L);
		a.setName("Acao");
		
		List<PapelCorretora> acoesCorretoras = new ArrayList<>();
		PapelCorretora acaoCorretora = new PapelCorretora();
		acaoCorretora.setId(3L);
		acaoCorretora.setPapel(a);
		a.setCorretoras(acoesCorretoras);
		//acaoCorretora.setCorretora(c);
		
		acoesCorretoras.add(acaoCorretora);
		
		c.setPapeis(acoesCorretoras);
		
		List<PrecoMedio> precosMedios = new ArrayList<>();
		PrecoMedio precoMedio = new PrecoMedio();
		precoMedio.setId(4L);
		precoMedio.setCompra(10D);
		precoMedio.setVenda(11D);
		
		precosMedios.add(precoMedio);
		
		acaoCorretora.setPrecosMedios(precosMedios);
		
		//a.setAcoes(acoesCorretoras);
		
		return c;
		
	}
	
	@GET
	@Path("/get")
	@Produces("application/json")
	public Entrie getProductInJSON() {


		Entrie e = new Entrie();
		
		e.setId("001");
		e.setText("pet03");
		e.setDetail("santander");
		e.setBuytrades(1l);
		e.setBuyqty(2l);
		e.setBuyvolume(3l);
		e.setPercentbuyvolume(4d);
		e.setPointsbuyvolume(5l);
		e.setAveragebuyprice(6d);
		e.setSelltrades(7l);
		e.setSellqty(8l);
		e.setSellvolume(9l);
		e.setPercentsellvolume(10d);
		e.setPointssellvolume(11l);
		e.setAveragesellprice(12d);
		e.setTotalqty(13l);
		e.setTotalvolume(14l);
		e.setPercenttotalvolume(15d);
		e.setNetqty(16l);
		e.setNetvolume(17l);
		e.setPercentnetvolume(18d);
		e.setAveragenetprice(19d);
		
		return e;

	}
	
	@GET
	@Path("/resumes")
	@Produces("application/json")
	public Resume getResumes() {

		Entrie e = new Entrie();
		
		e.setId("001");
		e.setText("pet03");
		e.setDetail("santander");
		e.setBuytrades(1l);
		e.setBuyqty(2l);
		e.setBuyvolume(3l);
		e.setPercentbuyvolume(4d);
		e.setPointsbuyvolume(5l);
		e.setAveragebuyprice(6d);
		e.setSelltrades(7l);
		e.setSellqty(8l);
		e.setSellvolume(9l);
		e.setPercentsellvolume(10d);
		e.setPointssellvolume(11l);
		e.setAveragesellprice(12d);
		e.setTotalqty(13l);
		e.setTotalvolume(14l);
		e.setPercenttotalvolume(15d);
		e.setNetqty(16l);
		e.setNetvolume(17l);
		e.setPercentnetvolume(18d);
		e.setAveragenetprice(19d);
		
		Resume r = new Resume();
		r.setUpdatetime(new Date());
		r.setEntries(new ArrayList<Entrie>());
		r.getEntries().add(e);
		r.getEntries().add(e);
		r.getEntries().add(e);
		r.getEntries().add(e);
		
		return r;

	}


	@POST
	@Path("/post")
	@Consumes("application/json")
	public Response createProductInJSON(Entrie market) {

		String result = "Product created : " + market;

		return Response.status(201).entity(result).build();

	}
	
	@POST
	@Path("/entries")
	@Consumes("application/json")
	public Response createResume(Resume resume) {
		
		String result = "Product created : " + resume;
		
		return Response.status(201).entity(result).build();
		
	}

}
