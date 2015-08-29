package br.com.skills.ricointegration.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.skills.ricointegration.service.SkillsFileUploadService;
import br.com.skills.ricointegration.service.SkillsSercice;


public class SkillsApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	
	public SkillsApplication() {
		singletons.add(new SkillsSercice());
		singletons.add(new SkillsFileUploadService());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
}
